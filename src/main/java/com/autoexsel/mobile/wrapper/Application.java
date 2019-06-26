package com.autoexsel.mobile.wrapper;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Application extends AppiumWrapperBase {

	public void scrollUp() {
		JavascriptExecutor js = null;
		if (appiumMobileDriver.getMobileDriver() != null) {
			js = appiumMobileDriver.getMobileDriver();
		} else if (appiumMobileDriver.getWebDriver() != null) {
			js = appiumMobileDriver.getWebDriver();
		}
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: scroll", scrollObject);
		reportManager.reportPass("scrollDown");
	}

	public void scrollDown() {
		JavascriptExecutor js = null;
		if (appiumMobileDriver.getMobileDriver() != null) {
			js = appiumMobileDriver.getMobileDriver();
		} else if (appiumMobileDriver.getWebDriver() != null) {
			js = appiumMobileDriver.getWebDriver();
		}
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
		reportManager.reportPass("scrollDown");
	}

	public void swipeLeft() {
		JavascriptExecutor js = null;
		if (appiumMobileDriver.getMobileDriver() != null) {
			js = appiumMobileDriver.getMobileDriver();
		} else if (appiumMobileDriver.getWebDriver() != null) {
			js = appiumMobileDriver.getWebDriver();
		}
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "left");
		js.executeScript("mobile: swipe", scrollObject);
		reportManager.reportPass("scrollDown");
	}

	public void swipeRight() {
		JavascriptExecutor js = null;
		if (appiumMobileDriver.getMobileDriver() != null) {
			js = appiumMobileDriver.getMobileDriver();
		} else if (appiumMobileDriver.getWebDriver() != null) {
			js = appiumMobileDriver.getWebDriver();
		}
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "right");
		js.executeScript("mobile: swipe", scrollObject);
		reportManager.reportPass("scrollDown");
	}

	public void alertAccept() {
		if (appiumMobileDriver.getMobileDriver() != null) {
			System.out.println("Before Alert");
			appiumMobileDriver.getMobileDriver().switchTo().alert().accept();
			System.out.println("After Alert");
		} else if (appiumMobileDriver.getWebDriver() != null) {
			appiumMobileDriver.getWebDriver().switchTo().alert().accept();
		}
		reportManager.reportPass("Alert Accepted !!!!!");
		System.out.println("Alert Accepted !!!!!");
	}

	public void alertDismiss() {
		if (appiumMobileDriver.getMobileDriver() != null) {
			appiumMobileDriver.getMobileDriver().switchTo().alert().dismiss();
		} else if (appiumMobileDriver.getWebDriver() != null) {
			appiumMobileDriver.getWebDriver().switchTo().alert().dismiss();
		}
		reportManager.reportPass("Alert Dismissed!!!!!");
		System.out.println("Alert Dismissed !!!!!");
	}

	public String getAlertText() {
		String alertText = "";
		if (appiumMobileDriver.getMobileDriver() != null) {
			alertText = appiumMobileDriver.getMobileDriver().switchTo().alert().getText();
		} else if (appiumMobileDriver.getWebDriver() != null) {
			alertText = appiumMobileDriver.getWebDriver().switchTo().alert().getText();
		}
		reportManager.reportPass("Retrieved \"" + alertText + "\" from Alert input box.");
		System.out.println("Retrieved \"" + alertText + "\" from Alert input box.");
		return alertText;
	}

	public void sendAlertKeys(String keys) {
		if (appiumMobileDriver.getMobileDriver() != null) {
			appiumMobileDriver.getMobileDriver().switchTo().alert().sendKeys(keys);
		} else if (appiumMobileDriver.getWebDriver() != null) {
			appiumMobileDriver.getWebDriver().switchTo().alert().sendKeys(keys);
		}
		reportManager.reportPass("Entered \"" + keys + "\" in Alert input box.");
		System.out.println("Entered \"" + keys + "\" in Alert input box.");
	}

	public boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = null;
		if (appiumMobileDriver.getMobileDriver() != null) {
			wait = new WebDriverWait(appiumMobileDriver.getMobileDriver(), 0 /* timeout in seconds */);
		} else if (appiumMobileDriver.getWebDriver() != null) {
			wait = new WebDriverWait(appiumMobileDriver.getWebDriver(), 0 /* timeout in seconds */);
		}
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}

}
