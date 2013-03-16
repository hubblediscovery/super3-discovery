package com.hubble.userprofile.main;

import java.util.List;

import com.hubble.userprofile.types.ClientLoginType;

public interface UserProfileService {
	
	/**
	 * Get keywords that represent a user's likes 
	 * @return a List of keywords that represent the entities the user
	 * has liked on facebook etc.
	 */
	List<String> getUserLikesKeywords();
	
	/**
	 * returns true if the user is already part of the system
	 * @return true, if user has used hubble before, false otherwise
	 */
	boolean userExists();
	
	/**
	 * Get the current city, hometown and other cities the user has 
	 * lived in from user data
	 * @return eg : [Sunnyvale CA, New York NY]
	 */
	List<String> getUserCities();

}
