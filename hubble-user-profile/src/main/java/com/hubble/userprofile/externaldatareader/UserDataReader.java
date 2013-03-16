/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.externaldatareader;

import java.util.List;

/**
 * Generic user data reader. interface to read user data using various sources.
 * 
 * @author narenathmaraman
 * 
 */
public interface UserDataReader {

	<T> T readUserData(String dataString, Class<T> objectType);

	<T> List<T> readUserConnections(String connectionString, Class<T> dataType);

	<T> T readDataWithId(String id, Class<T> dataType);

	String getDataSource();

}
