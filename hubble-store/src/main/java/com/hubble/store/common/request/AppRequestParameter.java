package com.hubble.store.common.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.appdisc.trend.twitter.TwitterTrendingProfile;

public class AppRequestParameter{
	
	private List<TwitterTrendingProfile> trendingTopics = null;
	private List<String> userPageLikes = null;
	private Map<String, Set<String>> trendingTopicsToKeyworkMap = null;
	
	public AppRequestParameter() {
		new AppRequestParameter(null, null, null);
	}
	
	public AppRequestParameter(List<TwitterTrendingProfile> pTrendingTopics, List<String> pUserPageLikes, Map<String, Set<String>> pTrendingTopicsToKeyworkMap) {
		this.trendingTopics = pTrendingTopics;
		this.userPageLikes = pUserPageLikes;
		this.trendingTopicsToKeyworkMap = pTrendingTopicsToKeyworkMap;
	}
	
	public List<TwitterTrendingProfile> getTrendingTopics() {
		return trendingTopics;
	}
	public void setTrendingTopics(List<TwitterTrendingProfile> trendingTopics) {
		this.trendingTopics = trendingTopics;
	}
	public List<String> getUserPageLikes() {
		return userPageLikes;
	}
	public void setUserPageLikes(List<String> userPageLikes) {
		this.userPageLikes = userPageLikes;
	}
	public Map<String, Set<String>> getTrendingTopicsToKeyworkMap() {
		return trendingTopicsToKeyworkMap;
	}
	public void setTrendingTopicsToKeyworkMap(
			Map<String, Set<String>> trendingTopicsToKeyworkMap) {
		this.trendingTopicsToKeyworkMap = trendingTopicsToKeyworkMap;
	}
	
	
}
