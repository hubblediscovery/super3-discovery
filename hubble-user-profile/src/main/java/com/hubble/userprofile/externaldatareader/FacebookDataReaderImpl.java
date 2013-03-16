/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.externaldatareader;

import java.util.List;

import org.apache.log4j.Logger;

import com.hubble.userprofile.types.FacebookConnectionData;
import com.hubble.userprofile.types.FacebookData;
import com.hubble.userprofile.utils.LoggerUtil;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Page;
import com.restfb.types.TestUser;
import com.restfb.types.User;

/**
 * @author narenathmaraman
 * 
 */
public class FacebookDataReaderImpl implements FacebookDataReader {

	private static Logger log = Logger.getLogger(FacebookDataReaderImpl.class);

	private final FacebookClient facebookClient;

	public FacebookDataReaderImpl(String accessToken) {
		facebookClient = new DefaultFacebookClient(accessToken);
	}

	/**
	 * Read data objects from facebook
	 * 
	 * @param dataString
	 *            , like "me" to read the user object
	 * @param dataType
	 *            , the type of object read like {@link User}, {@link Page}
	 * @return object of type dataType
	 * 
	 */
	public <T> T readUserData(String dataString, Class<T> dataType) {
		T facebookUser;
		try {
			facebookUser = facebookClient.fetchObject(dataString, dataType);
		} catch (FacebookOAuthException authException) {
			log.debug(authException.getErrorMessage());
			throw authException;
		} catch (FacebookNetworkException networkException) {
			LoggerUtil.logStackTrace(networkException);
			throw networkException;
		}
		return facebookUser;
	}

	/**
	 * Get the connections list from facebook
	 * 
	 * @param connectionString
	 *            , "me/likes", "me/friends"
	 * @param dataType
	 * @return
	 */
	public <T> List<T> readUserConnections(String connectionString,
			Class<T> dataType) {
		Connection<T> connections = facebookClient.fetchConnection(
				connectionString, dataType);
		return connections.getData();
	}

	/**
	 * Returns the name of the class
	 */
	public String getDataSource() {
		return facebookClient.getClass().getName();
	}

	/**
	 * Read a facebook entity with its id
	 * 
	 * @return entity of type T
	 */
	public <T> T readDataWithId(String id, Class<T> dataType) {
		T facebookEntity;
		try {
			facebookEntity = facebookClient.fetchObject(id, dataType);
		} catch (FacebookOAuthException authException) {
			log.debug(authException.getErrorMessage());
			throw authException;
		}
		return facebookEntity;
	}

	/**
	 * Creates a test user for the hubble app
	 * 
	 * @return
	 */
	public TestUser getTestUser(String userName) {
		return facebookClient.publish("338028699622610/accounts/test-users",
				TestUser.class, Parameter.with("installed", "true"),
				Parameter.with("permissions", "read_stream"),
				Parameter.with("name", userName));
	}

	/**
	 * Get user object from facebook
	 * 
	 * @return User object
	 */
	public User getFacebookUser() {
		User user = null;
		try {
			user = readUserData("me", User.class);
		} catch (FacebookNetworkException ne) {
			// TODO : Fix this
			user = readUserData("me", User.class);
		}
		return user;
	}

	/**
	 * Get Likes data from facebook
	 * 
	 * @return
	 */
	public List<FacebookConnectionData> getLikesList() {
		log.info(String.format("Client : %s", getDataSource()));
		List<FacebookConnectionData> likesList = readUserConnections(
				"me/likes", FacebookConnectionData.class);
		return likesList;
	}

	/**
	 * Get friends data from facebook
	 * 
	 * @return
	 */
	public List<FacebookData> getFriendsList() {
		List<FacebookData> friendsList = readUserConnections("me/friends",
				FacebookData.class);
		return friendsList;
	}

	/**
	 * Get the description text for a given entity It is a combination of about
	 * + description + name of the facebook entity
	 * 
	 * @param id
	 *            , id of the page/entity
	 * @return descriptive text
	 */
	public String getDescriptionForEntity(String id) {
		Page entityPageData = readUserData(id, Page.class);
		StringBuilder sb = new StringBuilder();
		sb.append(entityPageData.getAbout());
		sb.append(", ");
		sb.append(entityPageData.getDescription());
		sb.append(", ");
		sb.append(entityPageData.getName());
		return sb.toString();
	}

}
