package com.hubble.userprofile.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hubble.userprofile.exceptions.UserDoesNotExistException;
import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.externaldatareader.FacebookDataReader;
import com.hubble.userprofile.externaldatareader.FacebookDataReaderImpl;
import com.hubble.userprofile.hibernate.FacebookEntities;
import com.hubble.userprofile.hibernate.FacebookUserDemographics;
import com.hubble.userprofile.hibernate.FacebookUserDemographicsId;
import com.hubble.userprofile.hibernate.FbUserLikedEntities;
import com.hubble.userprofile.hibernate.FbUserLikedEntitiesId;
import com.hubble.userprofile.hibernate.HubbleUser;
import com.hubble.userprofile.hibernate.UserFriends;
import com.hubble.userprofile.mapper.HubbleHibernateMapper;
import com.hubble.userprofile.mapper.ThirdPartyDataMapper;
import com.hubble.userprofile.persistence.DataWriter;
import com.hubble.userprofile.persistence.HibernateDataWriter;
import com.hubble.userprofile.persistence.QueryUserProfileDb;
import com.hubble.userprofile.persistence.QueryUserProfileDbImpl;
import com.hubble.userprofile.types.FacebookConnectionData;
import com.hubble.userprofile.types.FacebookUser;
import com.hubble.userprofile.utils.LoggerUtil;

public class FacebookServiceImpl implements FacebookService {

	private String accessToken = null;
	private DataWriter writer = null;
	private QueryUserProfileDb queryDb = null;
	private FacebookUser user = null;
	private FacebookDataReader fbUserDataReader = null;
	private String facebookId = null;

	protected FacebookServiceImpl(String accessToken, String facebookId) {
		this.accessToken = accessToken;
		this.facebookId = facebookId;
		this.writer = new HibernateDataWriter();
		this.queryDb = new QueryUserProfileDbImpl();
		this.fbUserDataReader = new FacebookDataReaderImpl(this.accessToken);
	}

	/**
	 * Returns a list of keywords based on user interests from Facebook based on
	 * pages the user has liked
	 * 
	 * @throws UserProfilerException
	 */
	public List<String> getUserLikesKeywords() throws UserProfilerException {
		// Query keywords for all the entities the user has liked
		return getKeywordsForLikedEntities();
	}

	/**
	 * returns true if the user is already part of the system
	 * 
	 * @param loginType
	 * @param authToken
	 * @return true, if user has used hubble before, false otherwise
	 * @throws UserProfilerException
	 */
	public boolean userExists() throws UserProfilerException {
		try {
			if (!"".equals(getHubbleId())) {
				return true;
			}
		} catch (UserDoesNotExistException e) {
			// purge exception, return false if use doesn't exist
		}
		return false;
	}

	/**
	 * Get the location (current city), hometown and other cities the user has
	 * lived in from user data
	 * 
	 * @return cities list eg : [Sunnyvale CA, New York NY]
	 * @throws UserProfilerException
	 */
	public List<String> getUserCities() throws UserProfilerException {
		List<String> citiesList = new ArrayList<String>();
		String hubbleId = getHubbleId();
		citiesList.add(queryDb.getCurrentCity(hubbleId));
		citiesList.add(queryDb.getHometown(hubbleId));
		return citiesList;
	}

	/**
	 * Get the actual name of the user
	 * 
	 * @return, the actual name of the user
	 * @throws UserProfilerException
	 */
	public String getUserName() throws UserProfilerException {
		return queryDb.getUserName(getHubbleId());
	}

	@Override
	public void insertNewFacebookUser() throws UserProfilerException {
		user = getHubbleFacebookUser(true);
		// batch write
		List userData = new ArrayList();
		// HubbleUser
		userData.add(HubbleHibernateMapper.getPersistenceHubbleUser(user));
		// FacebookUserDemographics
		userData.add(HubbleHibernateMapper.getFacebookUserDemographics(user));
		// FacebookUserInterests
		userData.add(HubbleHibernateMapper.getFacebookUserInterests(user));
		// FbUserLikedEntites
		for (FbUserLikedEntities likedEntity : HubbleHibernateMapper
				.getFbUserLikedEntities(user)) {
			userData.add(likedEntity);
		}
		// UserFriends
		for (UserFriends friends : HubbleHibernateMapper.getUserFriends(user)) {
			userData.add(friends);
		}
		// Write all data
		writer.batchWriteData(userData);
		// add entity data that dont already exist in entity database
		addNewFbEntitesToDb();
	}

