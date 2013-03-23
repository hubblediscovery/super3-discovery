package com.hubble.store.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.appdisc.trend.profile.TrendingProfile;
import com.hubble.store.common.request.AppRequestParameter;
import com.hubble.store.common.util.HubbleLogger;
import com.hubble.store.model.HubbleAppData;
import com.hubble.store.model.HubbleEnrichedAppData;

public class PersistenceHandler {
	
	private static Session session = null;
	
	public static void initializeSession() {
	    session = PersistenceManager.getSessionFactory().openSession();
	}
	
	@SuppressWarnings("unchecked")
	public static void persistAppData(HubbleAppData appData) {
		
		session.beginTransaction();
		
		// Check if the App data row already exists, if so, get the record and update only data in it
		Criteria criteria = session.createCriteria(HubbleAppData.class)
			    .add(Restrictions.eq("appId", appData.getAppId()));
		List<HubbleAppData> data =  criteria.list();
		
		if(data.size() != 0) {
			// Update app data
			//System.out.println("Updating App data with App Name - " + appData.getAppName());
			//TODO un comment later
			
		/*	HubbleAppData hubbleAppData = (HubbleAppData) data.get(0);
			hubbleAppData.setAppDescription(appData.getAppDescription());
			hubbleAppData.setAppLastUpdatedDate(appData.getAppLastUpdatedDate());
			hubbleAppData.setAppTitle(appData.getAppTitle());
			hubbleAppData.setAppDeveloperInfo(appData.getAppDeveloperInfo());
			hubbleAppData.setAppPrimaryCategory(appData.getAppPrimaryCategory());
			hubbleAppData.setAppSecondaryCategory(appData.getAppSecondaryCategory());
			hubbleAppData.setAppUnitPrice(appData.getAppUnitPrice());
			hubbleAppData.setAppUnitPrice(appData.getAppUnitPrice());
			hubbleAppData.setAppUnitCurrency(appData.getAppUnitCurrency());*/
			
		}
		else {
			session.save(appData);
		}
		session.getTransaction().commit();
	}
	
	public static HubbleAppData getCompleteAppData(int hubbleAppId) {
		
		HubbleAppData appData = null;
		
		Criteria criteria = session.createCriteria(HubbleAppData.class)
			    .add(Restrictions.eq("hubbleAppId", hubbleAppId));
		@SuppressWarnings("unchecked")
		List<HubbleAppData> data = criteria.list();
		
		if(data.size() != 0) {
			return data.get(0);
		}
		
		return appData;
		
		
	}
	
	public static ScrollableResults readAppData() {
		
		if(session == null) initializeSession();
		Criteria criteria = session.createCriteria(HubbleAppData.class);
			    
		return criteria.scroll(ScrollMode.FORWARD_ONLY);
	}
	
	@SuppressWarnings("unchecked")
	public static void persistEnrichedAppData(HubbleEnrichedAppData data) {
		if(session == null) initializeSession();
		
		session.beginTransaction();
		
		// Check if the App data row already exists, if so, get the record and update only data in it
		Criteria criteria = session.createCriteria(HubbleEnrichedAppData.class)
			    .add(Restrictions.eq("id.hubbleAppId", data.getHubbleAppData().getHubbleAppId()));
		List<HubbleAppData> existingData =  criteria.list();
		
		if(existingData.size() != 0) {
			
		}
		else {
			session.save(data);
		}
		
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static boolean existsInDB(String tableName, Integer hubbleAppId) {
		
		Criteria criteria = session.createCriteria(tableName)
			    .add(Restrictions.eq("hubbleAppId", hubbleAppId));
		List<HubbleAppData> existingData =  criteria.list();
		
		if(existingData.size() != 0) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getAppForTrendingTopics(AppRequestParameter appRequestParam) {
		HubbleLogger.printMessage("Starting getAppForTrendingTopics");
		
		List<Integer> resultAppList = new ArrayList<Integer>();
		
		if(session == null) initializeSession();
		 
		Criteria c = session.createCriteria(HubbleAppData.class, "HubbleAppData");
		c.createAlias("hubbleEnrichedAppDatas", "hubbleEnrichedAppDatas"); // inner join by default
		c.add(constructTitleDisjunctionForTrendingTopics(appRequestParam.getCurrentCityTrendingProfile()));
		c.add(constructDescriptionDisjunctionForTrendingTopics(appRequestParam.getCurrentCityTrendingProfile()));
		
		List<HubbleAppData> list = c.list();
		
		for(HubbleAppData app : list) {
			HubbleLogger.printMessage(app.getAppName());
			resultAppList.add(app.getHubbleAppId());
		}
		
		HubbleLogger.printMessage("Completed getAppForTrendingTopics");
		return resultAppList;
		
	}
	
	private static Disjunction constructTitleDisjunctionForTrendingTopics(List<TrendingProfile> inputList) {
		
		Map<String, Set<String>> trendingTopicToKeywordsMap = null;
		Set<String> keywordsSet = null;
		Disjunction orFilter = Restrictions.disjunction();
		
		for(TrendingProfile trendingProfile : inputList) {
			// Get trend key and its corresponding keywords map
			trendingTopicToKeywordsMap = trendingProfile.getTrendingTopicToKeywordsMap();
			
			// Iterate trend key map
			for (Map.Entry<String, Set<String>> mapEntry : trendingTopicToKeywordsMap.entrySet()) { 
				keywordsSet = mapEntry.getValue();
			
				for(String str: keywordsSet) {
					orFilter.add(Restrictions.like("HubbleAppData.appTitle", "%" + str + "%"));
			    }
			}
		}
		return orFilter;
	}
	
	private static Disjunction constructDescriptionDisjunctionForTrendingTopics(List<TrendingProfile> inputList) {
		
		Map<String, Set<String>> trendingTopicToKeywordsMap = null;
		Set<String> keywordsSet = null;
		Disjunction orFilter = Restrictions.disjunction();
		
		for(TrendingProfile trendingProfile : inputList) {
			// Get trend key and its corresponding keywords map
			trendingTopicToKeywordsMap = trendingProfile.getTrendingTopicToKeywordsMap();
			
			// Iterate trend key map
			for (Map.Entry<String, Set<String>> mapEntry : trendingTopicToKeywordsMap.entrySet()) { 
				keywordsSet = mapEntry.getValue();
			
				for(String str: keywordsSet) {
					orFilter.add(Restrictions.like("HubbleAppData.appDescription", "%" + str + "%"));
			    }
			}
		}
		return orFilter;
		
		/*Disjunction orFilter = Restrictions.disjunction();
		
		for(TrendingProfile trendingProfile : inputList) {
			orFilter.add(Restrictions.like("HubbleAppData.appDescription", "%" + str + "%"));
		}
		
		return orFilter;*/
	}
	
	
	
}
