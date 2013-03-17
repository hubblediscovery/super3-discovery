package com.appdisc.trend.profile;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:18:22 AM
 * 
 */
public interface TrendingProfile extends Serializable,
		Comparable<TrendingProfile> {

	public Date getTrendDate();

	public Location getLocation();

	public Map<String, Set<String>> getTrendingTopicToKeywordsMap();

}
