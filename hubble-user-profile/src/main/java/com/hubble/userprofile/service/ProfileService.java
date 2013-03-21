package com.hubble.userprofile.service;

import java.util.List;

import com.hubble.userprofile.exceptions.UserProfilerException;

public interface ProfileService {
	
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
	String getUserName() throws UserProfilerException;
	
	/**
	 * Returns the hubbleId of the user using the clientId (facebookId or twitterId or hubbleId)
	 * @param clientId
	 * @return
	 * @throws UserProfilerException
	 */
	String getHubbleId() throws UserProfilerException;
	
	/**
	 * Update the database with all the user data Full upload of user data
	 * 
	 * @throws UserProfilerException
	 */
	public void updateUserProfile() throws UserProfilerException;

}
