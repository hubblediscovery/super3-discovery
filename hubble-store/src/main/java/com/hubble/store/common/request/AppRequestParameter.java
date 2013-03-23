package com.hubble.store.common.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.appdisc.trend.profile.TrendingProfile;

public class AppRequestParameter{
	
	private List<TrendingProfile> currentCityTrendingProfile;
	private List<TrendingProfile> hometownTrendingProfile;
	private List<TrendingProfile> deviceLocationTrendingProfile;
	private List<String> userPageLikes;
	private Map<String, Set<String>> trendingTopicsToKeyworkMap;
	
	public AppRequestParameter() {
		new AppRequestParameter(null, null, null, null, null);
	}
	
	public AppRequestParameter(List<TrendingProfile> currentCityTrendingProfile, List<TrendingProfile> hometownTrendingProfile, 
			List<TrendingProfile> deviceLocationTrendingProfile,
			List<String> pUserPageLikes, Map<String, Set<String>> pTrendingTopicsToKeyworkMap) {
		this.currentCityTrendingProfile = currentCityTrendingProfile;
		this.hometownTrendingProfile = hometownTrendingProfile;
		this.deviceLocationTrendingProfile = deviceLocationTrendingProfile;
		this.userPageLikes = pUserPageLikes;
		this.trendingTopicsToKeyworkMap = pTrendingTopicsToKeyworkMap;
	}
	
	public List<TrendingProfile> getCurrentCityTrendingProfile() {
		return currentCityTrendingProfile;
	}

	public void setCurrentCityTrendingProfile(
			List<TrendingProfile> currentCityTrendingProfile) {
		this.currentCityTrendingProfile = currentCityTrendingProfile;
	}

	public List<TrendingProfile> getHometownTrendingProfile() {
		return hometownTrendingProfile;
	}

	public void setHometownTrendingProfile(
			List<TrendingProfile> hometownTrendingProfile) {
		this.hometownTrendingProfile = hometownTrendingProfile;
	}

	public List<TrendingProfile> getDeviceLocationTrendingProfile() {
		return deviceLocationTrendingProfile;
	}

	public void setDeviceLocationTrendingProfile(
			List<TrendingProfile> deviceLocationTrendingProfile) {
		this.deviceLocationTrendingProfile = deviceLocationTrendingProfile;
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
