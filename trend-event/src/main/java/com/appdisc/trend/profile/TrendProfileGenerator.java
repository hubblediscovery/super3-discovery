package com.appdisc.trend.profile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.TwitterException;

import com.appdisc.AppDiscConstants;
import com.appdisc.db.mongodb.MongoDBConnectionManager;
import com.appdisc.trend.twitter.TwitterManager;
import com.appdisc.trend.twitter.TwitterTrendingProfile;
import com.appdisc.util.KeywordExtractor;
import com.appdisc.util.KeywordExtractor.KeywordExtractorAPIEnum;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:18:11 AM
 * 
 */
public class TrendProfileGenerator {

	private static final Logger log = LoggerFactory
			.getLogger(TrendProfileGenerator.class);

	private static DBCollection locationCollection;
	private static DBCollection trendCollection;

	public TrendProfileGenerator() {
		DB db = MongoDBConnectionManager.getInstance().getTrendDBInstance();
		locationCollection = db
				.getCollection(AppDiscConstants.LOCATION_COLLECTION);
		trendCollection = db.getCollection(AppDiscConstants.TREND_COLLECTION);
	}

	public TrendingProfile generateTrendingProfile(long woeid,
			DateTime trendDate) throws TwitterException,
			XPathExpressionException, SAXException,
			ParserConfigurationException {

		String place = (String) locationCollection.findOne(
				new BasicDBObject("woeid", woeid)).get("name");
		Location location = new Location(woeid);
		location.setPlace(place);

		String trendDateStr = AppDiscConstants.DATE_FORMAT.withZone(
				DateTimeZone.UTC).print(trendDate);
		// TODO: To confirm with the window for pick - 1 or 2 days
		String nextDateStr = AppDiscConstants.DATE_FORMAT.withZone(
				DateTimeZone.UTC).print(trendDate.plusDays(1));
		log.info("Trend Date: {}", trendDateStr);

		DBCursor trendCursor = trendCollection.find(new BasicDBObject(
				"locations.woeid", woeid).append("as_of", new BasicDBObject(
				"$gte", trendDateStr).append("$lt", nextDateStr)),
				new BasicDBObject("trends", 1));
		log.info(
				"******************** Below are the top trending topics for the location in the given time range: {} (woeid: {}) *********************",
				place, woeid);

		TwitterTrendingProfile trendingProfile = new TwitterTrendingProfile();
		trendingProfile.setTrendDate(trendDate.toDate());
		trendingProfile.setLocation(location);

		Map<String, Set<String>> trendingTopicToKeywordsMap = new HashMap<String, Set<String>>();
		Map<String, Set<String>> trendingTopicToTweetsMap = new HashMap<String, Set<String>>();
		trendingProfile
				.setTrendingTopicToKeywordsMap(trendingTopicToKeywordsMap);
		trendingProfile.setTrendingTopicToTweetsMap(trendingTopicToTweetsMap);

		while (trendCursor.hasNext()) {
			@SuppressWarnings("unchecked")
			List<DBObject> trendObjs = (List<DBObject>) trendCursor.next().get(
					"trends");

			for (DBObject trendObj : trendObjs) {
				String trendingTopic = (String) trendObj.get("name");
				String trendingQuery = (String) trendObj.get("query");
				log.info("Trending Topic: {}", trendingTopic);
				QueryResult queryResult = TwitterManager.getInstance()
						.getTwitterInstance().search(new Query(trendingQuery));
				List<Tweet> tweets = queryResult.getTweets();
				log.info(
						"Number of Tweets retrieved for this trending topic: {}",
						tweets.size());
				StringBuilder tweetTexts = new StringBuilder();
				Set<String> tweetStrs = new HashSet<String>();
				for (Tweet tweet : tweets) {
					String tweetText = tweet.getText();
					tweetTexts.append(tweetText);
					tweetStrs.add(tweetText);
				}
				trendingTopicToTweetsMap.put(trendingTopic, tweetStrs);
				trendingTopicToKeywordsMap.put(trendingTopic, KeywordExtractor
						.extractKeywords(tweetTexts.toString(),
								KeywordExtractorAPIEnum.ALCHEMY_API));
			}
		}
		// TODO: Persist this profile in the DB
		return trendingProfile;
	}

}
