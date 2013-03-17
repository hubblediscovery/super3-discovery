package com.hubble.store.helper;

import java.util.TreeMap;
import java.util.Map.Entry;

import com.hubble.store.model.*;

//Data structure to store all App Store feed entries from 
//Example all entries in the link
//http://itunes.apple.com/us/rss/topfreeapplications/limit=10/xml

public class AppStoreFeed {

	private TreeMap<Integer, HubbleAppData> appStoreFeeds;
	private String feedType;
	private String feedID;
	private String feedTitle;
	private Integer feedEntryCount;

	public AppStoreFeed() {
		appStoreFeeds = new TreeMap<Integer, HubbleAppData>();
		feedType = new String();
		feedID = new String();
		feedEntryCount = new Integer(0);
	}

	public TreeMap<Integer, HubbleAppData> getAppStoreFeeds() {
		return appStoreFeeds;
	}

	public void setAppStoreFeeds(TreeMap<Integer, HubbleAppData> appStoreFeeds) {
		this.appStoreFeeds = appStoreFeeds;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getFeedID() {
		return feedID;
	}

	public void setFeedID(String feedID) {
		this.feedID = feedID;
	}

	public String getFeedTitle() {
		return feedTitle;
	}

	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}

	public Integer getFeedEntryCount() {
		// return feedEntryCount;
		feedEntryCount = (appStoreFeeds != null) ? appStoreFeeds.size() : 0;
		return feedEntryCount;
	}

	public void setFeedEntryCount(Integer feedEntryCount) {
		this.feedEntryCount = feedEntryCount;
	}

	public void addEntryWithKey(Integer key, HubbleAppData value) {
		appStoreFeeds.put(key, value);
	}

	// Util method to verify the contents
	public void printAppStoreEntries() {
		for (Entry<Integer, HubbleAppData> entry : this.getAppStoreFeeds()
				.entrySet()) {
			HubbleAppData a = entry.getValue();
			System.out.println(entry.getKey().toString() + " - "
					+ a.getAppName());
		}
	}

}
