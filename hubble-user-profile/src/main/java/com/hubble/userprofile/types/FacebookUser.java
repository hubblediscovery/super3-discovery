/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.types;

import java.util.Date;
import java.util.List;

import com.restfb.types.User.Work;

/**
 * Models user data based on fields in the Facebook User object. This models the
 * personal data of the user. Refer :
 * https://developers.facebook.com/docs/reference/api/user/
 * 
 * @author narenathmaraman
 * 
 */
public class FacebookUser extends GenericUser {

	private String facebookId;
	private String userName;
	private List<String> interestedIn;
	private List<String> languages;
	private List<String> meetingFor;
	private String relationshipStatus;
	private String significantOtherId;
	private String thirdPartyId;
	private Date updatedTime;
	private Boolean verified;
	private String website;
	private List<Work> work;
	private String bio;
	private String email;
	private UserGeography geography;
	private UserInterests interests;
	private List<FacebookConnectionData> likes;
	private List<FacebookData> friends;

	public FacebookUser(String facebookId) {
		super();
		this.facebookId = facebookId;
	}

	/**
	 * @return the facebookId
	 */
	public String getFacebookId() {
		return facebookId;
	}

	/**
	 * @param facebookId
	 *            the facebookId to set
	 */
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the interestedIn
	 */
	public List<String> getInterestedIn() {
		return interestedIn;
	}

	/**
	 * @param interestedIn
	 *            the interestedIn to set
	 */
	public void setInterestedIn(List<String> interestedIn) {
		this.interestedIn = interestedIn;
	}

	/**
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/**
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	/**
	 * @return the meetingFor
	 */
	public List<String> getMeetingFor() {
		return meetingFor;
	}

	/**
	 * @param meetingFor
	 *            the meetingFor to set
	 */
	public void setMeetingFor(List<String> meetingFor) {
		this.meetingFor = meetingFor;
	}

	/**
	 * @return the relationshipStatus
	 */
	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	/**
	 * @param relationshipStatus
	 *            the relationshipStatus to set
	 */
	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	/**
	 * @return the significantOtherId
	 */
	public String getSignificantOtherId() {
		return significantOtherId;
	}

	/**
	 * @param significantOtherId
	 *            the significantOtherId to set
	 */
	public void setSignificantOtherId(String significantOtherId) {
		this.significantOtherId = significantOtherId;
	}

	/**
	 * @return the thirdPartyId
	 */
	public String getThirdPartyId() {
		return thirdPartyId;
	}

	/**
	 * @param thirdPartyId
	 *            the thirdPartyId to set
	 */
	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	/**
	 * @return the updatedTime
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime
	 *            the updatedTime to set
	 */
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return the verified
	 */
	public Boolean getVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the work
	 */
	public List<Work> getWork() {
		return work;
	}

	/**
	 * @param list
	 *            the work to set
	 */
	public void setWork(List<Work> list) {
		this.work = list;
	}

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @param bio
	 *            the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the geography
	 */
	public UserGeography getGeography() {
		return geography;
	}

	/**
	 * @param geography
	 *            the geography to set
	 */
	public void setGeography(UserGeography geography) {
		this.geography = geography;
	}

	/**
	 * @return the interests
	 */
	public UserInterests getInterests() {
		return interests;
	}

	/**
	 * @param interests
	 *            the interests to set
	 */
	public void setInterests(UserInterests interests) {
		this.interests = interests;
	}

	/**
	 * @return the likes
	 */
	public List<FacebookConnectionData> getLikes() {
		return likes;
	}

	/**
	 * @param likes
	 *            the likes to set
	 */
	public void setLikes(List<FacebookConnectionData> likes) {
		this.likes = likes;
	}

	/**
	 * @return the friends
	 */
	public List<FacebookData> getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends(List<FacebookData> friends) {
		this.friends = friends;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FacebookUser [facebookId=");
		builder.append(facebookId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", interestedIn=");
		builder.append(interestedIn);
		builder.append(", languages=");
		builder.append(languages);
		builder.append(", meetingFor=");
		builder.append(meetingFor);
		builder.append(", relationshipStatus=");
		builder.append(relationshipStatus);
		builder.append(", significantOtherId=");
		builder.append(significantOtherId);
		builder.append(", thirdPartyId=");
		builder.append(thirdPartyId);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", verified=");
		builder.append(verified);
		builder.append(", website=");
		builder.append(website);
		builder.append(", work=");
		builder.append(work);
		builder.append(", bio=");
		builder.append(bio);
		builder.append(", email=");
		builder.append(email);
		builder.append(", geography=");
		builder.append(geography);
		builder.append(", interests=");
		builder.append(interests);
		builder.append(", likes=");
		builder.append(likes);
		builder.append(", friends=");
		builder.append(friends);
		builder.append("]");
		return builder.toString();
	}
}
