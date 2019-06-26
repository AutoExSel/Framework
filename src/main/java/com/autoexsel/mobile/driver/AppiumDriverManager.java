package com.autoexsel.mobile.driver;

import org.openqa.selenium.WebElement;

import com.autoexsel.data.manager.DataValidations;
import com.autoexsel.data.manager.Database;
import com.autoexsel.mobile.wrapper.AppiumMobileElement;
import com.autoexsel.mobile.wrapper.Application;
import com.autoexsel.report.manager.ReportManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppiumDriverManager extends AppiumDriverBase {
	public static AppiumDriverManagerImp appiumDriverManager = null;

	static {
		appiumDriverManager = new AppiumDriverManagerImp();
	}

	public static void setAppiumMobileDriver(AppiumDriver<MobileElement> appiumDriver) {
		appiumDriverManager.setAppiumMobileDriver(appiumDriver);
	}

	public static void setAppiumWebDriver(AppiumDriver<WebElement> appiumDriver) {
		appiumDriverManager.setAppiumWebDriver(appiumDriver);
	}

	public static AppiumDriver<MobileElement> launchMobileApplication(AppType appType, String apmServer)
			throws Exception {
		return appiumDriverManager.launchMobileApplication(appType, apmServer);
	}

	public static AppiumDriver<WebElement> launchWebApplication(AppType appType) throws Exception {
		return appiumDriverManager.launchWebApplication(appType);
	}

	public static AppiumDriver<WebElement> launchWebApplication(AppType appType, String apmServer) throws Exception {
		return appiumDriverManager.launchWebApplication(appType, apmServer);
	}

	public static AppiumDriver<MobileElement> getAppiumDriverMobileInstance() {
		return appiumDriverManager.getAppiumDriverMobileInstance();
	}

	public static AppiumDriver<WebElement> getAppiumDriverWebElement() {
		return appiumDriverManager.getAppiumDriverWebInstance();
	}

	public static ReportManager getExtentReportManager() {
		return appiumDriverManager.getExtReportManager();
	}

	public static void printMissingLocators(LocatorType locatorType) {
		appiumDriverManager.printMissingLocators(locatorType);
	}

	public static void openURL(String url) throws Exception {
		appiumDriverManager.openURL(url);
	}

	public static void closeApplication() {
		appiumDriverManager.closeApplication();
	}

	public static AppiumDriver<MobileElement> restartMobileApplication(AppType appType) throws Exception {
		return appiumDriverManager.restartMobileApplication(appType);
	}

	public static AppiumDriver<WebElement> restartWebApplication(AppType appType) throws Exception {
		return appiumDriverManager.restartWebApplication(appType);
	}

	public static ReportManager startReports(String reportName) {
		return appiumDriverManager.startReports(reportName);
	}

	public static void endReports() {
		appiumDriverManager.endReports();
	}

	public static ReportManager startTest(String testName) {
		return appiumDriverManager.startTest(testName);
	}

	public static void endTest() {
		appiumDriverManager.endTest();
	}

	public static ReportManager addTestCategory(String testcategory) {
		return appiumDriverManager.addTestCategory(testcategory);
	}

	public static String setStepName(String stepName) {
		return appiumDriverManager.setStepName(stepName);
	}

	public static void reportPass(String message) {
		appiumDriverManager.reportPass(message);
	}

	public static void reportFail(String message) {
		appiumDriverManager.reportFail(message);
	}

	public static void reportInfo(String message) {
		appiumDriverManager.reportInfo(message);
	}

	public static void reportSkip(String message) {
		appiumDriverManager.reportSkip(message);
	}

	public static AppiumMobileElement findByXpath(String xpath) {
		return appiumDriverManager.findByXpath(xpath);
	}

	public static AppiumMobileElement findById(String id) {
		return appiumDriverManager.findById(id);
	}

	public static AppiumMobileElement findByName(String name) {
		return appiumDriverManager.findByName(name);
	}

	public static AppiumMobileElement findByAccessibilityId(String id) {
		return appiumDriverManager.findByAccessibilityId(id);
	}

	public static AppiumMobileElement findByAny(String locator) {
		return appiumDriverManager.findByAny(locator);
	}

	public static AppiumMobileElement findByAny(MobileElement element) {
		return appiumDriverManager.findByAny(element);
	}

	public static DataValidations verify(Object actual) {
		return appiumDriverManager.verify(actual);
	}

	public static Application application() {
		return appiumDriverManager.application();
	}
	
	public static Database db() {
		return appiumDriverManager.database();
	}

	public static void captureScreenshot() {
		appiumDriverManager.captureScreenshot();
	}

	public static void captureScreenshot(String screenName) {
		appiumDriverManager.captureScreenshot(screenName);
	}
}
