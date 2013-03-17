package com.appdisc.trend.twitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.logging.Logger;

import com.alchemyapi.api.AlchemyAPI;
import com.appdisc.AppDiscConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * 
 * @author easwar
 * @date Dec 29, 2012 1:02:10 AM
 * 
 */
public class TrendReader {

	private static final Logger log = Logger.getLogger(TrendReader.class);

	private static AlchemyAPI alchemyObj;
	private static Twitter twitter;
	private static Mongo mongo;
	private static DB db;

	private static final String TRENDS_TWEETS_FILE = "Trends and asso. Tweets";
	private static final String TRENDS_KEYWORDS_FILE = "Trends and asso. Keywords";

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static Map<Integer, Map<String, Set<String>>> trendingLocationToKeywordsMap = new HashMap<Integer, Map<String, Set<String>>>();

	public static void main(String[] args) {
		try {
			Date startDate = sdf.parse(args[0]);
			Date endDate = sdf.parse(args[1]);
			// Date startDate = new DateTime(2012, 12, 28, 0, 0, 0, 0).toDate();
			// Date endDate = new DateTime(2012, 12, 31, 0, 0, 0, 0).toDate();

			File file = new File(TRENDS_TWEETS_FILE + "-" + args[0] + ".log");
			PrintStream printStream = new PrintStream(
					new FileOutputStream(file));
			System.setOut(printStream);

			twitter = new TwitterFactory(new ConfigurationBuilder()
					.setJSONStoreEnabled(true).build()).getInstance();
			mongo = new Mongo(AppDiscConstants.DB_HOST,
					AppDiscConstants.DB_PORT);
			db = mongo.getDB(AppDiscConstants.DB_NAME);
			DBCollection locationCollection = db
					.getCollection(AppDiscConstants.LOCATION_COLLECTION);
			DBCollection trendCollection = db
					.getCollection(AppDiscConstants.TREND_COLLECTION);

			alchemyObj = AlchemyAPI
					.GetInstanceFromFile(AppDiscConstants.ALCHEMY_API_KEY_FILE);

			printTrendsByLocation(locationCollection, trendCollection,
					startDate, endDate);

			writeTrendsWithExtractedKeywords(args[0]);

		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mongo.close();
		}
	}

	private static void writeTrendsWithExtractedKeywords(String date)
			throws IOException {
		File file = new File(TRENDS_KEYWORDS_FILE + "-" + date + ".log");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for (Integer woeid : trendingLocationToKeywordsMap.keySet()) {
			bw.write("WOEID: " + woeid);
			bw.newLine();
			Map<String, Set<String>> trendingTopicToKeywordsMap = trendingLocationToKeywordsMap
					.get(woeid);
			for (String topic : trendingTopicToKeywordsMap.keySet()) {
				bw.write("Trending Topic: " + topic);
				bw.write("Below are the keywords extracted:");
				bw.newLine();
				for (String keyword : trendingTopicToKeywordsMap.get(topic)) {
					bw.write(keyword);
					bw.newLine();
				}
				bw.newLine();
			}
			bw.newLine();
		}
		bw.close();
	}

