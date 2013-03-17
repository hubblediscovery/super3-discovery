package com.appdisc.trend.profile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.GeoLocation;
import twitter4j.ResponseList;
import twitter4j.TwitterException;

import com.appdisc.GeoPlanetManager;
import com.appdisc.trend.twitter.TwitterManager;
import com.webguys.ponzu.api.block.predicate.Predicate;
import com.webguys.ponzu.impl.list.mutable.FastList;
import com.winterwell.jgeoplanet.GeoPlanetException;
import com.winterwell.jgeoplanet.Place;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:17:33 AM
 * 
 */
public class TrendProfiler {

	private static final Logger log = LoggerFactory
			.getLogger(TrendProfiler.class);

	private static Map<Location, FastList<TrendingProfile>> trendingProfileCache = new HashMap<Location, FastList<TrendingProfile>>();
	private static Map<String, Long> locationToWoeIdMap = new HashMap<String, Long>();

	public List<TrendingProfile> getTrends(String location, Date date,
			int lookBack) {
		List<TrendingProfile> trendingProfiles = FastList.newList();
		try {
			Long woeID = locationToWoeIdMap.get(location);
			// TODO: Moved the Place for now and need to revise this logic
			Place place = null;
			com.winterwell.jgeoplanet.Location loc = null;
			if (woeID == null) {
				// TODO: Try to retrieve from DB else follow
				// TODO: Get the API Key from Yahoo
				place = GeoPlanetManager.getInstance().getGeoPlanet()
						.getPlace(location);
				woeID = place.getWoeId();
				loc = place.getCentroid();
				locationToWoeIdMap.put(location, woeID);
				// TODO: Persist this map in DB
			}
			while (trendingProfiles.isEmpty()
					|| !place.getPlaceType().getName()
							.equalsIgnoreCase("country")) {
				// TODO: Try to get the available trendingProfile closer
				// (threshold) to the given date
				// else try to get for the parents, else try to fetch the top 15
				// retweets and generate profiles out of it.
				// TODO: Also check the neighbors, siblings, then parent
				// TODO: use the latitude and longitude to resolve the closest
				// location for which the trend is available
				trendingProfiles = getTrends(woeID, date, lookBack);
				place = place.getParent();
			}

			if (trendingProfiles.isEmpty()) {
				// TODO: The below code will work for sure
				// TODO: Best thing is if this data is stored & retrieved from
				// DB as Location
				// obj
				trendingProfiles = getTrends(loc.getLatitude(),
						loc.getLongitude(), date, lookBack);
			}
		} catch (GeoPlanetException e) {
			log.error("Error occurred while constructing GeoPlanet");
			throw new RuntimeException(e);
		}
		return trendingProfiles;
	}

	public List<TrendingProfile> getTrends(final long woeid, Date date,
			int lookBack) {
		FastList<TrendingProfile> trendingProfiles = FastList.newList();
		FastList<Location> locations = FastList.newList(trendingProfileCache
				.keySet());

		@SuppressWarnings("serial")
		Location location = locations.find(new Predicate<Location>() {

			@Override
			public boolean accept(Location location) {
				if (location.getWoeid() == woeid)
					return true;
				return false;
			}
		});
		DateTime trendDate = new DateTime(date);
		boolean locationFoundInCache = false;
		TrendingProfile trendingProfile;
		TrendProfileGenerator profileGenerator = new TrendProfileGenerator();
		try {
			do {
				if (location != null) {
					locationFoundInCache = true;
					FastList<TrendingProfile> profilesFromCache = trendingProfileCache
							.get(location);
					trendingProfile = fetchTrendingProfileFromCache(
							profilesFromCache, trendDate);
					if (trendingProfile == null) {
						trendingProfile = profileGenerator
								.generateTrendingProfile(woeid, trendDate);
						profilesFromCache.add(trendingProfile);
						trendingProfileCache.put(location, profilesFromCache);
					}
				} else {
					trendingProfile = profileGenerator.generateTrendingProfile(
							woeid, trendDate.toDateTime(DateTimeZone.UTC));
				}
				trendingProfiles.add(trendingProfile);
				lookBack--;
				trendDate = trendDate.minusDays(1);
			} while (lookBack >= 0);
			if (!locationFoundInCache) {
				trendingProfileCache.put(new Location(woeid), trendingProfiles);
			}
		} catch (Exception e) {
			log.error("Error while retrieving the trending profiles");
			throw new RuntimeException(e);
		}

		return trendingProfiles;
	}

	@SuppressWarnings("serial")
	private TrendingProfile fetchTrendingProfileFromCache(
			FastList<TrendingProfile> profilesFromCache,
			final DateTime trendDate) {
		return profilesFromCache.find(new Predicate<TrendingProfile>() {

			@Override
			public boolean accept(TrendingProfile profile) {
				if (profile.getTrendDate().equals(trendDate.toDate()))
					return true;
				return false;
			}
		});
	}

	public List<TrendingProfile> getTrends(double latitude, double longitude,
			Date date, int lookBack) {
		List<TrendingProfile> trendingProfiles = FastList.newList();
		try {
			ResponseList<twitter4j.Location> locations = TwitterManager
					.getInstance().getTwitterInstance()
					.getAvailableTrends(new GeoLocation(latitude, longitude));
			// TODO: Need to decide on how many nearest locations to fetch for?
			// If ideally fetching for just the closest is fine then just fetch
			// for the 1st location alone.
			for (twitter4j.Location location : locations) {
				trendingProfiles.addAll(getTrends(location.getWoeid(), date,
						lookBack));
			}
		} catch (TwitterException e) {
			log.error("Error while retrieving trends by co-ordinates");
			throw new RuntimeException(e);
		}
		for (TrendingProfile profile : trendingProfiles) {
			profile.getLocation().setLatitude(latitude);
			profile.getLocation().setLongitude(longitude);
		}
		return trendingProfiles;
	}

}
