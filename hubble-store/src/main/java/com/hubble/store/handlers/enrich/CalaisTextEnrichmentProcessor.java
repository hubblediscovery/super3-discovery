package com.hubble.store.handlers.enrich;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//import java.lang.reflect.Type;
//import com.google.gson.reflect.TypeToken;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.hubble.store.common.util.HubbleLogger;
import com.hubble.store.model.HubbleAppData;
import com.hubble.store.model.HubbleEnrichedAppData;
import com.hubble.store.model.HubbleEnrichedAppDataId;
import com.hubble.store.persistence.PersistenceHandler;

import org.hibernate.ScrollableResults;

public class CalaisTextEnrichmentProcessor {
	
	public CalaisTextEnrichmentProcessor() {
		
	}
	
	public static void processEnrichment() {
		ScrollableResults scrollableResult = PersistenceHandler.readAppData();
		HubbleAppData currentAppData = null;
		HubbleEnrichedAppData currentEnrichedData = null;
		HubbleEnrichedAppDataId currentEnrichedAppId = null;
		int count = 0;
		ThreadPoolExecutor threadPoolExecutor = null;
		LinkedList<Future<List<String>>> futures = null;
		Gson gson = new Gson();
		String jsonNames = new String();
		//Type type = new TypeToken<List<String>>(){}.getType();
		//HashMap<String, String> map = new HashMap<String,String>();
		
		try {
		
			while(scrollableResult.next()) {
				HubbleLogger.printMessage("Processing for index - " + count);
				currentAppData = (HubbleAppData) scrollableResult.get(0);
				count++;
				//HubbleLogger.printMessage(currentAppData.getAppName());
				
				threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>());
				futures = new LinkedList<Future<List<String>>>();
				//HubbleLogger.printMessage(currentAppData.getAppDescription());
	           	futures.add(threadPoolExecutor.submit(new CalaisTextEnrichmentTask(currentAppData.getAppDescription())));
			//}
			threadPoolExecutor.shutdown();
        
	        // Every second we print our progress
	        while (!threadPoolExecutor.isTerminated()) {
	        	threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS);
	            int progress = Math.round((threadPoolExecutor.getCompletedTaskCount() * 100) /
	            		threadPoolExecutor.getTaskCount());
	
	            System.out.println(progress + "% done (" + threadPoolExecutor.getCompletedTaskCount() +
	                               " download task has completed).");
	        }
	        try {
	        while(futures.size() != 0 ) {
            	Future<List<String>> future = futures.poll();
                jsonNames = gson.toJson(future.get());
                
                // Construct Hubble enriched data
                currentEnrichedAppId = new HubbleEnrichedAppDataId(currentAppData.getHubbleAppId(), 
                		currentAppData.getAppId(), "{ \"keywork\" : " + jsonNames + " }");
                currentEnrichedData = new HubbleEnrichedAppData(currentEnrichedAppId, currentAppData);
                
            	//HubbleLogger.printMessage(jsonNames);
            	
            }
	        }
	        catch(ExecutionException e1) {
	        	HubbleLogger.printErrorMessage("Exception while processing Hubble id - " + currentAppData.getHubbleAppId());
	        	continue;
	        }
	        
	        // Persist enriched keywords
	        PersistenceHandler.persistEnrichedAppData(currentEnrichedData);
	        
			} // while(scrollableResult.next() && count == 0 )
	        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
