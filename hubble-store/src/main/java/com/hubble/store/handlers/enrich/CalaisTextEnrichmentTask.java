package com.hubble.store.handlers.enrich;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import mx.bigdata.jcalais.CalaisClient;
import mx.bigdata.jcalais.CalaisObject;
import mx.bigdata.jcalais.CalaisResponse;
import mx.bigdata.jcalais.rest.CalaisRestClient;

public class CalaisTextEnrichmentTask implements Callable<List<String>> {

	//private static final String CALAIS_URL = "http://api.opencalais.com/tag/rs/enrich";
	private static final String calais_api_key = "yxnr3htbctnhkkwfmp7htbw7";

	//private HttpClientPost clientPost = null;
	private String sourceText = null;
	
	public CalaisTextEnrichmentTask(String text) {
		//clientPost = new HttpClientPost(CALAIS_URL);
		sourceText = text;

	}

	/*public String call() throws XPathExpressionException, IOException,
			SAXException, ParserConfigurationException {
		List<NameValuePair> requestHeaders = new ArrayList<NameValuePair>();
		requestHeaders.add(new NameValuePair("x-calais-licenseID",
				calais_api_key));
		requestHeaders.add(new NameValuePair("Content-Type",
				"text/raw; charset=UTF-8"));
		// response type
		requestHeaders.add(new NameValuePair("Accept", "application/json"));
		requestHeaders
				.add(new NameValuePair("enableMetadataType", "SocialTags"));
		clientPost.setClientParameters("http.useragent", "Calais Rest Client");
		PostMethod method = clientPost.createPostMethod(requestHeaders);
		String calaisResponse = clientPost.doPostRequest(sourceText, method);
		//HubbleLogger.printMessage(calaisResponse);
		return calaisResponse;
	}*/
	
	public List<String> call() throws IOException {
        CalaisClient client = new CalaisRestClient(calais_api_key);
        CalaisResponse response = client
                .analyze(sourceText);
        Set<String> keywords = new HashSet<String>();
        //Entities referenced in text
        for(CalaisObject entity : response.getEntities()) {
            keywords.add(entity.getField("name"));
        }
        //Topics referenced in text
        for(CalaisObject topics : response.getTopics()) {
            keywords.add(topics.getField("categoryName"));
        }
        //Social Tags
        for(CalaisObject tags : response.getSocialTags()) {
            keywords.add(tags.getField("name"));
        }
        return new ArrayList<String>(keywords);
    }
	
}