	/**
	 * Update user's current City from facebook
	 * 
	 * @param hubbleId
	 * @throws UserProfilerException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void updateCurrentCity(String hubbleId)
			throws UserProfilerException, SecurityException,
			NoSuchFieldException {
		if (user == null) {
			user = getHubbleFacebookUser(false);
		}
		String currentCity = queryDb.getCurrentCity(hubbleId);
		String fbCurrentCity = user.getGeography()
				.getCurrentPermanentLocation();
		if (fbCurrentCity != null) {
			if (!fbCurrentCity.equals(currentCity)) {
				// update city information
				FacebookUserDemographics fbUserDemo = new FacebookUserDemographics();
				fbUserDemo.setCurrentLocation(fbCurrentCity);
				writer.updateField(fbUserDemo, "currentLocation",
						new FacebookUserDemographicsId(user.getFacebookId(),
								hubbleId));
			}
		}
	}

	/**
	 * Update user's home town if it has changed on facebook
	 * @param hubbleId
	 * @throws UserProfilerException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void updateHomeTown(String hubbleId) throws UserProfilerException,
			SecurityException, NoSuchFieldException {
		if (user == null) {
			user = getHubbleFacebookUser(false);
		}
		String fbHomeTown = user.getHometown();
		String hubbleHomeTown = queryDb.getHometown(hubbleId);
		if (fbHomeTown != null) {
			if (!fbHomeTown.equals(hubbleHomeTown)) {
				// update hometown data
				HubbleUser hubbleUser = new HubbleUser();
				hubbleUser.setHometown(fbHomeTown);
				writer.updateField(hubbleUser, "hometown", hubbleId);
			}
		}
	}

	/**
	 * Update user's likes data from facebook remove / add liked / unliked
	 * entities
	 * 
	 * @param hubbleId
	 * @throws UserProfilerException
	 */
	public void updateUserLikes(String hubbleId) throws UserProfilerException {
		if (user == null) {
			user = getHubbleFacebookUser(true);
		}
		// update likes
		List<String> likedEntityIds = queryDb.getLikedEntities(queryDb
				.getUserIdFromFacebookId(user.getFacebookId()));
		List<FacebookConnectionData> fbLikesList = user.getLikes();
		List<String> fbLikesIds = new ArrayList<String>();
		for (FacebookConnectionData connection : fbLikesList) {
			fbLikesIds.add(connection.getId());
		}
		List<FbUserLikedEntities> additionList = new ArrayList<FbUserLikedEntities>();
		List<FbUserLikedEntities> removeList = new ArrayList<FbUserLikedEntities>();
		for (String fbLikesId : fbLikesIds) {
			if (!likedEntityIds.contains(fbLikesId)) {
				// entity needs to be inserted
				additionList.add(new FbUserLikedEntities(
						new FbUserLikedEntitiesId(fbLikesId, hubbleId)));
			}
		}
		for (String likedEntityId : likedEntityIds) {
			if (!fbLikesIds.contains(likedEntityId)) {
				// entity needs to be removed
				removeList.add(new FbUserLikedEntities(
						new FbUserLikedEntitiesId(likedEntityId, hubbleId)));
			}
		}
		writer.batchWriteData(additionList);
		writer.batchDeleteData(removeList);
	}

