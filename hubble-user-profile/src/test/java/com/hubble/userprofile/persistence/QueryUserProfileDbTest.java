package com.hubble.userprofile.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.hubble.userprofile.exceptions.UserDoesNotExistException;
import com.hubble.userprofile.exceptions.UserProfilerException;

public class QueryUserProfileDbTest {
	private QueryUserProfileDb queryDb = null;
	private static final String narenFbId = "32819031";
	private static final String invalidFbId = "-1";
	@Rule
	public ExpectedException eExp = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		queryDb = new QueryUserProfileDbImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getNarenAppId() throws UserProfilerException {
		String id = queryDb.getUserIdFromFacebookId(narenFbId);
		assertEquals("1", id);
	}
	
	@Test
	public void getNarenCurrentCity() throws UserProfilerException {
		String id = queryDb.getUserIdFromFacebookId(narenFbId);
		assertEquals("Mountain View, California", queryDb.getCurrentCity(id));
	}
	
	@Test
	public void getNarenHometown() throws UserProfilerException {
		String id = queryDb.getUserIdFromFacebookId(narenFbId);
		assertEquals("Chennai, Tamil Nadu", queryDb.getHometown(id));
	}
	
	@Test
	public void getNarenLikedEntities() throws UserProfilerException {
		String id = queryDb.getUserIdFromFacebookId(narenFbId);
		assertNotNull(queryDb.getLikedEntities(id));
	}
	
	@Test
	public void getNarenLikedEntKeywords() throws UserProfilerException {
		String id = queryDb.getUserIdFromFacebookId(narenFbId);
		List<String> likedEntitiesList = queryDb.getLikedEntities(id);
		List<String> keywordList = new ArrayList<String>();
		for(String entityId : likedEntitiesList) {
			keywordList.addAll(queryDb.getEntityKeywords(entityId));
		}
		System.out.println(keywordList);
		assertFalse(keywordList.isEmpty());
	}

	@Test
	public void NonExistentUser() throws UserProfilerException {
		eExp.expect(UserDoesNotExistException.class);
		queryDb.getUserIdFromFacebookId(invalidFbId);
	}
}
