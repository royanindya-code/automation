package com.qa.ndtv.api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import com.qa.ndtv.core.SessionManager;

public class BaseApi {

	private static final String SERVICE_URL = "http://api.openweathermap.org/data";
	private static final String VERSION = "/2.5/";
	private static final String API_ID =  "&appid=" + SessionManager.getApiKey();
	
	protected Response get(String path) {
		System.out.println(SERVICE_URL + VERSION + path + API_ID);
		return given().get(SERVICE_URL + VERSION + path + API_ID)
				.then()
				.log().ifError()
				.statusCode(200)
				.extract()
				.response();
	}
}
