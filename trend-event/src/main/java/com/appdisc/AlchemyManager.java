package com.appdisc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alchemyapi.api.AlchemyAPI;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:18:00 AM
 * 
 */
public class AlchemyManager {

	private static final Logger log = LoggerFactory
			.getLogger(AlchemyManager.class);

	private static final AlchemyManager ALCHEMY_MANAGER = new AlchemyManager();
	private static AlchemyAPI alchemyApi;

	private AlchemyManager() {
		// try {
		alchemyApi = AlchemyAPI.GetInstanceFromString(DeveloperAPIEnum.ALCHEMY
				.getApiKey());
		// .GetInstanceFromFile(AppDiscConstants.ALCHEMY_API_KEY_FILE);
		// } catch (FileNotFoundException e) {
		// log.error("Make sure the {} file is in the classpath",
		// AppDiscConstants.ALCHEMY_API_KEY_FILE);
		// throw new RuntimeException(e);
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
	};

	public static AlchemyManager getInstance() {
		return ALCHEMY_MANAGER;
	}

	public AlchemyAPI getAlchemyAPI() {
		return alchemyApi;
	}
}
