package com.hubble.userprofile.dataprocessor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

public class HttpClientPost {

	private static Logger log = Logger.getLogger(HttpClientPost.class);

	private String url = null;
	private HttpClient client = null;
	private NameValuePair clientParameters = null;

	public HttpClientPost(String baseUrl) {
		this.url = baseUrl;
		this.client = new HttpClient();
	}

	/**
	 * Creates a new PostMethod with the baseUrl and sets the request headers
	 * 
	 * @param requestHeaders
	 * @return the post method
	 */
	public PostMethod createPostMethod(List<NameValuePair> requestHeaders) {
		PostMethod method = new PostMethod(url);
		for (NameValuePair nvPair : requestHeaders) {
			method.setRequestHeader(nvPair.getName(), nvPair.getValue());
		}
		return method;
	}

	/**
	 * @param clientParameters
	 *            the clientParameters to set
	 */
	public void setClientParameters(String name, String value) {
		this.clientParameters = new NameValuePair(name, value);
	}

	/**
	 * Send Post Request, with the passed in text as the request entity
	 * 
	 * @param text
	 * @param method
	 * @return the response
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public String doPostRequest(String text, PostMethod method)
			throws UnsupportedEncodingException, HttpException, IOException {
		int returnCode;
		// set client parameters
		if (clientParameters != null) {
			client.getParams().setParameter(clientParameters.getName(),
					clientParameters.getValue());
		}
		// set request entity
		method.setRequestEntity(new StringRequestEntity(text, null, null));
		try {
			returnCode = client.executeMethod(method);
			if (returnCode != HttpStatus.SC_OK) {
				log.error("Method type not supported, HTTP STATUS : "
						+ returnCode);
				log.error("Response : " + method.getResponseBodyAsString());
			} else {
				return method.getResponseBodyAsString();
			}
		} finally {
			method.releaseConnection();
		}
		return "";
	}

}
