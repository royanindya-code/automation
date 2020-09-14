package com.qa.ndtv.api;

import io.restassured.response.Response;

public class WeatherApi extends BaseApi {
	
	public Response weatherApiRequest(String city) {
		return get("weather?q=" +city);
	}

}