	private static void printTrendsByLocation(DBCollection locationCollection,
			DBCollection trendCollection, Date startDate, Date endDate)
			throws TwitterException, XPathExpressionException, IOException,
			SAXException, ParserConfigurationException {
		Document keywordsDoc;
		BasicDBObject queryByDate = new BasicDBObject();
		String stDate = AppDiscConstants.DATE_FORMAT.print(new DateTime(
				startDate));
		String edDate = AppDiscConstants.DATE_FORMAT
				.print(new DateTime(endDate));
		System.out.println("Trend Start Date: " + stDate);
		System.out.println("Trend End Date: " + edDate);
		queryByDate.put("as_of",
				new BasicDBObject("$gte", startDate).append("$lt", endDate));
		@SuppressWarnings("unchecked")
		List<Integer> distinctWOEID = locationCollection.distinct("woeid",
				queryByDate);

		WOEID: for (Integer woeid : distinctWOEID) {
			String location = (String) locationCollection.findOne(
					new BasicDBObject("woeid", woeid)).get("name");
			DBCursor trendCursor = trendCollection.find(new BasicDBObject(
					"locations.woeid", woeid).append("as_of",
					new BasicDBObject("$gte", stDate).append("$lt", edDate)),
					new BasicDBObject("trends", 1));
			System.out.println();
			System.out
					.println("******************** Below are the top trending topics for the location in the given time range: "
							+ location
							+ " (woeid: "
							+ woeid
							+ ") *********************");
			Map<String, Set<String>> trendingTopicToKeywordsMap = new HashMap<String, Set<String>>();
			trendingLocationToKeywordsMap
					.put(woeid, trendingTopicToKeywordsMap);
			while (trendCursor.hasNext()) {
				@SuppressWarnings("unchecked")
				List<DBObject> trendObjs = (List<DBObject>) trendCursor.next()
						.get("trends");

				for (DBObject trendObj : trendObjs) {
					String trendingTopic = (String) trendObj.get("name");
					String trendingQuery = (String) trendObj.get("query");
					System.out.println();
					System.out.println("Trending Topic: " + trendingTopic);
					QueryResult queryResult = twitter.search(new Query(
							trendingQuery));
					List<Tweet> tweets = queryResult.getTweets();
					System.out
							.println("Number of Tweets retrieved for this trending topic: "
									+ tweets.size());
					System.out.println("Below are the associated Tweets: ");
					System.out.println("--------------------------------");
					int num = 1;
					StringBuilder tweetTexts = new StringBuilder();
					for (Tweet tweet : tweets) {
						// if (num > AppDiscConstants.NUM_OF_TWEETS_CONSIDERED)
						// break;
						String tweetText = tweet.getText();
						System.out.println(num++ + ". " + tweetText);
						tweetTexts.append(tweetText);
					}
					try {
						keywordsDoc = alchemyObj
								.TextGetRankedKeywords(tweetTexts.toString());
						trendingTopicToKeywordsMap.put(trendingTopic,
								extractKeywords(keywordsDoc));
						// System.out.println(getStringFromDocument(keywordsDoc));
					} catch (IOException e) {
						System.out.println(e.getLocalizedMessage());
						if (e.getLocalizedMessage().contains(
								AppDiscConstants.ALCHEMY_LIMIT_ERROR))
							break WOEID;
					} catch (IllegalArgumentException e) {
						System.out.println(e.getLocalizedMessage());
					}
				}
			}
		}
	}

	private static Set<String> extractKeywords(Document keywordsDoc)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException {
		Set<String> keywords = new HashSet<String>();
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();
		XPathExpression expr = xPath
				.compile("/results/keywords/keyword/text/text()");
		NodeList nodeList = (NodeList) expr.evaluate(keywordsDoc,
				XPathConstants.NODESET);
		for (int i = 0; i < nodeList.getLength(); i++) {
			keywords.add(nodeList.item(i).getNodeValue());
		}
		return keywords;
	}

	private static String getStringFromDocument(Document doc)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException {
		StringWriter writer = new StringWriter();
		try {
			DOMSource domSource = new DOMSource(doc);
			StreamResult result = new StreamResult(writer);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			// transformer.getOutputProperties()setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS,
			// "text");
			transformer.transform(domSource, result);

			XPathFactory xpf = XPathFactory.newInstance();
			XPath xp = xpf.newXPath();
			XPathExpression expr = xp
					.compile("/results/keywords/keyword/text/text()");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			String text = nl.item(0).getNodeValue();
			for (int i = 0; i < nl.getLength(); i++) {
				nl.item(i).getNodeValue();
			}
			// String text =
			// xp.evaluate("/results/keywords/keyword/text/text()",
			// doc.getDocumentElement());
			//
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new InputSource(new StringReader(
					writer.toString())));

			text = xp.evaluate("/results/keywords/keyword/text/text()",
					doc.getDocumentElement());

		} catch (TransformerException ex) {
			ex.printStackTrace();
		}
		return writer.toString();
	}

}
