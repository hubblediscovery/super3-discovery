package com.hubble.userprofile.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.hibernate.FacebookEntities;
import com.hubble.userprofile.hibernate.FacebookUserDemographics;
import com.hubble.userprofile.hibernate.FacebookUserDemographicsId;
import com.hubble.userprofile.hibernate.HubbleUser;

public class HibernateDbWriterTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private DataWriter writer = new HibernateDataWriter();
	private static final String narenFbId = "32819031";
	private QueryUserProfileDb queryDb = null;

	@Before
	public void setUp() throws Exception {
		queryDb = new QueryUserProfileDbImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void updateCurrentLocationData() throws SecurityException,
			UserProfilerException, NoSuchFieldException {
		FacebookUserDemographics fbUserDemo = new FacebookUserDemographics();
		String newCurrentLocation = "Sunnyvale, CA";
		String hubbleId = queryDb.getUserIdFromFacebookId(narenFbId);
		String existingCurrentLocation = queryDb.getCurrentCity(hubbleId);
		fbUserDemo.setCurrentLocation(newCurrentLocation);
		writer.updateField(fbUserDemo, "currentLocation",
				new FacebookUserDemographicsId(narenFbId, hubbleId));
		assertEquals("Update current location failed", newCurrentLocation,
				queryDb.getCurrentCity(hubbleId));
		// put existing data back
		fbUserDemo.setCurrentLocation(existingCurrentLocation);
		writer.updateField(fbUserDemo, "currentLocation",
				new FacebookUserDemographicsId(narenFbId, hubbleId));
		assertEquals("Revert current location failed", existingCurrentLocation,
				queryDb.getCurrentCity(hubbleId));
	}

	@Test
	public void updateHometownLocationData() throws UserProfilerException,
			SecurityException, NoSuchFieldException {
		String fbHomeTown = "Chicago, IL";
		String hometownField = "hometown";
		String hubbleId = queryDb.getUserIdFromFacebookId(narenFbId);
		String hubbleHomeTown = queryDb.getHometown(hubbleId);
		// update hometown data
		HubbleUser hubbleUser = new HubbleUser();
		hubbleUser.setHometown(fbHomeTown);
		writer.updateField(hubbleUser, hometownField, hubbleId);
		assertEquals("Update hometown failed : ", fbHomeTown,
				queryDb.getHometown(hubbleId));
		// put existing data back
		hubbleUser.setHometown(hubbleHomeTown);
		writer.updateField(hubbleUser, hometownField, hubbleId);
		assertEquals("Revert hometown failed : ", hubbleHomeTown,
				queryDb.getHometown(hubbleId));
	}

	@Test
	public void deleteSingleFbEntityDataTest() {
		String randomId = UUID.randomUUID().toString();
		String randomName = UUID.randomUUID().toString();
		FacebookEntities fbEntity = new FacebookEntities();
		fbEntity.setId(randomId);
		fbEntity.setName(randomName);
		writer.writeData(fbEntity);
		assertEquals("write data failed : ", randomId,
				queryDb.getEntity(randomId).getId());
		// delete entry
		writer.deleteData(fbEntity, fbEntity.getId());
		assertNull("Error deleting data", queryDb.getEntity(randomId));
	}

	@Test
	public void deleteMultipleHomogeneousDataTest() {
		String randomId = UUID.randomUUID().toString();
		String randomName = UUID.randomUUID().toString();
		FacebookEntities fbEntity = new FacebookEntities();
		fbEntity.setId(randomId);
		fbEntity.setName(randomName);
		String randomId2 = UUID.randomUUID().toString();
		String randomName2 = UUID.randomUUID().toString();
		FacebookEntities fbEntity2 = new FacebookEntities();
		fbEntity2.setId(randomId2);
		fbEntity2.setName(randomName2);
		writer.writeData(fbEntity);
		assertEquals("write data failed : ", randomId,
				queryDb.getEntity(randomId).getId());
		writer.writeData(fbEntity2);
		assertEquals("write data failed : ", randomId2,
				queryDb.getEntity(randomId2).getId());
		// delete entry
		List deleteList = new ArrayList();
		deleteList.add(fbEntity);
		deleteList.add(fbEntity2);
		writer.batchDeleteData(deleteList);
		assertNull("Error deleting data", queryDb.getEntity(randomId));
		assertNull("Error deleting data", queryDb.getEntity(randomId2));
	}

}
