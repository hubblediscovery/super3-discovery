package com.hubble.userprofile.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.persistence.QueryUserProfileDb;
import com.hubble.userprofile.persistence.QueryUserProfileDbImpl;
import com.hubble.userprofile.types.ClientLoginType;
import com.hubble.userprofile.utils.LoggerUtil;
import com.restfb.exception.FacebookOAuthException;

public class UserProfileServiceTest {
	UserProfileService profileSvc = null;
	private QueryUserProfileDb queryDb = null;
	private static final String narenFbId = "32819031";
	private static final String naren2FbId = "100004318901331";
	private static final String ACCESS_TOKEN = "AAACEdEose0cBAFSoj4ZBiBZA4a8KCZAHkfajWnBavaoLVoVVVFZCJ74vaLniWK6uKHcpolxHXIUDh4UrogC3yrrxMkVTmmj6FYuJKA2cYgZDZD";

	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Before
	public void setUp() throws Exception {
		profileSvc = new UserProfileServiceImpl(ClientLoginType.FACEBOOK,
				ACCESS_TOKEN);
		queryDb = new QueryUserProfileDbImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void insertUserWithAccessTokenTest() {
		try {
			System.out.println("Pre:" + profileSvc.userExists());
			assertNotNull(profileSvc.getUserLikesKeywords());
			assertTrue("Insert failed, user does not exist",
					profileSvc.userExists());
		} catch (UserProfilerException e) {
			LoggerUtil.logStackTrace(e);
			throw new AssertionError("profileService method throws Exception");
		}
	}

	@Test
	public void userLikesKeywordsWithAccessTokenTest() {
		for (int i = 0; i < 1; i++) {
			System.out.println("Invocation Number : " + i);
			try {
				List<String> keywords = profileSvc.getUserLikesKeywords();
				assertNotNull(keywords);
				System.out.println(keywords);
			} catch (UserProfilerException e) {
				LoggerUtil.logStackTrace(e);
				throw new AssertionError(
						"profileService method throws Exception");
			}
		}
	}

	@Test
	public void getNarenUserNameTest() {
		assertEquals("Naren Athmaraman", profileSvc.getUserName());
	}

	@Test
	public void getUserCitiesTest() throws UserProfilerException {
		
		List<String> userCities = profileSvc.getUserCities();
		System.out.println(userCities);
		assertFalse("User cities is empty", userCities.isEmpty());
	}
	
	//Test if the number of items in the keywords list are the same as the
	//number of liked entities
	@Test
	public void likeKeywordsLikedEntitiesTest() {
		try {
			List<String> likesKeywordsList = profileSvc.getUserLikesKeywords();
			List<String> dbLikesList = queryDb.getLikedEntities(queryDb.getUserIdFromFacebookId(narenFbId));
			LoggerUtil.logInfoMessage(String.valueOf(likesKeywordsList.size()));
			LoggerUtil.logInfoMessage(String.valueOf(dbLikesList.size()));
			assertEquals("Mismatch in entities from entity table and user-entity table", likesKeywordsList.size(), dbLikesList.size());
		} catch (UserProfilerException e) {
			LoggerUtil.logStackTrace(e);
			throw new AssertionError(
					"profileService method throws Exception");
		}
	}
	
	@Test
	public void invalidAccessToken() throws UserProfilerException {
		expectedException.expect(FacebookOAuthException.class);
		expectedException.expectMessage("Invalid OAuth access token");
		UserProfileService invalidTokenProfileSvc = new UserProfileServiceImpl(ClientLoginType.FACEBOOK,
				"InvalidToken");
		invalidTokenProfileSvc.getUserLikesKeywords();
	}
	
	@Test
	public void getUserProfileResponseObject() throws UserProfilerException {
		LoggerUtil.logInfoMessage(profileSvc.getUserProfileResponse().toString());
	}

}
