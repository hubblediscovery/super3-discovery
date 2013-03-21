/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.types;

/**
 * @author narenathmaraman
 *
 */
public class UserMediaRatings {
	
	private String hubbleMediaId;
	private Ratings rating;
	
	/**
	 * Constructor
	 * @param hubbleMediaId
	 * @param rating
	 */
	public UserMediaRatings(String hubbleMediaId, Ratings rating) {
		this.hubbleMediaId = hubbleMediaId;
		this.rating = rating;
	}

	/**
	 * @return the hubbleMediaId
	 */
	public String getHubbleMediaId() {
		return hubbleMediaId;
	}

	/**
	 * @param hubbleMediaId the hubbleMediaId to set
	 */
	public void setHubbleMediaId(String hubbleMediaId) {
		this.hubbleMediaId = hubbleMediaId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMediaRatings [hubbleMediaId=");
		builder.append(hubbleMediaId);
		builder.append(", rating=");
		builder.append(rating);
		builder.append("]");
		return builder.toString();
	}

}
