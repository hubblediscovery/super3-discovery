package com.appdisc;

/**
 * 
 * @author easwar
 * @date Mar 15, 2013 9:05:36 AM
 * 
 */
public enum DeveloperAPIEnum {
	ALCHEMY("Alchemy API", "eefb4d0686c15c7d070c1e72bf91af51fceb1479"),
	CALAIS("Calais API", ""),
	YAHOO_GEO_PLANET("", "dj0yJmk9TlRNTFk4OWthVllIJmQ9WVdrOU9HRkRXVTluTm1zbWNHbzlNakF3TURrNE16WTJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD1jNA--");

	String apiDescription;
	String apiKey;

	private DeveloperAPIEnum(String apiDescription, String apiKey) {
		this.apiDescription = apiDescription;
		this.apiKey = apiKey;
	}

	public String getApiDescription() {
		return apiDescription;
	}

	public String getApiKey() {
		return apiKey;
	}
}
