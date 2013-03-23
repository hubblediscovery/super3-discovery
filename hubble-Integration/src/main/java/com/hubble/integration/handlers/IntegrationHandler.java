package com.hubble.integration.handlers;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.hubble.integration.model.AppDiscoveryRequest;
import com.hubble.integration.tasks.AppDiscoveryTask;

public class IntegrationHandler {
	
	// TODO verify singleton with enum
	private static IntegrationHandler integrationHandler;
	private LinkedBlockingQueue<Runnable> appDiscoveryInputQueue;
	private ExecutorService integrationThreadPool;
	private ConcurrentMap<String, Future<List<Integer>>> appDiscoveryFutures;
	
	// TODO move all constants to a common interface
	private final int initialThreadCount = 15;
	private final int totalMaxThreadCount = 30;
	private final int liveThreadInterval = 1;
	
	private IntegrationHandler() {
		this.appDiscoveryFutures = new ConcurrentHashMap<String, Future<List<Integer>>>();
		this.appDiscoveryInputQueue = new LinkedBlockingQueue<Runnable>();
		this.integrationThreadPool = new ThreadPoolExecutor(initialThreadCount, totalMaxThreadCount, 
				liveThreadInterval, TimeUnit.SECONDS, appDiscoveryInputQueue);
	}
	
	public static synchronized IntegrationHandler initializeIntegrationHandler() {
		if(integrationHandler == null) {
			integrationHandler = new IntegrationHandler();
		}
		return integrationHandler;
	}
	
	public boolean closeIntegrationHanlder() {
		// TODO handler error cases
		this.integrationThreadPool.shutdown();
		return true;
	}
	
	public boolean getAppSuggestions(AppDiscoveryRequest discoveryRequest) {
		// TODO handler error cases
		// Submit discovery request in to the thread pool
		Future<List<Integer>> alreadySubmittedFuture = appDiscoveryFutures.get(discoveryRequest.getUserId());
        if (alreadySubmittedFuture == null) {
        	submitAppSuggestionRequest(discoveryRequest);
        }
        else {
        	 // Suggestion for the user is already submitted, so just return true
        	return true;
        }
		return true;
	}
	
	private boolean submitAppSuggestionRequest(AppDiscoveryRequest discoveryRequest) {
		// Construct discovery task and submit
		AppDiscoveryTask discoveryTask = new AppDiscoveryTask(discoveryRequest);
		Future<List<Integer>> requestFuture = integrationThreadPool.submit(discoveryTask);
		appDiscoveryFutures.putIfAbsent(discoveryRequest.getUserId(), requestFuture);
		return true;
	}
	
	public List<Integer> getAppSuggestionResponse(String userId) {
		Future<List<Integer>> requestFuture = appDiscoveryFutures.get(userId);
		try {
			return requestFuture.get();
		}
		catch(InterruptedException interruptedException) {
			// TODO thread got interrupted while executing, need to recover
			interruptedException.printStackTrace();
		}
		catch(ExecutionException executionException) {
			// Discovery request resulted in exception
			// TODO need to recover
			executionException.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

	}

}
