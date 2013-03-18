package com.appdisc;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:18:52 AM
 * 
 */
public final class AppDiscConstants {

	public static final String LOCATION_COLLECTION = "location_c";
	public static final String TREND_COLLECTION = "trend_c";
	public static final String DB_NAME = "twittertrend_db";
	public static final String DB_HOST = "localhost";
	public static final int DB_PORT = 27017;
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat
			.forPattern("YYYY-MM-dd HH:MM:ssZ");
	public static final int NUM_OF_TWEETS_CONSIDERED = 5;

	public static final String ALCHEMY_API_KEY_FILE = "alchemy_api_key.properties";
	public static final String ALCHEMY_LIMIT_ERROR = "daily-transaction-limit-exceeded";
}
