package com.qa.ndtv.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	private static final String PROPERTY_FILE = "ndtv.properties";
	private static final String CHROME_DRIVER_PATH = "//Resources//Web//Mac//Browser//Chrome//chromedriver";
	private static final String FIREFOX_DRIVER_PATH = "//Resources//Web//Mac//Browser//Firefox//geckodriver";
	private static Properties prop;
	private static SessionManager sessionManager;

	@BeforeTest
	public void beforeTest() throws IOException {
		prop = new Properties();
		FileInputStream fileReader = new FileInputStream(new File(System.getProperty("user.dir") +"//src//test//resources//" +PROPERTY_FILE));
		prop.load(fileReader);
		InitSessionManager();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void customBeforeMethod(Method method) throws MalformedURLException {
		WebDriver driver = null;
		String browserType = "";
		String apiKey = prop.getProperty("apiKey");
		if(System.getProperty("gridUrl").equalsIgnoreCase("localhost")) {
			if(System.getProperty("browser") != null) {
				browserType = System.getProperty("browser");
			} else {
				browserType = prop.getProperty("browser");
			}
			if(browserType.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+CHROME_DRIVER_PATH);
				driver = new ChromeDriver();
			} else if(browserType.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+FIREFOX_DRIVER_PATH);
				driver = new FirefoxDriver();			
			}			
		} else {
			DesiredCapabilities capability = null;
			if(System.getProperty("browser") != null) {
				browserType = System.getProperty("browser");
			} else {
				browserType = prop.getProperty("browser");
			}
			if(browserType.equalsIgnoreCase("Chrome")) {
				capability = DesiredCapabilities.chrome();
			} else if(browserType.equalsIgnoreCase("firefox")) {
				capability = DesiredCapabilities.firefox();		
			}
			capability.setBrowserName(browserType);
			capability.setPlatform(Platform.ANY);
			driver = new RemoteWebDriver(new URL(System.getProperty("gridUrl")), capability);
		}
		
		String name = method.getName() + method.getParameters();
		Thread.currentThread().setName(name);
		sessionManager.setDriver(name, driver, apiKey);
		driver.get(prop.getProperty("baseurl"));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void afterMethod() throws BaseException {
		SessionManager.getDriver().close();
	}
	
	private void InitSessionManager() {
		sessionManager = SessionManager.initSession();
	}
}
