package com.appdisc.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.appdisc.AlchemyManager;

/**
 * 
 * @author easwar
 * @date Mar 15, 2013 8:09:12 AM
 * 
 */
public class KeywordExtractor {

	private static final Logger log = LoggerFactory
			.getLogger(KeywordExtractor.class);

	public enum KeywordExtractorAPIEnum {
		// TODO: Populate Calais extractPath
		ALCHEMY_API("Alchemy API", "/results/keywords/keyword/text/text()"), CALAIS(
				"Calais", "");

		String description;
		String extractPath;

		KeywordExtractorAPIEnum(String description, String extractPath) {
			this.description = description;
			this.extractPath = extractPath;
		}

		public String getDescription() {
			return description;
		}

		public String getExtractPath() {
			return extractPath;
		}
	}

	public static Set<String> extractKeywords(Document keywordsDoc,
			String extractPath) {
		Set<String> keywords = new HashSet<String>();
		if (keywordsDoc != null) {
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xPath = xpf.newXPath();
			try {
				XPathExpression expr = xPath.compile(extractPath);
				NodeList nodeList = (NodeList) expr.evaluate(keywordsDoc,
						XPathConstants.NODESET);
				for (int i = 0; i < nodeList.getLength(); i++) {
					keywords.add(nodeList.item(i).getNodeValue());
				}
			} catch (XPathExpressionException e) {
				log.error(
						"Error while processing the xpath expression to extract the keywords",
						e);
			}
		} else {
			log.error("Document object is NULL. Please ensure to pass a valid Document.");
		}
		return keywords;
	}

	public static Set<String> extractKeywords(String text,
			KeywordExtractorAPIEnum extractorAPIEnum)
			throws XPathExpressionException, SAXException,
			ParserConfigurationException {
		Document keywordsDoc = null;
		if (extractorAPIEnum.equals(KeywordExtractorAPIEnum.ALCHEMY_API)) {
			try {
				keywordsDoc = AlchemyManager.getInstance().getAlchemyAPI()
						.TextGetRankedKeywords(text);
			} catch (IOException e) {
				log.error("Error while retrieving keywords using Alchemy API",
						e.getLocalizedMessage());
				// if (e.getLocalizedMessage().contains(
				// AppDiscConstants.ALCHEMY_LIMIT_ERROR))
				// break PROFILE_PER_DATE;
			} catch (IllegalArgumentException e) {
				log.error(e.getLocalizedMessage());
			}
		}
		return extractKeywords(keywordsDoc, extractorAPIEnum.getExtractPath());
	}

}
