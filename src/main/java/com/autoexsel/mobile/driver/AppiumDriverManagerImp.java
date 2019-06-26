package com.autoexsel.mobile.driver;

import org.openqa.selenium.WebElement;

import com.autoexsel.data.manager.DataValidations;
import com.autoexsel.data.manager.Database;
import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.data.manager.ORLoader;
import com.autoexsel.mobile.wrapper.AppiumMobileElement;
import com.autoexsel.mobile.wrapper.Application;
import com.autoexsel.report.manager.ReportManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppiumDriverManagerImp extends AppiumDriverBase {


	public void setAppiumMobileDriver(AppiumDriver<MobileElement> appiumDriver) {
		appiumDriverMobileApp = appiumDriver;
		findAppiumMobileElements = new FindAppiumAppElement(new AppiumMobileDriver());
	}
	public void setAppiumWebDriver(AppiumDriver<WebElement> appiumDriver) {
		appiumDriverWebApp = appiumDriver;
		findAppiumMobileElements = new FindAppiumAppElement(new AppiumMobileDriver());
	}

	public AppiumDriver<MobileElement> launchMobileApplication(AppType appType) throws Exception {
		initiateClassObjects(appType);
		appiumHost = appiumServerManager.startAppiumServer();
		return launchMobileApplications(appType, appiumHost);
	}

	public AppiumDriver<MobileElement> launchMobileApplication(AppType appType, String apmServer) throws Exception {
		initiateClassObjects(appType);
		appiumServer = apmServer;
		appiumHost = "http://" + appiumServer + "/wd/hub";
		return launchMobileApplications(appType, appiumHost);
	}

	public AppiumDriver<MobileElement> launchMobileApplications(AppType appType, String appiumHost) throws Exception {
		appiumServerManager = new AppiumServerManager();
		appiumMobileDriver = new AppiumMobileDriver();
		switch (appType) {
		case IOSAPP:
			appiumDriverMobileApp = appiumMobileDriver.launchIOSApplication(appiumHost);
			break;
		case ANDROIDAPP:
			appiumDriverMobileApp = appiumMobileDriver.launchAndroidApplication(appiumHost);
			break;
		default:
			break;
		}
		findAppiumMobileElements = new FindAppiumAppElement(appiumMobileDriver);
		return appiumDriverMobileApp;
	}

	public AppiumDriver<WebElement> launchWebApplication(AppType appType) throws Exception {
		initiateClassObjects(appType);
		appiumHost = appiumServerManager.startAppiumServer();
		return launchWebApplications(appType, appiumHost);
	}

	public AppiumDriver<WebElement> launchWebApplication(AppType appType, String apmServer) throws Exception {
		initiateClassObjects(appType);
		appiumServer = apmServer;
		appiumHost = "http://" + appiumServer + "/wd/hub";
		return launchWebApplications(appType, appiumHost);
	}

	public AppiumDriver<WebElement> launchWebApplications(AppType appType, String appiumHost) throws Exception {
		appiumServerManager = new AppiumServerManager();
		appiumMobileDriver = new AppiumMobileDriver();
		switch (appType) {
		case IOSWEB:
			appiumDriverWebApp = appiumMobileDriver.launchIOSWebApplication(appiumHost);
			break;
		case ANDROIDWEB:
			appiumDriverWebApp = appiumMobileDriver.launchAndroidWebApplication(appiumHost);
			break;
		default:
			break;
		}
		findAppiumMobileElements = new FindAppiumWebElement(appiumMobileDriver);
		return appiumDriverWebApp;
	}

	public AppiumDriver<MobileElement> restartMobileApplication(AppType appType) throws Exception {
		closeApp();
		return launchMobileApplication(appType, appiumServer);
	}

	public AppiumDriver<WebElement> restartWebApplication(AppType appType) throws Exception {
		closeApp();
		return launchWebApplication(appType, appiumServer);
	}

	public void printMissingLocators(LocatorType locatoryType) {
		if (configLoader == null) {
			configLoader = new JSONLoader();
			configLoader.loadConfig();
			ORLoader.printMissingRepositories(locatoryType.toString());
		}
	}
	public void openURL(String url) throws Exception {
		appiumDriverWebApp.get(url);
	}

	public AppiumDriver<MobileElement> getAppiumDriverMobileInstance() {
		return appiumDriverMobileApp;
	}

	public AppiumDriver<WebElement> getAppiumDriverWebInstance() {
		return appiumDriverWebApp;
	}

	public void closeApplication() {
//		quitApp();
		if (reportManager != null) {
			endReports();
		}
		database.closeDatabseConnection();
		System.out.println("\nExecution Result: "+resultLocation);
	}

	public ReportManager startReports(String reportName) {
		if (reportManager == null) {
			reportManager = new ReportManager(getReportType());
			reportManager.startReports(reportName);
		}
		System.out.println("reportManager:" + reportManager);
		return reportManager;
	}

	public ReportManager getExtReportManager() {
		return reportManager;
	}

	public void endReports() {
		reportManager.endTest();
		reportManager.flush();
		reportManager.close();
		reportManager = null;
	}

	public ReportManager startTest(String testName) {
//		assignCategory = true;
		initiateReportManager();
		reportManager.startTest(testName);
		return reportManager;
	}

	public void endTest() {
		reportManager.endTest();
	}

	public ReportManager addTestCategory(String testcategory) {
//		if (!category.equalsIgnoreCase(testcategory)) {
//			System.out.println("Test report category: " + testcategory);
			reportManager.assignCatetory(testcategory);
			category = testcategory;
//		}
		return reportManager;
//		if (assignCategory) {
//			reportManager.assignCatetory(testcategory);
//			category = testcategory;
//			assignCategory = false;
//		}
	}

	public String setStepName(String stepName) {
		return setFullStepName(stepName);
	}

	public void reportPass(String message) {
		reportManager.reportPass(message);
	}

	public void reportFail(String message) {
		reportManager.reportFail(message);
	}

	public void reportInfo(String message) {
		reportManager.reportInfo(message);
	}

	public void reportSkip(String message) {
		reportManager.reportSkip(message);
	}

	public AppiumMobileElement findByXpath(String xpath) {
		setFullStepName("");
		return findAppiumMobileElements.byXpath(xpath);
	}

	public AppiumMobileElement findById(String id) {
		setFullStepName("");
		return findAppiumMobileElements.byId(id);
	}

	public AppiumMobileElement findByName(String name) {
		setFullStepName("");
		return findAppiumMobileElements.byName(name);
	}

	public AppiumMobileElement findByAccessibilityId(String id) {
		setFullStepName("");
		return findAppiumMobileElements.byAccessibilityId(id);
	}

	public AppiumMobileElement findByAny(String locator) {
		setFullStepName("");
		return findAppiumMobileElements.byAny(locator);
	}

	public AppiumMobileElement findByAny(MobileElement element) {
		setFullStepName("");
		return findAppiumMobileElements.byAny(element);
	}

	public void updateRunTimeTestData(String key, String value) {
//		AppiumDriverBase.genericWebElement.alertSendKeys(keys);
	}

	public void captureScreenshot() {
		setFullStepName("");
		reportManager.takeScreenshot(appiumMobileDriver.getMobileDriver(), category + "_" + index);
		index = index + 1;
	}

	public void captureScreenshot(String screenName) {
		setFullStepName("");
		reportManager.takeScreenshot(appiumMobileDriver.getMobileDriver(), category + "_" + index);
		index = index + 1;
	}

	public DataValidations verify(Object actual) {
		dataValidations.setActualValue(actual);
		return dataValidations;
	}
	
	public Application application() {
		return application;
	}
	
	public Database database() {
		return database;
	}

}
