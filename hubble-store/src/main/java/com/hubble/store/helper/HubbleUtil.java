package com.hubble.store.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HubbleUtil {

	public HashMap<String, String> categoryIDMap;
	public HashMap<String, String> gamesCategoryIDMap;
	public HashMap<String, String> rssTopFreeApplicationFeedURLs;
	public HashMap<String, String> rssTopFreeApplicationGamesFeedURLs;
	public HashMap<String, List<String>> rssFeedURLs;

	public HubbleUtil() {
		this.loadAppStoreCategory();
		this.loadAppStoreRSSURLs();
		this.loadAppStoreGamesCategory();
		// this.loadRSSFeedURL();
	}

	// App Store Feed Entry Tag Elements
	public static String APPSTORE_FEED_ENTRY_ELEMENT = "entry";
	public static String APPSTORE_FEED_UPDATED_ELEMENT = "updated";
	public static String APPSTORE_FEED_ID_ELEMENT = "id";
	public static String APPSTORE_FEED_TITLE_ELEMENT = "title";
	public static String APPSTORE_FEED_SUMMARY_ELEMENT = "summary";
	public static String APPSTORE_FEED_LINK_ELEMENT = "link";
	public static String APPSTORE_FEED_CATEGORY_ELEMENT = "category";

	public static String APPSTORE_FEED_IM_NAME_ELEMENT = "im:name";
	public static String APPSTORE_FEED_IM_CONTENTTYPE_ELEMENT = "im:contentType";
	public static String APPSTORE_FEED_IM_ARTIST_ELEMENT = "im:artist";
	public static String APPSTORE_FEED_IM_PRICE_ELEMENT = "im:price";
	public static String APPSTORE_FEED_IM_IMAGE_ELEMENT = "im:image";
	public static String APPSTORE_FEED_IM_RELEASEDATE_ELEMENT = "im:releaseDate";

	public static String APPSTORE_FEED_IM_APPID_ATTR = "im:id";
	public static String APPSTORE_FEED_IM_BUNDLEID_ATTR = "im:bundleId";
	public static String APPSTORE_FEED_IM_CATEGORYID_ATTR = "term";
	public static String APPSTORE_FEED_AMOUNT_ATTR = "amount";
	public static String APPSTORE_FEED_CURRENCY_ATTR = "currency";

	// App Store Feed Tag Elements
	public static String APPSTORE_FEED_HEADER_ID_ELEMENT = "id";
	public static String APPSTORE_FEED_HEADER_TITLE_ELEMENT = "title";
	public static String APPSTORE_FEED_HEADER_UPDATED_ELEMENT = "updated";

	// App Store Feed Tag Types
	public static String APPSTORE_FEED_TAG_TYPE = "Feed";
	public static String APPSTORE_ENTRY_TAG_TYPE = "Entry";

	// App Store Feed
	public static String APPSTORE_FEED_ELEMENT = "Feed";

	// 1 - Top Free, 2 - Top Free Games, 3 - Top Paid, 4 - Top Paid Games, 5 -
	// Top Grossing, 6 - Top Grossing Games
	// 7 - Top New, 8 - Top New Games, 9 - Top New Free, 10 - Top New Free
	// Games, 11 - Top New Paid, 12 - Top New Paid Games
	public HashMap<String, List<String>> getRSSFeedURLs() {
		rssFeedURLs = new HashMap<String, List<String>>();
		List<String> urlList = new ArrayList<String>();

		// Top Free Application - General Category
		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/topfreeapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("1"), urlList);

		// Top Free Application - Games Sub-Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : gamesCategoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/topfreeapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("2"), urlList);

		// Top Paid Application - General Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/toppaidapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("3"), urlList);

		// Top Paid Application - Games Sub-Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : gamesCategoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/toppaidapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("4"), urlList);

		// Top Grossing Application - General Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/topgrossingapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("5"), urlList);

		// Top Grossing Application - Games Sub-Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : gamesCategoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/topgrossingapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("6"), urlList);

		// Top New Application - General Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/newapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("7"), urlList);

		// Top New Application - Games Sub-Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : gamesCategoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/newapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("8"), urlList);

		// Top New FreeApplication - General Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/newfreeapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("9"), urlList);

		// Top New FreeApplication - Games Sub-Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : gamesCategoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/newfreeapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("10"), urlList);

		// Top New Paid Application - General Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/newpaidapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("11"), urlList);

		// Top New Paid Application - Games Sub-Category
		urlList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : gamesCategoryIDMap.entrySet()) {
			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/newpaidapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			urlList.add(feedURL.toString());

		}
		rssFeedURLs.put(new String("12"), urlList);

		return rssFeedURLs;
	}

	public void loadAppStoreCategory() {
		categoryIDMap = new HashMap<String, String>();

		categoryIDMap.put(new String("6000"), new String("Business"));
		categoryIDMap.put(new String("6001"), new String("Weather"));
		categoryIDMap.put(new String("6002"), new String("Utilities"));
		categoryIDMap.put(new String("6003"), new String("Travel"));
		categoryIDMap.put(new String("6004"), new String("Sports"));
		categoryIDMap.put(new String("6005"), new String("Social Networking"));
		categoryIDMap.put(new String("6006"), new String("Reference"));
		categoryIDMap.put(new String("6007"), new String("Productivity"));
		categoryIDMap.put(new String("6008"), new String("Photography"));
		categoryIDMap.put(new String("6009"), new String("News"));
		categoryIDMap.put(new String("6010"), new String("Navigation"));
		categoryIDMap.put(new String("6011"), new String("Music"));
		categoryIDMap.put(new String("6012"), new String("Lifestyle"));
		categoryIDMap.put(new String("6016"), new String("Entertainment"));
		categoryIDMap.put(new String("6017"), new String("Education"));
		categoryIDMap.put(new String("6018"), new String("Books"));
		categoryIDMap.put(new String("6020"), new String("Medical"));

		/*
		 * categoryIDMap.put(new String("7001"),new String("Action"));
		 * categoryIDMap.put(new String("7002"),new String("Adventure"));
		 * categoryIDMap.put(new String("7003"),new String("Arcade"));
		 * categoryIDMap.put(new String("7004"),new String("Board"));
		 * categoryIDMap.put(new String("7005"),new String("Card"));
		 * categoryIDMap.put(new String("7006"),new String("Casino"));
		 * categoryIDMap.put(new String("7007"),new String("Dice"));
		 * categoryIDMap.put(new String("7008"),new String("Educational"));
		 * categoryIDMap.put(new String("7009"),new String("Family"));
		 * categoryIDMap.put(new String("7010"),new String("Kids"));
		 * categoryIDMap.put(new String("7011"),new String("Music"));
		 * categoryIDMap.put(new String("7012"),new String("Puzzle"));
		 * categoryIDMap.put(new String("7013"),new String("Racing"));
		 * categoryIDMap.put(new String("7014"),new
		 * String("Healthcare & Fitness")); categoryIDMap.put(new
		 * String("7015"),new String("Simulation")); categoryIDMap.put(new
		 * String("7016"),new String("Sports")); categoryIDMap.put(new
		 * String("7017"),new String("Strategy")); categoryIDMap.put(new
		 * String("7018"),new String("Trivia")); categoryIDMap.put(new
		 * String("7019"),new String("Word"));
		 */
	}

	public void loadAppStoreGamesCategory() {
		gamesCategoryIDMap = new HashMap<String, String>();

		gamesCategoryIDMap.put(new String("7001"), new String("Action"));
		gamesCategoryIDMap.put(new String("7002"), new String("Adventure"));
		gamesCategoryIDMap.put(new String("7003"), new String("Arcade"));
		gamesCategoryIDMap.put(new String("7004"), new String("Board"));
		gamesCategoryIDMap.put(new String("7005"), new String("Card"));
		gamesCategoryIDMap.put(new String("7006"), new String("Casino"));
		gamesCategoryIDMap.put(new String("7007"), new String("Dice"));
		gamesCategoryIDMap.put(new String("7008"), new String("Educational"));
		gamesCategoryIDMap.put(new String("7009"), new String("Family"));
		gamesCategoryIDMap.put(new String("7010"), new String("Kids"));
		gamesCategoryIDMap.put(new String("7011"), new String("Music"));
		gamesCategoryIDMap.put(new String("7012"), new String("Puzzle"));
		gamesCategoryIDMap.put(new String("7013"), new String("Racing"));
		gamesCategoryIDMap.put(new String("7014"), new String(
				"Healthcare & Fitness"));
		gamesCategoryIDMap.put(new String("7015"), new String("Simulation"));
		gamesCategoryIDMap.put(new String("7016"), new String("Sports"));
		gamesCategoryIDMap.put(new String("7017"), new String("Strategy"));
		gamesCategoryIDMap.put(new String("7018"), new String("Trivia"));
		gamesCategoryIDMap.put(new String("7019"), new String("Word"));
	}

	public void loadAppStoreRSSURLs() {

		rssTopFreeApplicationFeedURLs = new HashMap<String, String>();

		// Top Free Application - General Category
		rssTopFreeApplicationFeedURLs
				.put(new String("Top Free Application"),
						new String(
								"https://itunes.apple.com/us/rss/topfreeapplications/limit=400/xml"));

		for (Map.Entry<String, String> entry : categoryIDMap.entrySet()) {
			StringBuffer feedURLKey = new StringBuffer(
					"Top Free Application - ");
			feedURLKey.append(entry.getKey());

			StringBuffer feedURL = new StringBuffer(
					"https://itunes.apple.com/us/rss/topfreeapplications/limit=400/genre=");
			feedURL.append(entry.getKey());
			feedURL.append(new String("/xml"));

			rssTopFreeApplicationFeedURLs.put(feedURLKey.toString(),
					feedURL.toString());

		}

	}

}
