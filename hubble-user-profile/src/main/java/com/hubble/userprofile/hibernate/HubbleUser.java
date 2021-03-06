package com.hubble.userprofile.hibernate;

// Generated Mar 17, 2013 12:42:37 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * HubbleUser generated by hbm2java
 */
public class HubbleUser implements java.io.Serializable {

	private String appId;
	private String name;
	private String gender;
	private Date birthday;
	private String hometown;

	public HubbleUser() {
	}

	public HubbleUser(String appId, String name) {
		this.appId = appId;
		this.name = name;
	}

	public HubbleUser(String appId, String name, String gender, Date birthday,
			String hometown) {
		this.appId = appId;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.hometown = hometown;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

}
