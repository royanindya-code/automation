package com.qa.ndtv.pages.home;

import java.util.HashMap;
import java.util.Map;

import com.qa.ndtv.core.BaseException;
import com.qa.ndtv.core.CommonHelper;
import com.qa.ndtv.core.NavigationHelper;
import com.qa.ndtv.pages.weather.WeatherPage;

import static com.qa.ndtv.core.WebElementLocator.getElement;;

public class HomePage {
	private static Map<String, Object> _locatorMap = new HashMap<>();
	private static final CommonHelper _commonHelper = new CommonHelper();
	private static final NavigationHelper _navigationHelper = new NavigationHelper();
	
	public HomePage() {
		_locatorMap = _commonHelper.readLocators(System.getProperty("user.dir")+"//Resources//Locators//Home");
	}
	
	public HomePage clickNoThanksPopUp() throws BaseException, InterruptedException {
		_navigationHelper.waitForElementByXpath(_locatorMap.get("Notification_Wrap"));
		_navigationHelper.click(getElement(_locatorMap.get("NO_Thanks_ELE")));
		return this;
	}
	
	public HomePage clickOnSubMenu() throws BaseException {
		_navigationHelper.click(getElement(_locatorMap.get("Sub_Menu_Ele")));
		return this;
	}
	
	public WeatherPage clickOnWeather() throws BaseException {
		_navigationHelper.click(getElement(_locatorMap.get("TopNav_Heading_Locator"), "WEATHER"));
		return new WeatherPage();
	}

}
