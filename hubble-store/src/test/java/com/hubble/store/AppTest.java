package com.hubble.store;

import java.util.Arrays;
import java.util.List;

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
    	List<String> inputParam = Arrays.asList("Google"); 
    	appRequestParam.setTrendingTopics(inputParam);
    	PersistenceHandler.getAppForTrendingTopics(appRequestParam);
    }
}
