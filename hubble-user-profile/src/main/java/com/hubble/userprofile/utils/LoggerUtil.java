/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.utils;

import org.apache.log4j.Logger;

/**
 * Contains some logging related utilities
 * 
 * @author narenathmaraman
 * 
 */
public class LoggerUtil {

	static Logger log = Logger.getLogger(LoggerUtil.class);

	/**
	 * 
	 */
	public LoggerUtil() {

	}

	public static void logStackTrace(Exception e) {
		e.printStackTrace();
		log.error(e.getLocalizedMessage());
		log.error(e.getMessage());
		for (StackTraceElement element : e.getStackTrace()) {
			log.error(element.toString());
		}
	}

	public static void logInfoMessage(String message) {
		log.info(message);
	}

}
