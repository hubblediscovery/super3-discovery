package com.hubble.store.helper;

public interface ConcurrentCrawler {
	
	public final int crawlerBatchSize = 20;
	
	// API to start crawling
	public boolean startCrawling();
	
	// API to start enriching crawled app store data
	public boolean enrich();
}
