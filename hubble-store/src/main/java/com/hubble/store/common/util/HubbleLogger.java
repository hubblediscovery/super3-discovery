package com.hubble.store.common.util;

import java.util.List;

//import java.util.logging.Logger;

public class HubbleLogger {
	
	//private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void printMessage(String message) {
		//logger.info(message);
		System.out.println(message);
	}
	
	public static void printMessage(int message) {
		//logger.info(message);
		System.out.println(message);
	}
	
	public static void printErrorMessage(String message) {
		//logger.info(message);
		System.out.println(message);
	}
	
	public static void printList(List<String> list) {
		for(String message : list) 
			printMessage(message);
	}
	

}
