package com.appdisc.trend.twitter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.appdisc.trend.profile.Location;
import com.appdisc.trend.profile.TrendingProfile;
import com.mongodb.DBObject;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:19:20 AM
 * 
 */
public class TwitterTrendingProfile implements TrendingProfile, DBObject {

	private static final long serialVersionUID = -1313938400288317068L;
	private Date trendDate;
	private Location location;
	private Map<String, Set<String>> trendingTopicToTweetsMap = new HashMap<String, Set<String>>();
	private Map<String, Set<String>> trendingTopicToKeywordsMap = new HashMap<String, Set<String>>();

	public Date getTrendDate() {
		return trendDate;
	}

	public void setTrendDate(Date trendDate) {
		this.trendDate = trendDate;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Set<String>> getTrendingTopicToTweetsMap() {
		return trendingTopicToTweetsMap;
	}

	public void setTrendingTopicToTweetsMap(
			Map<String, Set<String>> trendingTopicToTweetsMap) {
		this.trendingTopicToTweetsMap = trendingTopicToTweetsMap;
	}

	public Map<String, Set<String>> getTrendingTopicToKeywordsMap() {
		return trendingTopicToKeywordsMap;
	}

	public void setTrendingTopicToKeywordsMap(
			Map<String, Set<String>> trendingTopicToKeywordsMap) {
		this.trendingTopicToKeywordsMap = trendingTopicToKeywordsMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((trendDate == null) ? 0 : trendDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterTrendingProfile other = (TwitterTrendingProfile) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (trendDate == null) {
			if (other.trendDate != null)
				return false;
		} else if (!trendDate.equals(other.trendDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrendingProfile [trendDate="
				+ trendDate
				+ ", location="
				+ location.getPlace()
				+ ", trendingTopics="
				+ getTopicsPrintable(this.getTrendingTopicToTweetsMap()
						.keySet()) + "]";
	}

	private String getTopicsPrintable(Set<String> trendingTopics) {
		StringBuilder topics = new StringBuilder();
		for (String topic : trendingTopics) {
			topics.append(topic).append(", ");
		}
		return topics.substring(0, topics.length() - 2);
	}

	@Override
	public int compareTo(TrendingProfile profile) {
		return this.trendDate.compareTo(profile.getTrendDate());
	}

	@Override
	public boolean containsField(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public boolean containsKey(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(BSONObject arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putAll(Map arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object removeField(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map toMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPartialObject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markAsPartialObject() {
		// TODO Auto-generated method stub

	}
}
