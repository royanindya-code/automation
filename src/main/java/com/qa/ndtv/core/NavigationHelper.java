package com.qa.ndtv.core;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * Various page operations of selenium will be inside this class.
 *
 */
public class NavigationHelper {
	private static final int EXPLICIT_WAIT_TIMEOUT = 120;

	// Clicks on web element
	public void click(WebElement element) {
		element.click();
	}

	// Switch to active element
	public void switchToActiveElement() throws BaseException {
		SessionManager.getDriver().switchTo().activeElement();
	}

	// Explicit wait for an element
	public void waitForElementByXpath(Object locator) throws BaseException {
		WebDriverWait wait = new WebDriverWait(SessionManager.getDriver(), EXPLICIT_WAIT_TIMEOUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((String) locator)));
	}

	// Gets text of all the web elements and returns list
	public List<String> getText(List<WebElement> elements) throws BaseException {
		List<String> texts = (List<String>) elements.stream().map(WebElement::getText).collect(Collectors.toList());
		return texts;
	}

	// Return text of the web element
	public String getText(WebElement element) throws BaseException {
		return element.getText();
	}

	// Enter text of a particular field
	public void enterText(WebElement element, String... args) {
		element.sendKeys(args);
	}
}
