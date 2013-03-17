package com.appdisc.trend.profile;

import java.util.List;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:35:28 AM
 * 
 */
public class TrendProfilerTest extends TestCase {

	@Test
	public void testByLatAndLong() {
		TrendProfiler profiler = new TrendProfiler();

		List<TrendingProfile> profiles = profiler.getTrends(37.371609,
				-122.038254, new DateTime(2013, 3, 16, 0, 0, 0, 0).toDate(), 0);

		for (TrendingProfile profile : profiles) {
			System.out.println(profile.toString());
		}
	}

}
