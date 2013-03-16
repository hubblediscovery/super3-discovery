/**
 * 
 */
package com.hubble.userprofile.types;

import java.util.Date;

import com.hubble.userprofile.utils.NumericUtils;

/**
 * Entity - Generic User data
 * 
 * @author narenathmaraman
 * 
 */
public class GenericUser {
	private String id; // hubble id
	private String name;
	private String gender;
	private String hometown;
	private Date birthday;

	public GenericUser() {
		this.id = String.valueOf(NumericUtils.generateSerialNumber());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenericUser [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", hometown=");
		builder.append(hometown);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append("]");
		return builder.toString();
	}
}
