package com.appdisc.trend.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:19:12 AM
 * 
 */
public class TwitterManager {

	private static final TwitterManager TWITTER_MANAGER = new TwitterManager();
	private static Twitter twitter;

	private TwitterManager() {
		twitter = new TwitterFactory(new ConfigurationBuilder()
				.setJSONStoreEnabled(true).build()).getInstance();
	};

	public static TwitterManager getInstance() {
		return TWITTER_MANAGER;
	}

	public Twitter getTwitterInstance() {
		return twitter;
	}
}
