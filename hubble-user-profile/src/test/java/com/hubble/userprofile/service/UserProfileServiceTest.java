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

import com.hubble.userprofile.exceptions.UserDoesNotExistException;
import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.persistence.QueryUserProfileDb;
import com.hubble.userprofile.persistence.QueryUserProfileDbImpl;
import com.hubble.userprofile.types.ClientLoginType;
import com.hubble.userprofile.types.UserProfileResponse;
import com.hubble.userprofile.utils.LoggerUtil;
import com.restfb.exception.FacebookOAuthException;

public class UserProfileServiceTest {
	UserProfileExternalService profileSvc = null;
	ProfileService generalProfileSvc = null;
	private QueryUserProfileDb queryDb = null;
	private static final String narenFbId = "32819031";
	private static final String naren2FbId = "100004318901331";
	private static final String ACCESS_TOKEN = "AAACEdEose0cBADfnoOrC2ZAZB7quBViNHQ7vGht6cFzBuO120rArxMle1w32zFvspYClrDGKEZABnQ6hwl59xvSamVB8F2EMOQK332xJAZDZD";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		profileSvc = ServiceFactory.getUserProfileExternalService(
				ClientLoginType.FACEBOOK, ACCESS_TOKEN, narenFbId);
		generalProfileSvc = ServiceFactory.getFacebookService(ACCESS_TOKEN,
				narenFbId);
		queryDb = new QueryUserProfileDbImpl();
	}

	@After
	public void tearDown() throws Exception {
		//TODO : remove all persisted stuff
	}

	@Test
	public void insertUserWithAccessTokenTest() {
		try {
			System.out.println("Pre:" + generalProfileSvc.userExists());
			assertNotNull(generalProfileSvc.getUserLikesKeywords());
			assertTrue("Insert failed, user does not exist",
					generalProfileSvc.userExists());
		} catch (UserProfilerException e) {
			LoggerUtil.logStackTrace(e);
			throw new AssertionError("profileService method throws Exception");
		}
	}

	@Test
	public void userLikesKeywordsWithAccessTokenTest() {
		for (int i = 0; i < 25; i++) {
			System.out.println("Invocation Number : " + i);
			try {
				List<String> keywords = generalProfileSvc
						.getUserLikesKeywords();
				assertNotNull(keywords);
				LoggerUtil.logInfoMessage(keywords);
			} catch (UserProfilerException e) {
				LoggerUtil.logStackTrace(e);
				throw new AssertionError(
						"profileService method throws Exception");
			}
		}
	}

	@Test
	public void getNarenUserNameTest() throws UserProfilerException {
		assertEquals("Naren Athmaraman", generalProfileSvc.getUserName());
	}

	@Test
	public void getUserCitiesTest() throws UserProfilerException {

		List<String> userCities = generalProfileSvc.getUserCities();
		LoggerUtil.logInfoMessage(userCities);
		assertFalse("User cities is empty", userCities.isEmpty());
	}

	// Test if the number of items in the keywords list are the same as the
	// number of liked entities
	@Test
	public void likeKeywordsLikedEntitiesTest() {
		try {
			List<String> likesKeywordsList = generalProfileSvc
					.getUserLikesKeywords();
			List<String> dbLikesList = queryDb.getLikedEntities(queryDb
					.getUserIdFromFacebookId(narenFbId));
			LoggerUtil.logInfoMessage(String.valueOf(likesKeywordsList.size()));
			LoggerUtil.logInfoMessage(String.valueOf(dbLikesList.size()));
			assertEquals(
					"Mismatch in entities from entity table and user-entity table",
					likesKeywordsList.size(), dbLikesList.size());
		} catch (UserProfilerException e) {
			LoggerUtil.logStackTrace(e);
			throw new AssertionError("profileService method throws Exception");
		}
	}

	@Test
	public void invalidAccessToken() throws UserProfilerException {
		expectedException.expect(FacebookOAuthException.class);
		expectedException.expectMessage("Invalid OAuth access token");
		UserProfileExternalService invalidTokenProfileSvc = ServiceFactory
				.getUserProfileExternalService(ClientLoginType.FACEBOOK, "accesstoken",
						narenFbId);
		invalidTokenProfileSvc.getUserProfileResponse();
	}
	
	@Test
	public void invalidClientId() throws UserProfilerException {
		expectedException.expect(UserDoesNotExistException.class);
		expectedException.expectMessage("does not exist in hubble realm");
		UserProfileExternalService invalidTokenProfileSvc = ServiceFactory
				.getUserProfileExternalService(ClientLoginType.FACEBOOK, ACCESS_TOKEN,
						narenFbId + "55");
		invalidTokenProfileSvc.getUserProfileResponse();
	}

	@Test
	public void createServiceWithNullArguments() throws UserProfilerException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("loginType/authToken/clientUserId cannot be null");
		UserProfileExternalService nullTokenProfileSvc = ServiceFactory
				.getUserProfileExternalService(ClientLoginType.FACEBOOK, "accesstoken",
						null);
	}

	@Test
	public void getUserProfileResponseObject() throws UserProfilerException {
		UserProfileResponse response = profileSvc.getUserProfileResponse();
		LoggerUtil.logInfoMessage(response.toString());
	}

}
