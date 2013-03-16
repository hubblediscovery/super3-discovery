/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.types;

import java.util.List;

import com.restfb.types.User.Sport;

/**
 * Models user interests like political interests favorite quotes, religion and
 * sports
 * 
 * @author narenathmaraman
 * 
 */
public class UserInterests {

	private String political;
	private String quotes;
	private String religion;
	private List<Sport> sports;

	public UserInterests() {

	}

	/**
	 * @return the political
	 */
	public String getPolitical() {
		return political;
	}

	/**
	 * @param political
	 *            the political to set
	 */
	public void setPolitical(String political) {
		this.political = political;
	}

	/**
	 * @return the quotes
	 */
	public String getQuotes() {
		return quotes;
	}

	/**
	 * @param quotes
	 *            the quotes to set
	 */
	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	/**
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion
	 *            the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}

	/**
	 * @return the sports
	 */
	public List<Sport> getSports() {
		return sports;
	}

	/**
	 * @param sports
	 *            the sports to set
	 */
	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInterests [political=");
		builder.append(political);
		builder.append(", quotes=");
		builder.append(quotes);
		builder.append(", religion=");
		builder.append(religion);
		builder.append(", sports=");
		builder.append(sports);
		builder.append("]");
		return builder.toString();
	}

}
