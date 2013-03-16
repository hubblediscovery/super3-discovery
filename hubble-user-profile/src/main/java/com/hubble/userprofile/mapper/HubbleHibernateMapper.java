/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.mapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hubble.userprofile.dataprocessor.AlchemyTextDataProcessor;
import com.hubble.userprofile.dataprocessor.CalaisTextDataProcessor;
import com.hubble.userprofile.dataprocessor.TextDataProcessor;
import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.externaldatareader.FacebookDataReader;
import com.hubble.userprofile.hibernate.FacebookEntities;
import com.hubble.userprofile.hibernate.FacebookUserDemographics;
import com.hubble.userprofile.hibernate.FacebookUserDemographicsId;
import com.hubble.userprofile.hibernate.FacebookUserInterests;
import com.hubble.userprofile.hibernate.FbUserLikedEntities;
import com.hubble.userprofile.hibernate.FbUserLikedEntitiesId;
import com.hubble.userprofile.hibernate.HubbleUser;
import com.hubble.userprofile.hibernate.UserFriends;
import com.hubble.userprofile.hibernate.UserFriendsId;
import com.hubble.userprofile.types.FacebookConnectionData;
import com.hubble.userprofile.types.FacebookData;
import com.hubble.userprofile.types.FacebookUser;
import com.hubble.userprofile.utils.HubbleConstants;
import com.hubble.userprofile.utils.LoggerUtil;

/**
 * Mapper class to map Hubble objects to POJO for persistence and vice versa
 * 
 * @author narenathmaraman TODO : Missing some reverse mapping methods
 * 
 */
public class HubbleHibernateMapper {

	private HubbleHibernateMapper() {

	}

	public static HubbleUser getPersistenceHubbleUser(
			com.hubble.userprofile.types.GenericUser hubbleUser) {
		HubbleUser persistUser = new HubbleUser();
		persistUser.setAppId(hubbleUser.getId());
		persistUser.setName(hubbleUser.getName());
		persistUser.setHometown(hubbleUser.getHometown());
		persistUser.setGender(hubbleUser.getGender());
		persistUser.setBirthday(hubbleUser.getBirthday());
		return persistUser;
	}

	/**
	 * Builds {@link FacebookUserDemographics} instance from
	 * {@link FacebookUser}
	 * 
	 * @param fbUser
	 * @return {@link FacebookUserDemographics} object
	 */
	public static FacebookUserDemographics getFacebookUserDemographics(
			FacebookUser fbUser) {
		FacebookUserDemographics fbUserDemo = new FacebookUserDemographics();
		if (fbUser.getBio() != null)
			fbUserDemo.setBio(fbUser.getBio());
		if (fbUser.getGeography().getCurrentPermanentLocation() != null)
			fbUserDemo.setCurrentLocation(fbUser.getGeography()
					.getCurrentPermanentLocation());
		fbUserDemo.setEmail(fbUser.getEmail());
		FacebookUserDemographicsId dbId = new FacebookUserDemographicsId();
		dbId.setFacebookId(fbUser.getFacebookId());
		dbId.setUserAppId(fbUser.getId());
		fbUserDemo.setId(dbId);
		if (fbUser.getInterestedIn() != null)
			fbUserDemo.setInterestedIn(fbUser.getInterestedIn().toString());
		if (fbUser.getLanguages() != null)
			fbUserDemo.setLanguages(fbUser.getLanguages().toString());
		if (fbUser.getGeography() != null)
			fbUserDemo.setLocale(fbUser.getGeography().getLocale());
		if (fbUser.getMeetingFor() != null)
			fbUserDemo.setMeetingFor(fbUser.getMeetingFor().toString());
		if (fbUser.getInterests() != null)
			fbUserDemo.setPolitical(fbUser.getInterests().getPolitical());
		fbUserDemo.setRelationshipStatus(fbUser.getRelationshipStatus());
		if (fbUser.getInterests() != null)
			fbUserDemo.setReligion(fbUser.getInterests().getReligion());
		fbUserDemo.setSignificantOther(fbUser.getSignificantOtherId());
		fbUserDemo.setThirdPartyId(fbUser.getThirdPartyId());
		if (fbUser.getGeography() != null
				&& fbUser.getGeography().getTimeZone() != null)
			fbUserDemo.setTimeZone(fbUser.getGeography().getTimeZone()
					.toString());
		fbUserDemo.setUpdatedTime(fbUser.getUpdatedTime());
		fbUserDemo.setUsername(fbUser.getUserName());
		if (fbUser.getVerified() != null)
			fbUserDemo.setVerified(fbUser.getVerified());
		fbUserDemo.setWebsite(fbUser.getWebsite());
		if (fbUser.getWork() != null)
			fbUserDemo.setWork(fbUser.getWork().toString().getBytes());
		return fbUserDemo;
	}

