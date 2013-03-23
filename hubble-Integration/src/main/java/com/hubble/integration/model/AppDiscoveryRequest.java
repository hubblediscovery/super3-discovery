package com.hubble.integration.model;

import com.appdisc.trend.profile.Location;
import com.hubble.userprofile.types.ClientLoginType;

/**
 * 
 * Input request with access token and other additional input details 
 * 
 * @author deepakvp
 *
 */

public class AppDiscoveryRequest {
	
	private String userName;
	private String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String accessToken;
	private Location currentDeviceLocation;
	private ClientLoginType clientType;
	
	public AppDiscoveryRequest() {
		new AppDiscoveryRequest(null, null, null, null);
	}
	
	public AppDiscoveryRequest(String userName, String accessToken,
			Location currentDeviceLocation, ClientLoginType clientType) {
		super();
		this.userName = userName;
		this.accessToken = accessToken;
		this.currentDeviceLocation = currentDeviceLocation;
		this.clientType = clientType;
	}
	
	public ClientLoginType getClientType() {
		return clientType;
	}
	public void setClientType(ClientLoginType clientType) {
		this.clientType = clientType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Location getCurrentDeviceLocation() {
		return currentDeviceLocation;
	}
	public void setCurrentDeviceLocation(Location currentDeviceLocation) {
		this.currentDeviceLocation = currentDeviceLocation;
	}
	
	

}
