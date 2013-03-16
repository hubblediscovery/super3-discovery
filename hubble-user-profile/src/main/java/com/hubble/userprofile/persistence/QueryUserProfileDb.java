/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.persistence;

import java.util.List;

import com.hubble.userprofile.exceptions.UserDoesNotExistException;
import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.hibernate.FacebookEntities;

/**
 * @author narenathmaraman
 * 
 */
public interface QueryUserProfileDb {

	/**
	 * Get the hubble user id using facebookId if the id does not exist, throw
	 * exception
	 * 
	 * @param facebookId
	 * @return the hubbleId of the user
	 * @throws UserDoesNotExistException
	 */
	public String getUserIdFromFacebookId(String facebookId)
			throws UserProfilerException;

	/**
	 * Get the user's current city
	 * 
	 * @param facebookId
	 * @return the current city like, Mountain View California
	 * @throws UserDoesNotExistException
	 */
	public String getCurrentCity(String hubbleAppId)
			throws UserProfilerException;

	/**
	 * Get the user's home town
	 * 
	 * @param facebookId
	 * @return the user's hometown like, Mountain View California
	 * @throws UserDoesNotExistException
	 */
	public String getHometown(String hubbleAppId) throws UserProfilerException;

	/**
	 * Get all the entity ids that the user likes
	 * 
	 * @param hubbleAppId
	 *            , the id of the user
	 * @return list of entity ids
	 * @throws UserProfilerException
	 */
	public List<String> getLikedEntities(String hubbleAppId)
			throws UserProfilerException;

	/**
	 * Get Keywords for entity
	 * 
	 * @param entityId
	 *            , the entity for which keywords are to be fetched
	 * @return
	 */
	public List<String> getEntityKeywords(String entityId)
			throws UserProfilerException;

	/**
	 * Get the FacebookEntities instance referred by entityId
	 * 
	 * @param entityId
	 * @return
	 */
	public FacebookEntities getEntity(String entityId);
	
	/**
	 * Get user name from database referred by hubbleAppId
	 * @param hubbleAppId
	 */
	public String getUserName(String hubbleAppId) throws UserProfilerException;;

}
