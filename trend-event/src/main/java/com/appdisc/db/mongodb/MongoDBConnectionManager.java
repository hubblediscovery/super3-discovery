package com.appdisc.db.mongodb;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdisc.AppDiscConstants;
import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:18:40 AM
 * 
 */
public class MongoDBConnectionManager {

	private static final Logger log = LoggerFactory
			.getLogger(MongoDBConnectionManager.class);

	private static final MongoDBConnectionManager MONGO_DB_CONNECTION_MANAGER = new MongoDBConnectionManager();
	private static Mongo mongo;
	private static DB trendDb;

	private MongoDBConnectionManager() {
		try {
			mongo = new Mongo(AppDiscConstants.DB_HOST,
					AppDiscConstants.DB_PORT);
		} catch (UnknownHostException e) {
			log.error("Unable to establish the database connection with the provided properties");
			throw new RuntimeException(e);
		}
		trendDb = mongo.getDB(AppDiscConstants.DB_NAME);
	};

	public static MongoDBConnectionManager getInstance() {
		return MONGO_DB_CONNECTION_MANAGER;
	}

	public DB getTrendDBInstance() {
		return trendDb;
	}

	public Mongo getMongo() {
		return mongo;
	}

}
