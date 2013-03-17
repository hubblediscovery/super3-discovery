package com.hubble.store.handlers;

import com.hubble.store.helper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HubbleDownloader {

	private HttpURLConnection connection = null;
	private BufferedReader bufferedReader = null;
	private StringBuilder rssContent = null;
	private String rssLine = null;
	private URL connectionURL = null;
	private String connectionURLString = null;

	public HubbleDownloader() {
		connectionURLString = new String();
		rssLine = new String();
		rssContent = new StringBuilder();
	}

	public AppStoreFeed startDownloadProcessWithURL(String url) {

		try {

			this.connectionURLString = url;
			connectionURL = new URL(connectionURLString);

			// set up out communications stuff
			connection = null;

			// Set up the initial connection
			connection = (HttpURLConnection) connectionURL.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			// connection.setReadTimeout(10000);

			connection.connect();

			// read the result from the server
			bufferedReader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			rssContent = new StringBuilder();

			while ((rssLine = bufferedReader.readLine()) != null) {
				rssContent.append(rssLine);
			}
			connection.disconnect();
			// System.out.println(rssContent.toString());

			// Process XML
			// return
			// HubbleXMLParser.startCrawlerForInput(rssContent.toString());
			return null;

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
			rssContent = null;
			connection = null;
		}
		return null;
	}
}
