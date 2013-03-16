/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.persistence;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hubble.userprofile.utils.LoggerUtil;

/**
 * @author narenathmaraman
 * 
 */
public class HibernateDataReader implements DataReader {

	static Logger log = Logger.getLogger(HibernateDataReader.class);

	/**
	 * Read data from the database dataType, the persistent type to be read
	 * primaryKey, the id of the object to be read
	 * 
	 * @see com.hubble.userprofile.persistence.DataReader#readData(java.lang.String)
	 */
	public <T> T readData(Class<T> dataType, Serializable primaryKey) {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		T obj = null;
		try {
			tx = session.beginTransaction();
			obj = (T) session.get(dataType, primaryKey);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LoggerUtil.logStackTrace(e);
		} finally {
			session.close();
		}
		return obj;
	}
}
