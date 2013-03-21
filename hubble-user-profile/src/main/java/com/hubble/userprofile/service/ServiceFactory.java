/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.service;

import com.hubble.userprofile.types.ClientLoginType;

/**
 * 
 * Factory for the different services
 * 
 * @author narenathmaraman
 * 
 */
public class ServiceFactory {

	public static FacebookService getFacebookService(String accessToken,
			String facebookId) {
		if(accessToken == null || facebookId == null) {
			throw new IllegalArgumentException("loginType/authToken/clientUserId cannot be null");
		}
		return new FacebookServiceImpl(accessToken, facebookId);
	}

	public static UserProfileExternalService getUserProfileService(
			ClientLoginType loginType, String accessToken, String clientUserId) {
		if(loginType == null || accessToken == null || clientUserId == null) {
			throw new IllegalArgumentException("loginType/authToken/clientUserId cannot be null");
		}
		return new UserProfileExternalServiceImpl(loginType, accessToken, clientUserId);
	}

}
