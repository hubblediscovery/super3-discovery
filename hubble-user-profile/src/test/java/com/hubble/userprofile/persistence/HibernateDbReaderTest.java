package com.hubble.userprofile.persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hubble.userprofile.hibernate.HubbleUser;

public class HibernateDbReaderTest {
	static Logger log = Logger.getLogger(HibernateDbReaderTest.class);
	private DataReader reader = new HibernateDataReader();
	private String narenAppId = "1";
	private String narenFbName = "Naren Athmaraman";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void readValidValue() {
		
		HubbleUser user = (HubbleUser) reader.readData(HubbleUser.class,
				narenAppId);
		assertNotNull(String.format(
				"Value is not null for random user id :%s, username: %s",
				narenAppId, user != null ? user.getName() : null), user);
		assertTrue("Data mismatch, fetching wrong entry ",
				narenFbName.equals(user.getName()));
	}

	@Test
	public void readInvalidValue() {
		String randomFbId = String.valueOf(new Random().nextInt(100000000));
		log.debug(String.format("Random id is %s", randomFbId));
		HubbleUser user = (HubbleUser) reader.readData(HubbleUser.class,
				randomFbId);
		assertNull(String.format(
				"Value is not null for random user id :%s, username: %s",
				randomFbId, user != null ? user.getName() : null), user);
	}

}
