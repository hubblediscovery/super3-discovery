/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.dataprocessor;

import java.util.List;

/**
 * @author narenathmaraman
 * 
 */
public interface TextDataProcessor {

	List<String> getKeywords(String text) throws Exception;

	List<String> getCategories(String text);

	List<String> getSentiment(String text);

}
