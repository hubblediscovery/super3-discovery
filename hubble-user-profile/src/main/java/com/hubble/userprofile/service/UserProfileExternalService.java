package com.hubble.userprofile.service;

import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.types.UserMediaRatings;
import com.hubble.userprofile.types.UserProfileResponse;

public interface UserProfileExternalService {
	
	/**
	 * Get an instance of {@link UserProfileResponse} with 
	 * user's name, current city, hometown and likes keywords
	 * @return {@link UserProfileResponse} object for this user
	 */
	UserProfileResponse getUserProfileResponse() throws UserProfilerException;
	
	/**
	 * Record feedback from the user when the user rates a recommendation
	 */
	void storeUserMediaRatings(UserMediaRatings userMediaRatings);

}
