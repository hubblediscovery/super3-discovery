package com.hubble.store.helper;

import com.hubble.store.model.*;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class HubbleXMLParser extends DefaultHandler implements
		Callable<AppStoreFeed> {

	private HubbleAppData currentAppStoreEntry;
	private String input;
	private AppStoreFeed currentAppStoreFeed;
	private boolean accumulateCharacters;
	private StringBuffer currentParsedString;
	private int entryCount = 0;
	// private volatile boolean running = true;
	private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

	public HubbleXMLParser(String crawlerInput) {
		super();
		input = crawlerInput;
		accumulateCharacters = false;
		currentParsedString = new StringBuffer();
		currentAppStoreFeed = new AppStoreFeed();
	}

	@Override
	public AppStoreFeed call() {

		try {
			// running = true;
			XMLReader xr = XMLReaderFactory.createXMLReader();
			// HubbleXMLParser handler = new HubbleXMLParser();
			xr.setContentHandler(this);
			xr.setErrorHandler(this);
			xr.parse(new InputSource(new StringReader(input)));
			return currentAppStoreFeed;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// System.out.println("Finished Parsing");
		}
		return null;
	}

	/*
	 * public static AppStoreFeed startCrawlerForInput(String crawlerInput)
	 * throws Exception {
	 * 
	 * }
	 */

	public HubbleXMLParser() {
		super();
		accumulateCharacters = false;
		currentParsedString = new StringBuffer();
		currentAppStoreFeed = new AppStoreFeed();
	}

	// //////////////////////////////////////////////////////////////////
	// Event handlers.
	// //////////////////////////////////////////////////////////////////

	public void startDocument() {
		// System.out.println("Start document");
	}

	public void endDocument() {
		// currentAppStoreFeed.printAppStoreEntries();
		// System.out.println("End document");
	}

	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		// Entry tag element. Initialize an object of App Store Feed Entry
		if (qName != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_ENTRY_ELEMENT)) {
			currentAppStoreEntry = new HubbleAppData();
			entryCount++;
			// System.out.println("Entry Count - " + entryCount);
		}

		if (qName != null
				&& (qName.equals(HubbleUtil.APPSTORE_FEED_TITLE_ELEMENT)
						|| qName.equals(HubbleUtil.APPSTORE_FEED_UPDATED_ELEMENT)
						|| qName.equals(HubbleUtil.APPSTORE_FEED_SUMMARY_ELEMENT)
						|| qName.equals(HubbleUtil.APPSTORE_FEED_IM_NAME_ELEMENT)
						|| qName.equals(HubbleUtil.APPSTORE_FEED_IM_IMAGE_ELEMENT)
						|| qName.equals(HubbleUtil.APPSTORE_FEED_IM_ARTIST_ELEMENT) || qName
							.equals(HubbleUtil.APPSTORE_FEED_IM_RELEASEDATE_ELEMENT))) {
			accumulateCharacters = true;
			currentParsedString = new StringBuffer();
		}

		else if (qName != null && currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_ID_ELEMENT)) {
			currentAppStoreEntry.setAppId(Integer.parseInt((atts
					.getValue(HubbleUtil.APPSTORE_FEED_IM_APPID_ATTR))));
			currentAppStoreEntry.setAppBundleId(atts
					.getValue(HubbleUtil.APPSTORE_FEED_IM_BUNDLEID_ATTR));
		}

		else if (qName != null && currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_CATEGORY_ELEMENT)) {
			// int categoryID =
			// Integer.parseInt(atts.getValue(HubbleUtil.APPSTORE_FEED_IM_CATEGORYID_ATTR));
			// String category = getCategoryForCategoryID(categoryID);
			currentAppStoreEntry.setAppPrimaryCategory(atts
					.getValue(HubbleUtil.APPSTORE_FEED_IM_CATEGORYID_ATTR));

		}

		else if (qName != null && currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_IM_PRICE_ELEMENT)) {
			currentAppStoreEntry.setAppUnitPrice(new BigDecimal((atts
					.getValue(HubbleUtil.APPSTORE_FEED_AMOUNT_ATTR))));
			currentAppStoreEntry.setAppUnitCurrency(atts
					.getValue(HubbleUtil.APPSTORE_FEED_CURRENCY_ATTR));
		}

	}

	public void endElement(String uri, String name, String qName) {

		if (qName != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_ENTRY_ELEMENT)) {
			currentAppStoreFeed.addEntryWithKey(new Integer(entryCount),
					currentAppStoreEntry);
		}

		if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_TITLE_ELEMENT)) {
			currentAppStoreEntry.setAppTitle(currentParsedString.toString());
		} else if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_UPDATED_ELEMENT)) {
			try {
				String dateSubString = currentParsedString.toString()
						.substring(0, 10);
				currentAppStoreEntry.setAppLastUpdatedDate(dt
						.parse(dateSubString));
			} catch (ParseException e) {
			}
		} else if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_IM_RELEASEDATE_ELEMENT)) {
			try {
				String dateSubString = currentParsedString.toString()
						.substring(0, 10);
				// System.out.println(dt.parse(dateSubString));
				currentAppStoreEntry
						.setAppReleasedDate(dt.parse(dateSubString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_SUMMARY_ELEMENT)) {
			currentAppStoreEntry.setAppDescription(currentParsedString
					.toString());
		} else if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_IM_NAME_ELEMENT)) {
			currentAppStoreEntry.setAppName(currentParsedString.toString());
		} else if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_IM_ARTIST_ELEMENT)) {
			currentAppStoreEntry.setAppDeveloperInfo(currentParsedString
					.toString());
		} else if (currentAppStoreEntry != null
				&& qName.equals(HubbleUtil.APPSTORE_FEED_IM_IMAGE_ELEMENT)) {
			// currentAppStoreEntry.addiPhoneImage(currentParsedString.toString());
		}

		accumulateCharacters = false;
		currentParsedString = new StringBuffer();
	}

	public void characters(char ch[], int start, int length) {
		if (accumulateCharacters) {
			currentParsedString.append(ch, start, length);
		}

	}

}