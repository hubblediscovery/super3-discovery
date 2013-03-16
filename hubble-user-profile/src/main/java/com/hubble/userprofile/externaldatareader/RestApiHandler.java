package com.hubble.userprofile.externaldatareader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.hubble.userprofile.types.RestCallTypesEnum;

public class RestApiHandler {
	private HttpURLConnection connection = null;
	private BufferedReader bufferedReader = null;
	private StringBuilder conceptTaggingResponse = null;
	private String conceptTaggingResponseLine = null;
	private StringBuilder callParameters = null;
	private URL connectionURL = null;
	private String connectionURLString = null;
	private RestCallTypesEnum callType = null;

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
	private static final String CHAR_SET = "charset";
	private static final String CHAR_SET_UTF8 = "utf-8";
	static Logger log = Logger.getLogger(RestApiHandler.class);

	public RestApiHandler(String baseUrl, RestCallTypesEnum callType) {
		connectionURLString = new String(baseUrl);
		conceptTaggingResponseLine = new String();
		callParameters = new StringBuilder();
		conceptTaggingResponse = new StringBuilder();
		this.callType = callType;

	}

	public void buildCallParameters(String... parameters) {
		for (String param : parameters) {
			callParameters.append(param);
		}
	}

	public StringBuilder downloadDataWithURL(String... parameters) {

		try {
			this.buildCallParameters(parameters);
			connectionURL = new URL(connectionURLString);
			System.out.println(connectionURLString);

			// set up out communications stuff
			connection = null;

			// Set up the initial connection
			connection = (HttpURLConnection) connectionURL.openConnection();
			connection.setRequestMethod(callType.toString());
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(callType.toString());
			connection.setRequestProperty(CONTENT_TYPE,
					CONTENT_TYPE_URL_ENCODED);
			connection.setRequestProperty(CHAR_SET, CHAR_SET_UTF8);
			connection.setRequestProperty(
					"Content-Length",
					""
							+ Integer.toString(callParameters.toString()
									.getBytes().length));
			connection.setUseCaches(false);

			// connection.setReadTimeout(10000);
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(callParameters.toString());
			wr.flush();
			wr.close();
			// connection.connect();

			// read the result from the server
			bufferedReader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			conceptTaggingResponse = new StringBuilder();

			while ((conceptTaggingResponseLine = bufferedReader.readLine()) != null) {
				conceptTaggingResponse.append(conceptTaggingResponseLine);
			}
			connection.disconnect();
			System.out.println(conceptTaggingResponse.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close the connection, set all objects to null
			// connection.disconnect();
			bufferedReader = null;
			conceptTaggingResponse = null;
			connection = null;
		}
		return conceptTaggingResponse;
	}
}
