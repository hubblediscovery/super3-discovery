package com.appdisc.trend.twitter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import com.appdisc.AppDiscConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
 * 
 * @author easwar
 * @date Dec 29, 2012 1:01:52 AM
 * 
 *       Fetches the locations that Twitter has trending topic information for
 *       and fetches the top 10 trending topics for each of those locations
 *       which are persisted in the corresponding collections
 */
public final class TrendPersister {

	private static Twitter twitter;
	private static Mongo mongo;
	private static DB db;

	private static Map<Integer, String> woeidToLocationMap = new HashMap<Integer, String>();

	public static void main(String[] args) {

		try {
			twitter = new TwitterFactory(new ConfigurationBuilder()
					.setJSONStoreEnabled(true).build()).getInstance();
			mongo = new Mongo(AppDiscConstants.DB_HOST,
					AppDiscConstants.DB_PORT);
			db = mongo.getDB(AppDiscConstants.DB_NAME);
			DBCollection locationCollection = db
					.getCollection(AppDiscConstants.LOCATION_COLLECTION);
			DBCollection trendCollection = db
					.getCollection(AppDiscConstants.TREND_COLLECTION);

			storeRawJSONLocations(locationCollection);
			storeRawJSONTrends(trendCollection);
			// TODO: twitter.getWeeklyTrends();

			printTrendLocationWithWoeid(locationCollection);

			System.out.println("done.");
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get trends: " + te.getMessage());
			System.exit(-1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongo.close();
			// System.exit(0);
		}
	}

	private static void storeRawJSONTrends(DBCollection trendCollection)
			throws TwitterException {
		Trends trends = null;
		for (int woeid : woeidToLocationMap.keySet()) {
			trends = twitter.getLocationTrends(woeid);
			System.out.println("Storing available RAW JSON trends for "
					+ woeidToLocationMap.get(woeid) + " (" + woeid + ")");
			System.out.println("Below are the top ten trends: ");
			String jsonTrends = DataObjectFactory.getRawJSON(trends);
			jsonTrends = jsonTrends.substring(1, jsonTrends.length() - 1);
			DBObject trendsObj = (DBObject) JSON.parse(jsonTrends);
			System.out.println(trendsObj);
			for (Trend trend : trends.getTrends()) {
				System.out.println(trend.getName());
			}
			trendCollection.insert(trendsObj);
			System.out.println("Stored...");
		}
	}

	private static void storeRawJSONLocations(DBCollection locationCollection)
			throws TwitterException {
		ResponseList<Location> locations = twitter.getAvailableTrends();
		System.out.println("Storing available RAW JSON trend locations...");
		List<DBObject> locationListObj = new ArrayList<DBObject>();
		for (Location location : locations) {
			int woeid = location.getWoeid();
			String locationName = location.getName();
			woeidToLocationMap.put(woeid, locationName);
			System.out.println(locationName + " (woeid:" + woeid + ")");
			String jsonLocation = DataObjectFactory.getRawJSON(location);
			DBObject locationObj = (DBObject) JSON.parse(jsonLocation);
			locationObj.put("as_of", new DateTime(DateTimeZone.UTC).toDate());
			locationListObj.add(locationObj);
		}
		locationCollection.insert(locationListObj);
		System.out.println("Stored...");
	}

	private static void printTrendLocationWithWoeid(
			DBCollection locationCollection) {
		@SuppressWarnings("unchecked")
		List<String> names = locationCollection.distinct("name");

		for (String name : names) {
			BasicDBObject query = new BasicDBObject();
			query.put("name", name);
			int woeid = (Integer) locationCollection.findOne(query)
					.get("woeid");
			System.out.println(name + " - " + woeid);
		}
	}
}
