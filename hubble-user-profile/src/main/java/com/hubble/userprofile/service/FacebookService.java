/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.service;

import com.hubble.userprofile.exceptions.UserProfilerException;

/**
 * Interface for various facebook related operations
 * @author narenathmaraman
 *
 */
public interface FacebookService extends ProfileService {
	
	/**
	 * Read data from facebook and persist it
	 */
	void insertNewFacebookUser() throws UserProfilerException;

}
