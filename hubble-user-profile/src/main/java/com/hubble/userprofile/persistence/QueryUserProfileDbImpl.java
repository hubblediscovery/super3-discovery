/**
 * Copyright 2013, Hubble Apps.
 */
package com.hubble.userprofile.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import com.hubble.userprofile.exceptions.UserDoesNotExistException;
import com.hubble.userprofile.exceptions.UserProfilerException;
import com.hubble.userprofile.hibernate.FacebookEntities;

/**
 * @author narenathmaraman
 * 
 */
public class QueryUserProfileDbImpl implements QueryUserProfileDb {

	DataReader reader = null;

	/**
	 * 
	 */
	public QueryUserProfileDbImpl() {
		reader = new HibernateDataReader();
	}

	/**
	 * Get HubbleApp Id from facebookUserId
	 * 
	 * @see com.hubble.userprofile.persistence.QueryUserProfileDb#
	 *      getUserIdUsingFacebookId(java.lang.String)
	 */
	public String getUserIdFromFacebookId(String facebookId)
			throws UserProfilerException {
		final String param_facebookId = "fbId";
		String baseQuery = "Select fbDemographics.id.userAppId from FacebookUserDemographics as fbDemographics where fbDemographics.id.facebookId = :"
				+ param_facebookId;
		Map<String, String> parameterValueMap = new HashMap<String, String>();
		parameterValueMap.put(param_facebookId, facebookId);
		List list = executeQuery(baseQuery, parameterValueMap);
		if (!list.isEmpty()) {
			return (String) list.get(0);
		}
		throw new UserDoesNotExistException(
				"User does not exist in hubble realm : " + facebookId);
	}

	/**
	 * Get the user's current city
	 * 
	 * @param facebookId
	 * @return the current city like, Mountain View California
	 * @throws UserProfilerException
	 */
	public String getCurrentCity(String hubbleAppId)
			throws UserProfilerException {
		final String param_hubbleID = "hubbleId";
		String baseQuery = "Select fbDemographics.currentLocation from FacebookUserDemographics as fbDemographics where fbDemographics.id.userAppId = :"
				+ param_hubbleID;
		Map<String, String> parameterValueMap = new HashMap<String, String>();
		parameterValueMap.put(param_hubbleID, hubbleAppId);
		List list = executeQuery(baseQuery, parameterValueMap);
		if (!list.isEmpty()) {
			return (String) list.get(0);
		}
		throw new UserDoesNotExistException(
				"User does not exist in hubble realm : " + hubbleAppId);
	}

	/**
	 * Get the user's home town
	 * 
	 * @param facebookId
	 * @return the user's hometown like, Mountain View California
	 * @throws UserProfilerException
	 */
	public String getHometown(String hubbleAppId) throws UserProfilerException {
		final String param_hubbleID = "hubbleId";
		String baseQuery = "Select huser.hometown from HubbleUser as huser where huser.appId = :"
				+ param_hubbleID;
		Map<String, String> parameterValueMap = new HashMap<String, String>();
		parameterValueMap.put(param_hubbleID, hubbleAppId);
		List list = executeQuery(baseQuery, parameterValueMap);
		if (!list.isEmpty()) {
			return (String) list.get(0);
		}
		throw new UserDoesNotExistException(
				"User does not exist in hubble realm : " + hubbleAppId);
	}

	/**
	 * Get all the entity ids that the user likes
	 * 
	 * @param hubbleAppId
	 *            , the id of the user
	 * @return list of entity ids
	 * @throws UserProfilerException
	 */
	public List<String> getLikedEntities(String hubbleAppId)
			throws UserProfilerException {
		final String param_hubbleID = "hubbleId";
		String baseQuery = "Select fbLikedEntities.id.fbEntityId from FbUserLikedEntities as fbLikedEntities where fbLikedEntities.id.appId = :"
				+ param_hubbleID;
		Map<String, String> parameterValueMap = new HashMap<String, String>();
		parameterValueMap.put(param_hubbleID, hubbleAppId);
		List<String> list = executeQuery(baseQuery, parameterValueMap);
		if (!list.isEmpty()) {
			return list;
		}
		throw new UserDoesNotExistException(
				"Could not fetch liked entities for user: " + hubbleAppId);
	}

	/**
	 * Get Keywords for entity
	 * 
	 * @param entityId
	 *            , the entity for which keywords are to be fetched
	 * @return
	 * @throws UserProfilerException
	 */
	public List<String> getEntityKeywords(String entityId)
			throws UserProfilerException {
		final String param_entityID = "entityId";
		List<String> keywordsList = new ArrayList<String>();
		String baseQuery = "Select fbEntities.keywords from FacebookEntities as fbEntities where fbEntities.id = :"
				+ param_entityID;
		Map<String, String> parameterValueMap = new HashMap<String, String>();
		parameterValueMap.put(param_entityID, entityId);
		keywordsList = executeQuery(baseQuery, parameterValueMap);
		return keywordsList;
	}
	
	/**
	 * Get the FacebookEntities instance referred by entityId
	 * 
	 * @param entityId
	 * @return
	 */
	public FacebookEntities getEntity(String entityId) {
		return reader.readData(FacebookEntities.class, entityId);

	}

	/**
	 * Get user name from database referred by hubbleAppId
	 * @param hubbleAppId
	 */
	public String getUserName(String hubbleAppId) throws UserProfilerException {
		final String param_hubbleID = "hubbleId";
		String baseQuery = "Select huser.name from HubbleUser as huser where huser.appId = :"
				+ param_hubbleID;
		Map<String, String> parameterValueMap = new HashMap<String, String>();
		parameterValueMap.put(param_hubbleID, hubbleAppId);
		List list = executeQuery(baseQuery, parameterValueMap);
		if (!list.isEmpty()) {
			return (String) list.get(0);
		}
		throw new UserDoesNotExistException(
				"User does not exist in hubble realm : " + hubbleAppId);
	}

	/**
	 * Builds and query using the parameter map and executes and returns the
	 * result
	 * 
	 * @param baseQuery
	 * @param parameterValueMap
	 * @return a List with results of the query
	 * @throws UserProfilerException
	 */
	private List executeQuery(String baseQuery,
			Map<String, String> parameterValueMap) throws UserProfilerException {
		Transaction tx = null;
		StatelessSession session = HibernateUtil.getHibernateSessionFactory()
				.openStatelessSession();
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(baseQuery);
			for (String paramKey : parameterValueMap.keySet()) {
				query.setString(paramKey, parameterValueMap.get(paramKey));
			}
			return query.list();
		} catch (RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new UserProfilerException("Error executing Hibernate query");
		} finally {
			tx.commit();
			session.close();
		}
	}
}