	/**
	 * Builds and returns a {@link FacebookUserInterests} object built from a
	 * {@link FacebookUser} object.
	 * 
	 * @param user
	 * @return
	 */
	public static FacebookUserInterests getFacebookUserInterests(
			FacebookUser user) {
		FacebookUserInterests dbFacebookUserInterests = new FacebookUserInterests(
				user.getId());
		// TODO : include Quotes in FacebookUser class
		dbFacebookUserInterests.setQuotes(user.getInterests().getQuotes());
		dbFacebookUserInterests.setInterests(user.getInterests().toString());
		dbFacebookUserInterests.setSports(user.getInterests().getSports()
				.toString());
		return dbFacebookUserInterests;
	}

	/**
	 * Get {@link FbUserLikedEntities} from a {@link FacebookUser} object
	 */
	public static List<FbUserLikedEntities> getFbUserLikedEntities(
			FacebookUser user) {
		List<FbUserLikedEntities> likedEntities = new ArrayList<FbUserLikedEntities>();
		for (FacebookConnectionData data : user.getLikes()) {
			likedEntities.add(new FbUserLikedEntities(
					new FbUserLikedEntitiesId(data.getId(), user.getId())));
		}
		return likedEntities;
	}

	/**
	 * Get {@link UserFriends} from a {@link FacebookUser} object
	 */
	public static List<UserFriends> getUserFriends(FacebookUser user) {
		List<UserFriends> friendsList = new ArrayList<UserFriends>();
		for (FacebookData data : user.getFriends()) {
			friendsList.add(new UserFriends(new UserFriendsId(user.getId(),
					data.getId())));
		}
		return friendsList;
	}

	/**
	 * Map {@link FacebookConnectionData} to {@link FacebookEntities} Set
	 * Description and keywords as well
	 * 
	 * @param connectionObject
	 * @return
	 * @throws UserProfilerException
	 */
	public static FacebookEntities getFacebookEntities(
			FacebookConnectionData connectionObject,
			FacebookDataReader fbDataReader) throws UserProfilerException {
		FacebookEntities fbEntity = new FacebookEntities();
		fbEntity.setId(connectionObject.getId());
		fbEntity.setName(connectionObject.getName());
		fbEntity.setCategory(connectionObject.getCategory());
		fbEntity.setCreatedTime(connectionObject.getCreate_time());
		String descriptionText = fbDataReader
				.getDescriptionForEntity(connectionObject.getId());
		fbEntity.setDescription(descriptionText);
		TextDataProcessor calaisTextProcessor = new CalaisTextDataProcessor();
		TextDataProcessor alchemyTextProcessor = new AlchemyTextDataProcessor(
				new File(HubbleConstants.FileNames.ALCHEMY_API_KEY_FILENAME));
		try {
			List<String> keywordsList = calaisTextProcessor
					.getKeywords(descriptionText);
			List<String> alchemyKeywordsList = alchemyTextProcessor
					.getKeywords(descriptionText);
			if(alchemyKeywordsList != null && !alchemyKeywordsList.isEmpty()) {
				keywordsList.addAll(alchemyKeywordsList);
			}
			fbEntity.setKeywords(keywordsList.toString());
		} catch (Exception e) {
			LoggerUtil.logStackTrace(e);
			throw new UserProfilerException(e.getMessage()
					+ "Error extracting keywords :"
					+ calaisTextProcessor.toString());
		}
		return fbEntity;
	}

}
