/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.dataprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;
import com.hubble.userprofile.utils.LoggerUtil;

/**
 * Use Alchemy API to process text http://access.alchemyapi.com
 * 
 * @author narenathmaraman
 * 
 */
public class AlchemyTextDataProcessor implements TextDataProcessor {
	private AlchemyAPI alchemyObj = null;

	private static Logger log = Logger
			.getLogger(AlchemyTextDataProcessor.class);

	public AlchemyTextDataProcessor(String _apiKey) {
		alchemyObj = AlchemyAPI.GetInstanceFromString(_apiKey);
	}
	
	public AlchemyTextDataProcessor(File apiKeyFile) {
		try {
			alchemyObj = AlchemyAPI.GetInstanceFromFile(apiKeyFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			LoggerUtil.logStackTrace(e);
		} catch (IOException e) {
			LoggerUtil.logStackTrace(e);
		}
	}

	/**
	 * Use text data
	 * 
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 * 
	 */
	public List<String> getKeywords(String text) {
		Document doc = null;
		try {
			doc = alchemyObj.TextGetRankedKeywords(text);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> keywordsList = new ArrayList();
		if(doc != null) {
			keywordsList = new ArrayList<String>(getStringFromDocument(doc));
		}
		return keywordsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hubble.userprofile.dataprocessor.TextDataProcessor#getCategories(
	 * java.lang.String)
	 */
	public List<String> getCategories(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hubble.userprofile.dataprocessor.TextDataProcessor#getSentiment(java
	 * .lang.String)
	 */
	public List<String> getSentiment(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	// utility method
	private static Set<String> getStringFromDocument(Document doc) {
		Set<String> keywords = new HashSet<String>();
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();
		XPathExpression expr;
		try {
			expr = xPath
					.compile("/results/keywords/keyword/text/text()");
			NodeList nodeList = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				keywords.add(nodeList.item(i).getNodeValue());
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return keywords;
	}

}
