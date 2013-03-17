package com.hubble.store.handlers.enrich;

import com.hubble.store.common.util.HubbleLogger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;


public class HttpClientPost {

	private String url = null;
	private HttpClient client = null;
	private NameValuePair clientParameters = null;

	public HttpClientPost(String baseUrl) {
		this.url = baseUrl;
		this.client = new HttpClient();
	}

	public PostMethod createPostMethod(List<NameValuePair> requestHeaders) {
		PostMethod method = new PostMethod(url);
		for (NameValuePair nvPair : requestHeaders) {
			method.setRequestHeader(nvPair.getName(), nvPair.getValue());
		}
		return method;
	}

	public String doPostRequest(String text, PostMethod method)
			throws UnsupportedEncodingException, HttpException, IOException {
		int returnCode;
		//set client parameters
		if(clientParameters != null) {
			client.getParams().setParameter(clientParameters.getName(), clientParameters.getValue());
		}
		//set request entity
		method.setRequestEntity(new StringRequestEntity(text, null, null));
		try {
			returnCode = client.executeMethod(method);
			if (returnCode != HttpStatus.SC_OK) {
				HubbleLogger.printErrorMessage("Method type not supported, HTTP STATUS : "
						+ returnCode);
				HubbleLogger.printErrorMessage("Response : " + method.getResponseBodyAsString());
			} else {
				return method.getResponseBodyAsString();
			}
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	/**
	 * @param clientParameters the clientParameters to set
	 */
	public void setClientParameters(String name, String value) {
		this.clientParameters = new NameValuePair(name, value);
	}

}
