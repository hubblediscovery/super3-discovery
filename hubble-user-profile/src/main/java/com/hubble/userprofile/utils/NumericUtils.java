/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author narenathmaraman
 * 
 */
public class NumericUtils {

	private static final AtomicLong nextSerialNum = new AtomicLong();

	public static long generateSerialNumber() {
		return nextSerialNum.getAndIncrement();
	}

}
