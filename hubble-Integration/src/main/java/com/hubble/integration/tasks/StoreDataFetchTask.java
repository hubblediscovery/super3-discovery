package com.hubble.integration.tasks;

import com.hubble.store.model.HubbleAppData;
import com.hubble.store.services.StoreIntegrationService;

import java.util.concurrent.Callable;

/**
 * Task to fetch Hubble App data by giving hubble App id
 * 
 * @author deepakvp
 *
 */

public class StoreDataFetchTask implements Callable<HubbleAppData>{
	
	private int hubbleAppId;
	
	public StoreDataFetchTask(int hubbleAppId) {
		this.hubbleAppId = hubbleAppId;
	}

	public HubbleAppData call() throws Exception {
		// Construct App request parameter
		return StoreIntegrationService.getCompleteAppData(this.hubbleAppId);
	}
}
