package com.hubble.userprofile.hibernate;

// Generated Mar 16, 2013 11:34:47 PM by Hibernate Tools 3.4.0.CR1

/**
 * FacebookUserInterests generated by hbm2java
 */
public class FacebookUserInterests implements java.io.Serializable {

	private String userAppId;
	private String quotes;
	private String sports;
	private String interests;

	public FacebookUserInterests() {
	}

	public FacebookUserInterests(String userAppId) {
		this.userAppId = userAppId;
	}

	public FacebookUserInterests(String userAppId, String quotes,
			String sports, String interests) {
		this.userAppId = userAppId;
		this.quotes = quotes;
		this.sports = sports;
		this.interests = interests;
	}

	public String getUserAppId() {
		return this.userAppId;
	}

	public void setUserAppId(String userAppId) {
		this.userAppId = userAppId;
	}

	public String getQuotes() {
		return this.quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public String getSports() {
		return this.sports;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	public String getInterests() {
		return this.interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

}
