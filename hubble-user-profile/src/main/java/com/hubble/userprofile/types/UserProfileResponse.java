/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.types;

import java.util.List;

/**
 * @author narenathmaraman
 *
 */
public class UserProfileResponse {
	
	private String userFullName;
	private String hometown;
	private String currentCity;
	private List<String> likesKeywords;

	/**
	 * User profile object built for parameters required in the response
	 * User by Hubble integration project
	 */
	public UserProfileResponse(String userFullName) {
		this.userFullName = userFullName;
	}

	/**
	 * @return the userFullName
	 */
	public String getUserName() {
		return userFullName;
	}

	/**
	 * @param userFullName the userFullName to set
	 */
	public void setUserName(String userName) {
		this.userFullName = userName;
	}

	/**
	 * @return the hometown
	 */
	public String getHometown() {
		return hometown;
	}

	/**
	 * @param hometown the hometown to set
	 */
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	/**
	 * @return the currentCity
	 */
	public String getCurrentCity() {
		return currentCity;
	}

	/**
	 * @param currentCity the currentCity to set
	 */
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	/**
	 * @return the likesKeywords
	 */
	public List<String> getLikesKeywords() {
		return likesKeywords;
	}

	/**
	 * @param likesKeywords the likesKeywords to set
	 */
	public void setLikesKeywords(List<String> likesKeywords) {
		this.likesKeywords = likesKeywords;
	}

}
