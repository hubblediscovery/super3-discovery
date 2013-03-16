/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.types;

/**
 * Data object for Geography related data
 * 
 * @author narenathmaraman
 * 
 */
public class UserGeography {

	private String locale;
	private String currentPermanentLocation;
	private Double timeZone;

	public UserGeography() {

	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the currentPermanentLocation
	 */
	public String getCurrentPermanentLocation() {
		return currentPermanentLocation;
	}

	/**
	 * @param currentPermanentLocation
	 *            the currentPermanentLocation to set
	 */
	public void setCurrentPermanentLocation(String currentPermanentLocation) {
		this.currentPermanentLocation = currentPermanentLocation;
	}

	/**
	 * @return the timeZone
	 */
	public Double getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone
	 *            the timeZone to set
	 */
	public void setTimeZone(Double timeZone) {
		this.timeZone = timeZone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserGeography [locale=");
		builder.append(locale);
		builder.append(", currentPermanentLocation=");
		builder.append(currentPermanentLocation);
		builder.append(", timeZone=");
		builder.append(timeZone);
		builder.append("]");
		return builder.toString();
	}

}
