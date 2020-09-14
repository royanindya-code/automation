package com.qa.ndtv.pages.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.qa.ndtv.api.WeatherApi;
import com.qa.ndtv.core.BaseException;
import com.qa.ndtv.core.CommonHelper;
import com.qa.ndtv.core.NavigationHelper;

import io.restassured.response.Response;

import static com.qa.ndtv.core.WebElementLocator.*;

public class WeatherPage {
	private static Map<String, Object> _locatorMap = new HashMap<>();
	private static final CommonHelper _commonHelper = new CommonHelper();
	private static final NavigationHelper _navigationHelper = new NavigationHelper();
	private static final WeatherApi _weatherApi = new WeatherApi();
	
	public WeatherPage() {
		_locatorMap = _commonHelper.readLocators(System.getProperty("user.dir")+"//Resources//Locators//Weather");
	}
	
	public List<String> getCities() throws BaseException {
		return _navigationHelper.getText(getElements(_locatorMap.get("Weather_place_Ele")));
	}
	
	public void uncheckAllCities() throws BaseException {
		List<WebElement> elements = getElements(_locatorMap.get("Weather_place_input_ele"));
		for(WebElement element: elements) {
			if(element.isSelected()) {
				_navigationHelper.click(element);
			}
		}
	}
	
	public WeatherPage enterCity(String city) throws BaseException {
		uncheckAllCities();
		_navigationHelper.enterText(getElement(_locatorMap.get("Search_city_ele")), city);
		selectCity(city);
		return this;
	}
	
	public WeatherPage selectCity(String city) throws BaseException {
		_navigationHelper.click(getElement(_locatorMap.get("Select_City_Ele"), city));
		return this;
	}
	
	public WeatherPage selectCityWeather(String city) throws BaseException {
		_navigationHelper.click(getElement(_locatorMap.get("Select_City_Temp_ele"), city));
		return this;
	}
	
	public String getCityName(String city) throws BaseException {
		return _navigationHelper.getText(getElement(_locatorMap.get("City_Name_Ele"), city));
	}
	
	public String getCondition() throws BaseException {
		return _navigationHelper.getText(getElement(_locatorMap.get("City_Weather_Common_Ele"), "Condition"));
	}
	
	public String getWind() throws BaseException {
		return _navigationHelper.getText(getElement(_locatorMap.get("City_Weather_Common_Ele"), "Wind"));
	}
	
	public String getHumidity() throws BaseException {
		return _navigationHelper.getText(getElement(_locatorMap.get("City_Weather_Common_Ele"), "Humidity"));
	}
	
	public String getTempInDegrees() throws BaseException {
		return _navigationHelper.getText(getElement(_locatorMap.get("City_Weather_Common_Ele"), "Temp in Degrees"));
	}
	
	public String getTempInFahrenheit() throws BaseException {
		return _navigationHelper.getText(getElement(_locatorMap.get("City_Weather_Common_Ele"), "Temp in Fahrenheit"));
	}
	
	public Response citeWeatherApi(String city) {
		return _weatherApi.weatherApiRequest(city);
	}
}
