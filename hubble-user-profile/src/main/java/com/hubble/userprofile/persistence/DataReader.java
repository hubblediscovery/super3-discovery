/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.persistence;

import java.io.Serializable;

/**
 * Persistence data reader interface
 * 
 * @author narenathmaraman
 * 
 */
public interface DataReader {

	<T> T readData(Class<T> dataType, Serializable primaryKey);

}
