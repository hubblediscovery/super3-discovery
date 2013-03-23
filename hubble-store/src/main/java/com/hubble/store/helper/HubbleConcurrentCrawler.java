package com.hubble.store.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.hubble.store.handlers.HubbleConcurrentDownloader;
import com.hubble.store.handlers.enrich.CalaisTextEnrichmentProcessor;
import com.hubble.store.model.HubbleAppData;
import com.hubble.store.persistence.PersistenceHandler;

public class HubbleConcurrentCrawler implements ConcurrentCrawler {
	public static final int APPLE_APPSTORE_CRAWLER = 1;
	public static final int GOOGLE_PLAY_CRAWLER = 2;
	public static final int MICROSOFT_MARKETPLACE_CRAWLER = 3;
	public static final int AMAZON_APPSTORE_CRAWLER = 4;
	public static ArrayList<String> rssFeedURLs = new ArrayList<String>();
	private final static HubbleUtil hubbleUtil = new HubbleUtil();

	public static int crawlerType = 0;
	
	private static HubbleConcurrentCrawler crawler;
	
	private HubbleConcurrentCrawler() {
		
	}
	
	/**
	 * Initialization method for the crawler
	 * 
	 * @return A singleton HubbleConcurrentCrawler object
	 */
	public static synchronized HubbleConcurrentCrawler initializeCrawler() {
		if(crawler == null) {
			crawler = new HubbleConcurrentCrawler();
		}
		return crawler;
	}
	
	public boolean startCrawling() {
		return true;
	}
	
	public boolean enrich() {
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			PersistenceHandler.initializeSession();

			// Thread Pool to fetch RSS feeds of Apps Store
			System.out.println("Current Heap Size = "
					+ Runtime.getRuntime().totalMemory());
			System.out.println("RSS Download started");
			int runCount = 1;

			for (Map.Entry<String, List<String>> entry : hubbleUtil
					.getRSSFeedURLs().entrySet()) {

				System.out.println("Batch Run - " + entry.getKey());
				ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
						15, 15, 1, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>());
				LinkedList<Future<String>> futures = new LinkedList<Future<String>>();

				for (String feedURL : entry.getValue()) {
					futures.add(threadPoolExecutor
							.submit(new HubbleConcurrentDownloader(feedURL,
									entry.getKey())));
				}

				System.out.println("Total task submitted - " + futures.size());
				threadPoolExecutor.shutdown();

				// Every second we print our progress
				while (!threadPoolExecutor.isTerminated()) {
					threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS);
					int progress = Math.round((threadPoolExecutor
							.getCompletedTaskCount() * 100)
							/ threadPoolExecutor.getTaskCount());

					System.out.println(progress + "% done ("
							+ threadPoolExecutor.getCompletedTaskCount()
							+ " download task has completed).");
				}

				System.out.println("RSS Download completed");

				// Process RSS Feed
				ThreadPoolExecutor threadPoolParseExecutor = new ThreadPoolExecutor(
						15, 15, 1, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>());
				List<Future<AppStoreFeed>> appStoreFeedFutures = new ArrayList<Future<AppStoreFeed>>(
						40);

				while (futures.size() != 0) {
					Future<String> future = futures.poll();
					appStoreFeedFutures
							.add(threadPoolParseExecutor
									.submit(new HubbleXMLParser(future.get()
											.toString())));
				}

				System.out.println("Total Parsing task submitted - "
						+ appStoreFeedFutures.size());

				threadPoolParseExecutor.shutdown();

				// Every second we print our progress
				while (!threadPoolParseExecutor.isTerminated()) {
					threadPoolParseExecutor.awaitTermination(1,
							TimeUnit.SECONDS);
					int progress = Math.round((threadPoolParseExecutor
							.getCompletedTaskCount() * 100)
							/ threadPoolParseExecutor.getTaskCount());

					System.out.println(progress + "% done ("
							+ threadPoolParseExecutor.getCompletedTaskCount()
							+ " parsing task has completed).");
				}

				System.out.println("Parsing has completed.");

				for (Future<AppStoreFeed> future : appStoreFeedFutures) {
					AppStoreFeed appStoreFeed = (AppStoreFeed) future.get();

					// Save parsed data
					System.out.println("Persisting in DB ....  ");
					for (Entry<Integer, HubbleAppData> data : appStoreFeed
							.getAppStoreFeeds().entrySet()) {
						HubbleAppData appData = (HubbleAppData) data.getValue();
						PersistenceHandler.persistAppData(appData);
					}
				}

				System.out.println("App Data successfully stored in DB.");
				System.out.print("Batch Run - " + runCount + " - Completed.");
				runCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Enrich Data
		CalaisTextEnrichmentProcessor.processEnrichment();

	}

}
