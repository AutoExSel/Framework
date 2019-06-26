package com.autoexsel.mobile.driver;

import org.json.JSONException;
import org.openqa.selenium.WebElement;

import com.autoexsel.data.manager.DataValidations;
import com.autoexsel.data.manager.Database;
import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.data.manager.ORLoader;
import com.autoexsel.mobile.wrapper.AppiumMobileElement;
import com.autoexsel.mobile.wrapper.Application;
import com.autoexsel.report.manager.ReportManager;
import com.autoexsel.services.wrapper.RestAssuredManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppiumDriverBase {
	
	public static enum AppType {
		ANDROIDAPP, ANDROIDWEB, IOSAPP, IOSWEB
	};

	public static enum LocatorType {
		Accessibility_ID, XPath, Name, Class, ID
	};

	protected static AppiumDriver<MobileElement> appiumDriverMobileApp = null;
	protected static AppiumDriver<WebElement> appiumDriverWebApp = null;
	protected static AppiumMobileElement appiumMobileElement = null;
	protected static AppiumMobileDriver appiumMobileDriver = null;
	protected static AppiumServerManager appiumServerManager = null;
	protected static FindAppiumMobileElement findAppiumMobileElements = null;
	protected static String appiumServer = null;
	protected static Application application = null;

	protected static RestAssuredManager restAssuredManager = null;
	protected static Database database = null;
	protected static JSONLoader configLoader = null;
	protected static String appiumHost = null;
	protected static ReportManager reportManager = null;
	protected static boolean stepNameIsSet = false;
	protected static String suite = "default";
	protected static String env = "qa";
	protected static String lastFunctionName = "";
	protected static String resultLocation;
	protected static DataValidations dataValidations = null;
	public static String category = "DefaultTestCategory";
	public static int index = 1;
	public static String alias = "";
	
	public ReportManager getReportManager() {
		return reportManager;
	}

	protected String setFullStepName(String stepName) {
		String parentFunction = Thread.currentThread().getStackTrace()[4].getMethodName();
		if (!lastFunctionName.equalsIgnoreCase(parentFunction)) {
			lastFunctionName = parentFunction;
			parentFunction = parentFunction.replace("_", " ");
			parentFunction = stepName.trim() + " " + parentFunction;
			reportManager.setStepName(parentFunction, true);
		}
		return lastFunctionName;
	}

	protected void initiateReportManager() {
		if (reportManager == null) {
			reportManager = new ReportManager(getReportType());
			resultLocation = reportManager.startReports(getReportName());
		}
	}

	protected void initiateClassObjects(AppType appType) {
		if (configLoader == null) {
			configLoader = new JSONLoader();
			configLoader.loadConfig();
			ORLoader.loadObjectRepositories(appType.toString());
		}
		initiateReportManager();
		if (database == null) {
			database = new Database();
		}
		if (JSONLoader.config != null && !JSONLoader.config.isNull("api-testing")) {
			try {
				if (JSONLoader.config.getString("api-testing").equalsIgnoreCase("true")) {
					restAssuredManager = new RestAssuredManager();
					RestAssuredManager.setupReportAndConfigManager(reportManager, configLoader);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (application == null) {
			application = new Application();
		}
		if (dataValidations == null) {
			dataValidations = new DataValidations(reportManager);
		}
	}

	protected String getReportType() {
		String reportType = "aventstack";
		if (JSONLoader.config != null && !JSONLoader.config.isNull("report-type")) {
			try {
				reportType = JSONLoader.config.getString("report-type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return reportType;
	}

	private String getReportName() {
		String result = "defaultSuite";
		if (JSONLoader.config != null) {
			try {
				result = JSONLoader.getConfigValue("suite/" + suite);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	protected void quitApp() {
		if (appiumDriverMobileApp != null) {
			appiumDriverMobileApp.quit();
		}
		if (appiumDriverWebApp != null) {
			appiumDriverWebApp.quit();
		}
		if (appiumServerManager != null) {
			appiumServerManager.stopServer();
		}
	}

	protected void closeApp() {
		if (appiumDriverMobileApp != null) {
			appiumDriverMobileApp.quit();
		}
		if (appiumDriverWebApp != null) {
			appiumDriverWebApp.quit();
		}
	}

	protected String lastFunctionName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	protected String currentFunction() {
		String functionName = Thread.currentThread().getStackTrace()[3].getMethodName();
		functionName = functionName.replace("_", " ");
		return functionName;
	}
}
