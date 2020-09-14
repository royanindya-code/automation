package com.qa.ndtv.test.weather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.qa.ndtv.core.BaseException;
import com.qa.ndtv.core.CommonHelper;
import com.qa.ndtv.core.ExcelReader;
import com.qa.ndtv.pages.home.HomePage;
import com.qa.ndtv.pages.weather.WeatherPage;

import io.restassured.response.Response;

public class TemperatureTest extends BaseWeatherTest {

	@DataProvider(name = "testDataCityTemp", parallel = true)
	public Object[][] createTestDataCityTemp() throws FileNotFoundException, IOException {
		return ExcelReader.readExcel(System.getProperty("user.dir") + "//Resources//TestData//TemperatureTest.xlsx", "testTemperature");
	}

	@Test(dataProvider = "testDataCityTemp")
	public void testTemperature(String city, double degreesDifference, double farenheitDifference)
			throws BaseException, InterruptedException {
		HomePage homePage = new HomePage();
		CommonHelper commonHelper = new CommonHelper();

		Reporter.log("Open ndtv page in browser and navigate to weather page");
		WeatherPage weatherPage = homePage.clickNoThanksPopUp().clickOnSubMenu().clickOnWeather();

		Reporter.log("Get all the cities listed in the dropdown");
		List<String> cities = weatherPage.getCities();

		Reporter.log("Validate if " + city + " present in the list of avaliable cities");
		Assert.assertTrue(commonHelper.isPresent(cities, city));

		Reporter.log("Enter the " + city + " and get the weather details");
		weatherPage.enterCity(city).selectCityWeather(city);

		Reporter.log("Get the tempature in degrees and farenheit unit of the selected city");
		double uiTempInDegrees = Double.parseDouble(weatherPage.getTempInDegrees().replaceAll("[^0-9]", ""));
		double uiTempInFarenheit = Double.parseDouble(weatherPage.getTempInFahrenheit().replaceAll("[^0-9]", ""));

		Reporter.log("Tempareture of the city in degree as double unit " + uiTempInDegrees);
		Reporter.log("Tempareture of the city in farenheit as double unit " + uiTempInFarenheit);

		Reporter.log("Trigger api response to get the temprature from reponse");
		Response response = weatherPage.citeWeatherApi(city);
		Double value = JsonPath.parse(response.asString()).read("$['main']['temp']", Double.class);
		Reporter.log("The temperature received from the api response is " + value + " in pascal unit");

		double apiTempInDgrees = (value - 273.15);
		double apiTempInFarenheit = ((((value - 273.15) * 9) / 5) + 32);
		Reporter.log("Coverted temperature received from api response to degress is " + apiTempInDgrees);
		Reporter.log("Coverted temperature received from api response to farenheit is " + apiTempInFarenheit);

		Reporter.log("Verify temerature for degrees received in ui " + uiTempInDegrees
				+ " along with the tempreature received in api response " + apiTempInDgrees
				+ " is falling under permissable variance of " + degreesDifference);
		Assert.assertTrue(commonHelper.compare(apiTempInDgrees, uiTempInDegrees, degreesDifference),
				"Temperature of weather in ui " + uiTempInDegrees + " in degrees is not within permissable range of "
						+ degreesDifference + " with the temperature in api " + apiTempInDgrees);

		Reporter.log("Verify temerature for farenheit received in ui " + uiTempInFarenheit
				+ " along with the tempreature received in api response " + apiTempInFarenheit
				+ " is falling under permissable variance of " + farenheitDifference);
		Assert.assertTrue(commonHelper.compare(apiTempInFarenheit, uiTempInFarenheit, farenheitDifference),
				"Temperature of weather in ui " + uiTempInFarenheit
						+ " in farenheit is not within permissable range of " + farenheitDifference
						+ " with the temperature in api " + apiTempInFarenheit);
	}
}
