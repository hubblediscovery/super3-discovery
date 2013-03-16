/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.exceptions;

/**
 * @author narenathmaraman
 * 
 */
public class UserProfilerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String commonMessage = "Exception in User Profile Management";

	/**
	 * 
	 */
	public UserProfilerException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public UserProfilerException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public UserProfilerException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UserProfilerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the commonmessage
	 */
	public static String getCommonmessage() {
		return commonMessage;
	}

}
