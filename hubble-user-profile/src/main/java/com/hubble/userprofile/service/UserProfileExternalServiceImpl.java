package com.hubble.userprofile.service;

import org.apache.log4j.Logger;

import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.persistence.QueryUserProfileDb;
import com.hubble.userprofile.persistence.QueryUserProfileDbImpl;
import com.hubble.userprofile.types.ClientLoginType;
import com.hubble.userprofile.types.UserMediaRatings;
import com.hubble.userprofile.types.UserProfileResponse;

public class UserProfileExternalServiceImpl implements UserProfileExternalService {
	static Logger log = Logger.getLogger(UserProfileExternalServiceImpl.class);

	private ProfileService profileService = null;
	private QueryUserProfileDb queryDb = null;

	/**
	 * Return a user profile service instance
	 * 
	 * @param loginType
	 *            login mechanism used, facebook / twitter or Hubble
	 * @param authToken
	 *            the authentication token returned by the auth mechanism
	 * @param clientUserId
	 *            the user id associated with the auth token
	 */
	protected UserProfileExternalServiceImpl(ClientLoginType loginType,
			String authToken, String clientUserId) {
		if (loginType == null || authToken == null || clientUserId == null) {
			throw new IllegalArgumentException(
					"loginType/authToken/clientUserId cannot be null");
		}
		if (ClientLoginType.FACEBOOK.equals(loginType)) {
			profileService = ServiceFactory.getFacebookService(authToken,
					clientUserId);
		}
		queryDb = new QueryUserProfileDbImpl();
	}

	/**
	 * Get an instance of {@link UserProfileResponse} with user's name, current
	 * city, hometown and likes keywords
	 * 
	 * @return {@link UserProfileResponse} object for this user
	 */
	public UserProfileResponse getUserProfileResponse()
			throws UserProfilerException {
		profileService.updateUserProfile();
		UserProfileResponse userData = new UserProfileResponse(
				profileService.getUserName());
		String hubbleId = profileService.getHubbleId();
		userData.setCurrentCity(queryDb.getCurrentCity(hubbleId));
		userData.setHometown(queryDb.getHometown(hubbleId));
		userData.setLikesKeywords(profileService.getUserLikesKeywords());
		return userData;
	}

	@Override
	public void storeUserMediaRatings(UserMediaRatings userMediaRatings) {
		// TODO Implement update ratings method

	}

}
