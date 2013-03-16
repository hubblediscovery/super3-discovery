/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.persistence;

import java.io.Serializable;
import java.util.List;

import com.hubble.userprofile.exceptions.UserProfilerException;

/**
 * interface to write data
 * 
 * @author narenathmaraman
 * 
 */
public interface DataWriter {

	<T> void writeData(T data);

	/**
	 * Write a list of objects atomically
	 * 
	 * @param dataList
	 */
	public void batchWriteData(List dataList);

	/**
	 * Update entry in the database for row id with the data passed in as
	 * newData.
	 * 
	 * @throws UserProfilerException
	 */
	<T> void updateData(T newData, Serializable id)
			throws UserProfilerException;

	/**
	 * Update a particular field referred by @param fieldName to value set in
	 * newData's fieldName
	 * 
	 * @param newData
	 *            , the object from which the new fieldName value is to be read
	 * @param fieldName
	 *            , the name of the field to be modified
	 * @param id
	 *            , the primary key of type T
	 * @throws UserProfilerException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	<T> void updateField(T newData, String fieldName, Serializable id)
			throws UserProfilerException, SecurityException,
			NoSuchFieldException;

	/**
	 * Delete a list of objects atomically
	 * 
	 * @param dataList
	 */
	void batchDeleteData(List deleteList);

	/**
	 * Delete object referred by id
	 * 
	 * @param deleteData
	 * @param id
	 */
	<T> void deleteData(T deleteData, Serializable id);

}
