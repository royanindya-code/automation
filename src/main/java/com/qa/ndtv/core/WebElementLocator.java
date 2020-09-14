package com.qa.ndtv.core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * Web element locator for each locator available in the yaml configuration file.
 *
 */
public class WebElementLocator {

	// Returns web element
	public static WebElement getElement(Object element) throws BaseException {
		return SessionManager.getDriver().findElement(getLocators(element));
	}

	// Returns web elements
	public static List<WebElement> getElements(Object element) throws BaseException {
		return SessionManager.getDriver().findElements(getLocators(element));
	}

	// Adding dynamic content in the web element and returns as web element
	public static WebElement getElement(Object element, Object... args) throws BaseException {
		element = String.format((String) element, args);
		return SessionManager.getDriver().findElement(getLocators(element));
	}

	// Converts element to By class in selenium.
	private static By getLocators(Object element) throws BaseException {
		String element1 = (String) element;
		if (element1.startsWith("XPATH=")) {
			element1 = element1.replace("XPATH=", "");
			return By.xpath(element1);
		} else if (element1.startsWith("ID=")) {
			element1 = element1.replace("ID=", "");
			return By.id(element1);
		}
		throw new BaseException("This locator - " + element1
				+ " is not in expected format. Please note that - It has to start with this format (i.e - XPATH= / CSS= / ID=");
	}
}
