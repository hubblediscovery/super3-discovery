package com.hubble.test.utils;

import java.util.UUID;

import org.junit.Test;

import com.hubble.userprofile.externaldatareader.FacebookDataReader;
import com.hubble.userprofile.externaldatareader.FacebookDataReaderImpl;
import com.restfb.types.TestUser;


public class FacebookUtils {

	FacebookDataReader reader = null;
	private static final String ACCESS_TOKEN = "338028699622610|IxkzJEm87EmEhIqfBwM1QkNER_4";

	public FacebookUtils() {
		reader = new FacebookDataReaderImpl(ACCESS_TOKEN);
	}

	@Test
	public void createFacebookUser() {
		TestUser tu = reader.getTestUser(UUID.randomUUID().toString());
		System.out.println(tu);
		System.out.println(tu.getEmail());
		System.out.println(tu.getAccessToken());
	}
}
