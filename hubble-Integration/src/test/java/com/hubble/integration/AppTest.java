package com.hubble.integration;

import java.util.ArrayList;
import java.util.List;

import com.hubble.integration.handlers.IntegrationHandler;
import com.hubble.integration.model.AppDiscoveryRequest;
import com.hubble.store.common.util.HubbleLogger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	private String accessToken = "AAACEdEose0cBAE9TqSiZArLnGmxZBYAZBdzITnd3Uk0PdJblO5IL7rLP1v2n8cErCCqxjKqVdk4NC0DQkNpK5W2iKK4ZAYkUDq7YYHkUuAZDZD";
	private String userId = "431635";
	
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

    /**
     * Rigourous Test :-)
     */
    /*public void testApp()
    {
        assertTrue( true );
    }*/
    
    public void testIntegrationTestcase1() {
    	HubbleLogger.printMessage("Starting testIntegrationTestcase1...");
    	List<Integer> appList = new ArrayList<Integer>();
    	IntegrationHandler testHandler = IntegrationHandler.initializeIntegrationHandler();
    	// App request creation
    	AppDiscoveryRequest appRequest = new AppDiscoveryRequest();
    	appRequest.setAccessToken(accessToken);
    	appRequest.setUserId(userId);
    	
    	// Submit task
    	testHandler.getAppSuggestions(appRequest);
    	
    	// Get task results
    	appList = testHandler.getAppSuggestionResponse(appRequest.getUserId());
    	
    	// Print List
    	HubbleLogger.printList(appList);
    	
    	// Close handler
    	testHandler.closeIntegrationHanlder();
    	HubbleLogger.printMessage("Completing testIntegrationTestcase1");
    }
    
   /* public void testIntegrationTestcase2() {
    	HubbleLogger.printMessage("Starting testIntegrationTestcase2...");
    	
    	HubbleLogger.printMessage("Completing testIntegrationTestcase2");
    }*/
    
    
    
}
