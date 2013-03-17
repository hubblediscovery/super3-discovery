package com.hubble.store.integration;

import java.util.List;

import com.hubble.store.common.request.AppRequestParameter;
import com.hubble.store.model.HubbleAppData;
import com.hubble.store.persistence.PersistenceHandler;


public class StoreIntegrationService {
	
	// Integration API to get additional details of the App 
	public static HubbleAppData getCompleteAppData(int hubbleAppId) {
		HubbleAppData appData = null;
		
		appData = PersistenceHandler.getCompleteAppData(hubbleAppId);
		if(appData == null) {
			// Handle case where invalid app id was passed 
		}
			
		return appData;	
	}
	
	public static List<Integer> getAppForTrendingTopics(AppRequestParameter appRequestParam) {
		
		List<Integer> appList = null;
		
		appList = PersistenceHandler.getAppForTrendingTopics(appRequestParam);
		
		if(appList == null) {
			// Handle when no qualifying apps were found in the DB
		}
		
		return  appList;
	}
}
