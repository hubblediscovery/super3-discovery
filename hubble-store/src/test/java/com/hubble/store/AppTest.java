package com.hubble.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.appdisc.trend.profile.TrendingProfile;
import com.appdisc.trend.twitter.TwitterTrendingProfile;
import com.hubble.store.common.request.AppRequestParameter;
import com.hubble.store.common.util.HubbleLogger;
import com.hubble.store.persistence.PersistenceHandler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
        HubbleLogger.printMessage("Testing - getAppForTrendingTopics");
        getAppForTrendingTopics();
        
    }
    
    public void getAppForTrendingTopics() {
    	AppRequestParameter appRequestParam = new AppRequestParameter();
    	
    	Set<String> keywords = new HashSet<String>(Arrays.asList("iPad", "iOS"));
    	Map<String, Set<String>> trendToKeywordMap = new HashMap<String, Set<String>>();
    	trendToKeywordMap.put("Apple", keywords);
    	
    	TwitterTrendingProfile trendProfile = new TwitterTrendingProfile();
    	trendProfile.setTrendingTopicToKeywordsMap(trendToKeywordMap);

    	List<TrendingProfile> inputParam = new ArrayList<TrendingProfile>();
    	inputParam.add(trendProfile);
   	
    	appRequestParam.setTrendingTopics(inputParam);
    	PersistenceHandler.getAppForTrendingTopics(appRequestParam);
    }
}