	/**
	 * Update the database with all the user data Full upload of user data
	 * 
	 * @param loginType
	 * @param authToken
	 * @throws UserProfilerException
	 */
	// suppress warnings is okay, this list is just a container for batchwrite
	// should not be used outside of this method and batchWriteData.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateUserProfile() throws UserProfilerException {
		FacebookUser user = getHubbleFacebookUser(true);
		if (userExists()) {
			// User is already part of the system
			// Just update the relevant parts
			try {
				updateUserData();
			} catch (SecurityException e) {
				LoggerUtil.logStackTrace(e);
				throw new RuntimeException(
						"FATAL error occured, terminating...");
			} catch (NoSuchFieldException e) {
				LoggerUtil.logStackTrace(e);
				throw new RuntimeException(
						"FATAL error occured, terminating...");
			} catch (UserProfilerException checkedExp) {
				LoggerUtil.logStackTrace(checkedExp);
				// TODO : Retry data update
				// Set new exception error message and throwing it back..
				throw new UserProfilerException("Error updating profile");
			}
		} else {
			insertNewFacebookUser();
		}
	}

	/**
	 * If user is cached return cached user data, if not get user from facebook
	 * 
	 * @return, FacebookUser
	 */
	private FacebookUser getHubbleFacebookUser(boolean includeConnections) {
		// TODO : if user is already in cache, return from cache
		if (getUserDataFromCache() == null) {
			user = ThirdPartyDataMapper.getHubbleFbUser(fbUserDataReader
					.getFacebookUser());
			if (includeConnections) {
				user.setLikes(fbUserDataReader.getLikesList());
				user.setFriends(fbUserDataReader.getFriendsList());
			}
		}
		return user;
	}

	/**
	 * Get user data from cache
	 * 
	 * @return
	 */
	private FacebookUser getUserDataFromCache() {
		return null;
	}

	/**
	 * Get a list of entities that the user has liked but are not persisted in
	 * hubble database
	 * 
	 * @throws UserProfilerException
	 */
	private List<FacebookEntities> getNewEntitiesListForUser()
			throws UserProfilerException {
		if (user == null) {
			user = getHubbleFacebookUser(true);
		}
		List<FacebookEntities> fbEntitiesList = new ArrayList<FacebookEntities>();
		// FacebookEntities
		for (FacebookConnectionData likeEntity : user.getLikes()) {
			// Check if entity already exists in db, if yes skip
			// TODO : Add mechanism to update data. If description has been
			// updated etc.
			if (queryDb.getEntity(likeEntity.getId()) == null) {
				fbEntitiesList.add(HubbleHibernateMapper.getFacebookEntities(
						likeEntity, fbUserDataReader));
			}
		}
		return fbEntitiesList;
	}

	/**
	 * Add new fb entites data to db
	 * 
	 * @throws UserProfilerException
	 */
	private void addNewFbEntitesToDb() throws UserProfilerException {
		for (FacebookEntities entity : getNewEntitiesListForUser()) {
			writer.writeData(entity);
		}
	}

	@Override
	public String getHubbleId() throws UserProfilerException {
		return queryDb.getUserIdFromFacebookId(this.facebookId);
	}

	/**
	 * Update parts of user data that have changed, this is for returning users
	 * use update data paths. Look for changes ONLY in current city / hometown
	 * and in likes and update them TODO : Add mechanism to update rest of the
	 * profile.
	 * 
	 * @throws UserProfilerException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * 
	 */
	private void updateUserData() throws UserProfilerException,
			SecurityException, NoSuchFieldException {
		// Update Location and Likes
		String hubbleId = getHubbleId();
		updateCurrentCity(hubbleId);
		updateHomeTown(hubbleId);
		updateUserLikes(hubbleId);
		addNewFbEntitesToDb();
	}

	/**
	 * Get keywords of all the entities the user likes
	 * 
	 * @return
	 * @throws UserProfilerException
	 */
	private List<String> getKeywordsForLikedEntities()
			throws UserProfilerException {
		Set<String> keywords = new HashSet<String>();
		List<String> likedEntityId = queryDb.getLikedEntities(getHubbleId());
		for (String entityId : likedEntityId) {
			keywords.addAll(queryDb.getEntityKeywords(entityId));
		}
		return new ArrayList<String>(keywords);
	}

}
