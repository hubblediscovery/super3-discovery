package com.appdisc;

import junit.framework.TestCase;

import org.junit.Test;

import com.winterwell.jgeoplanet.GeoPlanetException;
import com.winterwell.jgeoplanet.Place;

public class GeoPlanetTest extends TestCase {

	@Test
	public void testGeoPlanet() throws GeoPlanetException {
		Place place = GeoPlanetManager.getInstance().getGeoPlanet()
				.getPlace("sunnyvale");
		System.out.println(place.getName());
		System.out.println(place.getPlaceType().getName());
		System.out.println(place.getWoeId());
		System.out.println(place.getCentroid().getLatitude());
		System.out.println(place.getCentroid().getLongitude());
	}

}
