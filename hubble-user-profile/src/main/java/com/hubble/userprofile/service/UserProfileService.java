package com.hubble.userprofile.service;

import java.util.List;

import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.types.UserProfileResponse;

public interface UserProfileService {

	/**
	 * Get keywords that represent a user's likes
	 * 
	 * @return a List of keywords that represent the entities the user has liked
	 *         on facebook etc.
	 */
	List<String> getUserLikesKeywords() throws UserProfilerException;

	/**
	 * returns true if the user is already part of the system
	 * 
	 * @return true, if user has used hubble before, false otherwise
	 */
	boolean userExists() throws UserProfilerException;

	/**
	 * Get the current city, hometown and other cities the user has lived in
	 * from user data
	 * 
	 * @return eg : [Sunnyvale CA, New York NY]
	 */
	List<String> getUserCities() throws UserProfilerException;

	/**
	 * Get the actual name of the user
	 * 
	 * @return, the actual name of the user
	 */
	String getUserName();
	
	/**
	 * Get an instance of {@link UserProfileResponse} with 
	 * user's name, current city, hometown and likes keywords
	 * @return {@link UserProfileResponse} object for this user
	 */
	UserProfileResponse getUserProfileResponse() throws UserProfilerException;

}
