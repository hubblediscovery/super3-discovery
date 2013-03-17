package com.hubble.store.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HubbleConcurrentDownloader implements Callable<String> {

	private HttpURLConnection connection;
	private BufferedReader bufferedReader;
	private final StringBuilder rssContent;
	private String rssLine = null;
	private URL connectionURL;
	private final String connectionURLString;
	private final String downloaderThreadName;

	public HubbleConcurrentDownloader(final String downloadURL,
			final String downloaderName) {
		this.connectionURLString = downloadURL;
		this.downloaderThreadName = downloaderName;
		this.rssContent = new StringBuilder();
		this.connection = null;
		this.bufferedReader = null;
		this.connectionURL = null;

		try {
			this.connectionURL = new URL(connectionURLString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String call() throws InterruptedException {
		try {

			// Set up the initial connection
			connection = (HttpURLConnection) connectionURL.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			// connection.setReadTimeout(10000);

			connection.connect();

			// read the result from the server
			bufferedReader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((rssLine = bufferedReader.readLine()) != null) {
				rssContent.append(rssLine);
			}
			connection.disconnect();
			// System.out.println(rssContent.toString());

			// Process XML
			return rssContent.toString();

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
			connection = null;
		}
		return null;
	}

	@Override
	public String toString() {
		return downloaderThreadName;
	}
}
