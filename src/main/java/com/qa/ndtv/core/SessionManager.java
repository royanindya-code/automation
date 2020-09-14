package com.qa.ndtv.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;

/**
 * 
 * Stores selenium driver object for each test instance in a separate thread to
 * isolate multiple tests running parallel.
 *
 */
public class SessionManager {
	private static Map<String, Session> sessions_map = new ConcurrentHashMap<>();
	private static SessionManager _instance = null;

	private SessionManager() {
		// Avoid Instantiation
	}

	public static SessionManager initSession() {
		if (_instance == null)
			_instance = new SessionManager();
		return _instance;
	}

	// Sets webdriver instance to the current thread
	public void setDriver(String testCaseName, WebDriver driver, String apiKey) {
		if (sessions_map.containsKey(testCaseName)) {
			return;
		}
		Session session = new Session();
		session.setDriver(driver);
		session.setApiKey(apiKey);
		sessions_map.put(testCaseName, session);
	}

	// Gets webdriver instance from the current thread
	public static WebDriver getDriver() throws BaseException {
		String name = Thread.currentThread().getName();
		if (sessions_map.containsKey(name)) {
			return sessions_map.get(name).getDriver();
		}
		throw new BaseException("WebDriver instance is not avaliable");
	}
	
	public static String getApiKey() {
		String name = Thread.currentThread().getName();
		if (sessions_map.containsKey(name)) {
			return sessions_map.get(name).getApiKey();
		}
		return null;
	}
}

 class Session {
	private WebDriver driver;
	private String apiKey;
	
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
