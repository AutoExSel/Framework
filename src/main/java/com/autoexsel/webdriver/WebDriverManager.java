package com.autoexsel.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autoexsel.data.manager.DataValidations;
import com.autoexsel.data.manager.Database;
import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.data.manager.ORLoader;
import com.autoexsel.report.manager.ReportManager;
import com.autoexsel.webdriver.wrapper.SeleniumWebElement;
import com.autoexsel.webdriver.wrapper.WebBrowser;

public class WebDriverManager extends WebDriverBase {

	public WebDriverManager() {
		initiateClassObjects();
	}

	public WebDriver getWebDriverInstance() {
		return seleniumWebDriver.driver;
	}

	public ReportManager startReports(String reportName) {
		if (reportManager == null) {
			reportManager = new ReportManager(configLoader.getReportType());
			reportManager.startReports(reportName);
		}
		System.out.println("reportManager:" + reportManager);
		return reportManager;
	}

	public static ReportManager startTest(String testName) {
		initiateClassObjects();
		reportManager.startTest(testName);
		return reportManager;
	}

	public static void endTest() {
		reportManager.endTest();
	}

	public static void closeExecution() {
		teardownExecution();
	}

	public static void teardown() {
		teardownExecution();
	}

	public static WebDriver openURL(String url) {
		initiateClassObjects();
		if (seleniumWebDriver == null) {
			seleniumWebDriver = new SeleniumWebDriver();
			findWebElements = new FindWebElements(seleniumWebDriver);
			seleniumWebElement = new SeleniumWebElement(seleniumWebDriver);
		}
		webBrowser.openURL(url);
		return seleniumWebDriver.driver;
	}

	public static ReportManager addTestCategory(String testcategory) {
		reportManager.assignCatetory(testcategory);
		category = testcategory;
		return reportManager;
	}

	public static void printMissingLocators(LocatorType locatorType) {
		if (configLoader == null) {
			configLoader = new JSONLoader();
			configLoader.loadConfig();
			ORLoader.printMissingRepositories(locatorType.toString());
		}
	}
	
	public static Database db() {
		return database;
	}

	public static String setStepName(String stepName) {
		return setFullStepName(stepName);
	}

	public static String setDefaultStepName() {
		return setFullStepName("");
	}

	public static void reportPass(String stepName, String message) {
		reportManager.reportPass(stepName, message);
	}

	public static void reportFail(String stepName, String message) {
		reportManager.reportFail(stepName, message);
	}

	public static void reportInfo(String stepName, String message) {
		reportManager.reportInfo(stepName, message);
	}

	public static SeleniumWebElement findByXpath(String xpath) {
		return findWebElements.byXpath(xpath);
	}

	public static SeleniumWebElement findById(String id) {
		return findWebElements.byId(id);
	}

	public static SeleniumWebElement findByName(String name) {
		setFullStepName("");
		return findWebElements.byName(name);
	}

	public static SeleniumWebElement findByAny(WebElement element) {
		return findWebElements.byAny(element);
	}

	public static SeleniumWebElement findByAny(String locator) {
		return findWebElements.byAny(locator);
	}

	public static void captureScreenshot() {
		setFullStepName("");
		reportManager.takeScreenshot(seleniumWebDriver.driver, category + "_" + index);
		index = index + 1;
	}

	public static void captureScreenshot(String screenName) {
		setFullStepName("");
		reportManager.takeScreenshot(seleniumWebDriver.driver, category + "_" + index);
		index = index + 1;
	}

	public static DataValidations verify(Object actual) {
		dataValidations.setActualValue(actual);
		return dataValidations;
	}

	public static WebBrowser browser(Object actual) {
		return webBrowser;
	}
}
