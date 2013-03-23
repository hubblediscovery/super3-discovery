package com.hubble.integration.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.appdisc.trend.profile.TrendProfiler;
import com.appdisc.trend.profile.TrendingProfile;
import com.hubble.integration.model.AppDiscoveryRequest;
import com.hubble.store.common.request.AppRequestParameter;
import com.hubble.store.common.util.HubbleLogger;
import com.hubble.store.services.StoreIntegrationService;
import com.hubble.userprofile.service.ServiceFactory;
import com.hubble.userprofile.service.UserProfileExternalService;
import com.hubble.userprofile.types.ClientLoginType;
import com.hubble.userprofile.types.UserProfileResponse;

/**
 * 
 * Integration handler to manage and process results from User profile,
 * Trending and Store module
 * 
 * @author deepakvp
 *
 */

public class AppDiscoveryTask implements Callable<List<Integer>> {
	
	private AppDiscoveryRequest discoveryRequest;
	
	// User Profile
	private UserProfileExternalService userProfileExternalService;
	private UserProfileResponse userProfileResponse;
	
	// Trending profile
	private List<TrendingProfile> currentCityTrendingProfile;
	private List<TrendingProfile> hometownTrendingProfile;
	private List<TrendingProfile> deviceLocationTrendingProfile;
	
	private TrendProfiler trendProfiler;
	
	// Store 
	private AppRequestParameter appRequestParameter = null;
	private List<Integer> appList = null;
	
	public AppDiscoveryTask(AppDiscoveryRequest discoveryRequest) {
		this.discoveryRequest = discoveryRequest;
		this.currentCityTrendingProfile = new ArrayList<TrendingProfile>();
		this.hometownTrendingProfile = new ArrayList<TrendingProfile>();
		this.deviceLocationTrendingProfile = new ArrayList<TrendingProfile>();
		this.trendProfiler = new TrendProfiler();
	}
	
	public List<Integer> call() throws Exception {
		//TODO Handle multiple use cases 
		try {
		// Create User profile object
			userProfileExternalService = ServiceFactory.getUserProfileExternalService(
	                ClientLoginType.FACEBOOK, discoveryRequest.getAccessToken(), discoveryRequest.getUserId()); 
				
		// Get user profile response
		userProfileResponse = userProfileExternalService.getUserProfileResponse();
		
		// Call getTrends for current city
		currentCityTrendingProfile = trendProfiler.getTrends(userProfileResponse.getCurrentCity(), 
				new Date(), 1);
		
		// Call getTrends for home town
		hometownTrendingProfile = (trendProfiler.getTrends(userProfileResponse.getHometown(), 
				new Date(), 1));
		
		// Call getTrends for current device location
		deviceLocationTrendingProfile = trendProfiler.getTrends(discoveryRequest.getCurrentDeviceLocation().getLatitude(),  
				discoveryRequest.getCurrentDeviceLocation().getLongitude(), new Date(), 1);
		
		// Call store API to get list of qualifying Apps
		// TODO modify app request parameter to accept all trending profiles
		appRequestParameter = new AppRequestParameter();
		appRequestParameter.setCurrentCityTrendingProfile(currentCityTrendingProfile);
		appRequestParameter.setCurrentCityTrendingProfile(hometownTrendingProfile);
		appRequestParameter.setCurrentCityTrendingProfile(deviceLocationTrendingProfile);
		appList = StoreIntegrationService.getAppForTrendingTopics(appRequestParameter);	
		
		HubbleLogger.printList(appList);
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
