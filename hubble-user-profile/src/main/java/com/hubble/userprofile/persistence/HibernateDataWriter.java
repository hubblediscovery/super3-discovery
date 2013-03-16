package com.hubble.userprofile.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.utils.LoggerUtil;

/**
 * Write and update database using hibernate
 * 
 * @author narenathmaraman
 * 
 */
public class HibernateDataWriter implements DataWriter {

	static Logger log = Logger.getLogger(HibernateDataWriter.class);

	/**
	 * Write a list of objects atomically
	 * 
	 * @param dataList
	 */
	public void batchWriteData(List dataList) {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Object data : dataList) {
				session.save(data);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			for (StackTraceElement element : e.getStackTrace()) {
				log.error(element.toString());
			}
		} finally {
			session.close();
		}
	}

	/**
	 * Write a single object atomically
	 */
	public <T> void writeData(T data) {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(data);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			for (StackTraceElement element : e.getStackTrace()) {
				log.error(element.toString());
			}
		} finally {
			session.close();
		}

	}

	/**
	 * Update entry in the database for row id with the data passed in as
	 * newData.
	 * 
	 * @throws UserProfilerException
	 */
	public <T> void updateData(T newData, Serializable id)
			throws UserProfilerException {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// since session.get returns only an instance of newData if
			// identified by
			// id, this unchecked cast is okay
			@SuppressWarnings("unchecked")
			T dataObject = (T) session.get(newData.getClass(), id);
			if (dataObject == null) {
				log.debug("Entity does not exist " + id + " ,"
						+ newData.getClass());
				throw new UserProfilerException("Entity does not exist with :"
						+ id + " , type : " + newData.getClass());
			}
			session.merge(newData);
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LoggerUtil.logStackTrace(e);
		} finally {
			session.close();
		}

	}

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
	public <T> void updateField(T newData, String fieldName,
			Serializable primaryKey) throws UserProfilerException,
			SecurityException, NoSuchFieldException {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// since session.get returns only an instance of newData if
			// identified by
			// id, this unchecked cast is okay
			@SuppressWarnings("unchecked")
			T dataObject = (T) session.get(newData.getClass(), primaryKey);
			if (dataObject == null) {
				log.debug("Entity does not exist " + primaryKey + " ,"
						+ newData.getClass());
				throw new UserProfilerException("Entity does not exist with :"
						+ primaryKey + " , type : " + newData.getClass());
			}
			Field fieldToModify = dataObject.getClass().getDeclaredField(
					fieldName);
			fieldToModify.setAccessible(true);
			fieldToModify.set(dataObject, fieldToModify.get(newData));
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LoggerUtil.logStackTrace(e);
			throw new UserProfilerException(e.getClass() + ":" + e.getMessage());
		} catch (IllegalArgumentException e) {
			LoggerUtil.logStackTrace(e);
			throw new UserProfilerException(e.getMessage());
		} catch (IllegalAccessException e) {
			LoggerUtil.logStackTrace(e);
			throw new UserProfilerException(e.getMessage());
		} finally {
			session.close();
		}

	}

	/**
	 * Delete a list of objects atomically
	 * 
	 * @param dataList
	 *            Suppress warnings is okay because deleteList is heterogeneous
	 */
	@SuppressWarnings("rawtypes")
	public void batchDeleteData(List deleteList) {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Object data : deleteList) {
				session.buildLockRequest(LockOptions.NONE).lock(data);
				session.delete(data);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			LoggerUtil.logStackTrace(e);
		} finally {
			session.close();
		}
	}

	/**
	 * Delete the given object
	 * 
	 * @param objectToDelete
	 */
	public <T> void deleteData(T deleteData, Serializable id) {
		Session session = HibernateUtil.getHibernateSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			T dataToDelete = (T) session.get(deleteData.getClass(), id);
			session.buildLockRequest(LockOptions.NONE).lock(dataToDelete);
			session.delete(dataToDelete);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LoggerUtil.logStackTrace(e);
		} finally {
			session.close();
		}

	}

}
