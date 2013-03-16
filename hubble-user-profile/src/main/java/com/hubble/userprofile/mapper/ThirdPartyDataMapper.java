/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.mapper;

import com.hubble.userprofile.types.FacebookUser;
import com.hubble.userprofile.types.UserGeography;
import com.hubble.userprofile.types.UserInterests;
import com.restfb.types.User;

/**
 * Maps user profile types from third party applications to hubble user profile
 * types
 * 
 * @author narenathmaraman
 * 
 */
public class ThirdPartyDataMapper {

	private ThirdPartyDataMapper() {

	}

	/**
	 * Maps restfb libraries "User" to Hubble FacebookUser
	 * (com.hubble.userprofile.types.FacebookUser)
	 * 
	 * @param facebookUser
	 *            , User object from restfb
	 * @return com.hubble.userprofile.types.FacebookUser object with fields set
	 */
	public static FacebookUser getHubbleFbUser(User facebookUser) {
		FacebookUser hubbleFbUser = new FacebookUser(facebookUser.getId()); // random
																			// id
																			// is
																			// set
		// Set GenericUser fields
		hubbleFbUser.setName(facebookUser.getName());
		hubbleFbUser.setBirthday(facebookUser.getBirthdayAsDate());
		hubbleFbUser.setGender(facebookUser.getGender());
		hubbleFbUser.setHometown(facebookUser.getHometownName());
		// Set FacebookUser fields
		if (facebookUser.getSignificantOther() != null)
			hubbleFbUser.setSignificantOtherId(facebookUser
					.getSignificantOther().getId());
		hubbleFbUser.setBio(facebookUser.getBio());
		hubbleFbUser.setEmail(facebookUser.getEmail());
		hubbleFbUser.setInterestedIn(facebookUser.getInterestedIn());
		hubbleFbUser.setMeetingFor(facebookUser.getMeetingFor());
		hubbleFbUser
				.setRelationshipStatus(facebookUser.getRelationshipStatus());
		hubbleFbUser.setThirdPartyId(facebookUser.getThirdPartyId());
		hubbleFbUser.setUpdatedTime(facebookUser.getUpdatedTime());
		hubbleFbUser.setUserName(facebookUser.getUsername());
		hubbleFbUser.setVerified(facebookUser.getVerified());
		hubbleFbUser.setWebsite(facebookUser.getWebsite());
		hubbleFbUser.setWork(facebookUser.getWork());
		hubbleFbUser.setGeography(getHubbleUserGeography(facebookUser));
		hubbleFbUser.setInterests(getHubbleUserInterests(facebookUser));
		return hubbleFbUser;
	}

	private static UserGeography getHubbleUserGeography(User facebookUser) {
		UserGeography userGeo = new UserGeography();
		if (facebookUser.getLocation() != null)
			userGeo.setCurrentPermanentLocation(facebookUser.getLocation()
					.getName());
		userGeo.setLocale(facebookUser.getLocale());
		userGeo.setTimeZone(facebookUser.getTimezone());
		return userGeo;
	}

	private static UserInterests getHubbleUserInterests(User facebookUser) {
		UserInterests userInterests = new UserInterests();
		userInterests.setPolitical(facebookUser.getPolitical());
		userInterests.setQuotes(facebookUser.getQuotes());
		userInterests.setReligion(facebookUser.getReligion());
		userInterests.setSports(facebookUser.getSports());
		return userInterests;
	}

}
