package com.appdisc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterwell.jgeoplanet.GeoPlanet;
import com.winterwell.jgeoplanet.GeoPlanetException;

/**
 * 
 * @author easwar
 * @date Mar 15, 2013 9:17:02 AM
 * 
 */
public class GeoPlanetManager {

	private static final Logger log = LoggerFactory
			.getLogger(GeoPlanetManager.class);

	private static final GeoPlanetManager GEOPLANET_MANAGER = new GeoPlanetManager();
	private static GeoPlanet geoPlanet;

	private GeoPlanetManager() {
		try {
			geoPlanet = new GeoPlanet(
					DeveloperAPIEnum.YAHOO_GEO_PLANET.getApiKey());
		} catch (GeoPlanetException e) {
			log.error(
					"Error while constructing Geo Planet with the provided API key.",
					e);
		}
	}

	public static GeoPlanetManager getInstance() {
		return GEOPLANET_MANAGER;
	}

	public GeoPlanet getGeoPlanet() {
		return geoPlanet;
	}
}
