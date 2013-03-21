/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.types;

/**
 * Enum of ratings
 * 4 being the highest and 1 the lowest
 * 
 * @author narenathmaraman
 * 
 */
public enum Ratings {
	BULLS_EYE(4), BULL(3), TRIPLE_RING(2), DOUBLE_RING(1);

	private final int ratingValue;

	// Constructor
	Ratings(int value) {
		this.ratingValue = value;
	}

	public int value() {
		return ratingValue;
	}
}
