/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.dataprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.bigdata.jcalais.CalaisClient;
import mx.bigdata.jcalais.CalaisObject;
import mx.bigdata.jcalais.CalaisResponse;
import mx.bigdata.jcalais.rest.CalaisRestClient;

import org.apache.log4j.Logger;

/**
 * Use Calais text enrichment api to process text data
 * 
 * @author narenathmaraman
 * 
 */
public class CalaisTextDataProcessor implements TextDataProcessor {

	private static final String CALAIS_URL = "http://api.opencalais.com/tag/rs/enrich";
	private static final String calais_api_key = "rfdnj4tdpv9xqgcvef638wum";
	private static Logger log = Logger.getLogger(CalaisTextDataProcessor.class);

	private HttpClientPost clientPost = null;
	private String responseType = "application/xml";

	public CalaisTextDataProcessor() {
		clientPost = new HttpClientPost(CALAIS_URL);
	}

	public CalaisTextDataProcessor(String responseType) {
		this.responseType = responseType;
	}

	/**
	 * Get keywords from Calais API
	 * 
	 * @throws IOException
	 * 
	 * @see com.hubble.userprofile.dataprocessor.TextDataProcessor#getKeywords(java.lang.String)
	 */
	public List<String> getKeywords(final String text) throws IOException {
		CalaisClient client = new CalaisRestClient(calais_api_key);
		CalaisResponse response = client.analyze(text);
		Set<String> keywords = new HashSet<String>();
		// Entities referenced in text
		for (CalaisObject entity : response.getEntities()) {
			keywords.add(entity.getField("name"));
		}
		// Topics referenced in text
		for (CalaisObject topics : response.getTopics()) {
			keywords.add(topics.getField("categoryName"));
		}
		// Social Tags
		for (CalaisObject tags : response.getSocialTags()) {
			keywords.add(tags.getField("name"));
		}
		return new ArrayList<String>(keywords);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hubble.userprofile.dataprocessor.TextDataProcessor#getCategories(
	 * java.lang.String)
	 */
	public List<String> getCategories(final String text) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hubble.userprofile.dataprocessor.TextDataProcessor#getSentiment(java
	 * .lang.String)
	 */
	public List<String> getSentiment(final String text) {
		// TODO Auto-generated method stub
		return null;
	}

}
