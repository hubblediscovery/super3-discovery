/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.externaldatareader;

import java.util.List;

import com.hubble.userprofile.types.FacebookConnectionData;
import com.hubble.userprofile.types.FacebookData;
import com.restfb.types.TestUser;
import com.restfb.types.User;

/**
 * @author narenathmaraman
 * 
 */
public interface FacebookDataReader extends UserDataReader {

	/**
	 * Get user object from facebook
	 */
	User getFacebookUser();

	/**
	 * Get Likes data from facebook
	 * 
	 * @return List of {@link FacebookConnectionData}
	 */
	List<FacebookConnectionData> getLikesList();

	/**
	 * Get friends data from facebook
	 * 
	 * @return List of {@link FacebookData}
	 */
	List<FacebookData> getFriendsList();

	/**
	 * Get the description text for a given entity It is a combination of about
	 * + description + name of the facebook entity
	 * 
	 * @param id
	 *            , id of the page/entity
	 * @return descriptive text
	 */
	String getDescriptionForEntity(String id);
	
	/**
	 * Creates a test user for the hubble app
	 * 
	 * @return
	 */
	TestUser getTestUser(String userName);

}
