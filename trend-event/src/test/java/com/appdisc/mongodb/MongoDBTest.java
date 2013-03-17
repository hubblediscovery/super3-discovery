package com.appdisc.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
 * 
 * @author easwar
 * @date Mar 11, 2013 2:20:14 AM
 * 
 */
public class MongoDBTest {

	public static void main(String[] args) {

		try {

			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("test");
			DBCollection collection = db.getCollection("testColl");

			// convert JSON to DBObject directly
			DBObject dbObject = (DBObject) JSON
					.parse("{'name':'easwar', 'age':55}");

			collection.insert(dbObject);

			DBCursor cursorDoc = collection.find();
			while (cursorDoc.hasNext()) {
				System.out.println(cursorDoc.next().get("name"));
			}

			System.out.println("Done");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

}
