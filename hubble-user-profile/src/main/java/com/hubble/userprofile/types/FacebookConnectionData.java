/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.types;

import com.restfb.Facebook;

/**
 * Models facebook connections data object
 * 
 * @author narenathmaraman
 * 
 */
public class FacebookConnectionData extends FacebookData {
	@Facebook
	private String category;
	@Facebook
	private String created_time;
	private String description;
	private String keywords;

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the created_time
	 */
	public String getCreate_time() {
		return created_time;
	}

	/**
	 * @param created_time
	 *            the created_time to set
	 */
	public void setCreate_time(String create_time) {
		this.created_time = create_time;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the created_time
	 */
	public String getCreated_time() {
		return created_time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((created_time == null) ? 0 : created_time.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FacebookConnectionData)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FacebookConnectionData fbConnData = (FacebookConnectionData) obj;
		if (super.equals(obj) && this.category.equals(fbConnData.getCategory())
				&& this.created_time.equals(fbConnData.getCreate_time())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FacebookConnectionData [id=");
		builder.append(id);
		builder.append(", Name=");
		builder.append(name);
		builder.append(", category=");
		builder.append(category);
		builder.append(", created_time=");
		builder.append(created_time);
		builder.append(", description=");
		builder.append(description);
		builder.append(", keywords=");
		builder.append(keywords);
		builder.append("]");
		return builder.toString();
	}

}
