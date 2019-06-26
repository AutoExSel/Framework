package com.autoexsel.webdriver;

import org.json.JSONException;

import com.autoexsel.data.manager.DataValidations;
import com.autoexsel.data.manager.Database;
import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.data.manager.ORLoader;
import com.autoexsel.report.manager.ReportManager;
import com.autoexsel.services.wrapper.RestAssuredManager;
import com.autoexsel.webdriver.wrapper.SeleniumWebElement;
import com.autoexsel.webdriver.wrapper.WebBrowser;

public class WebDriverBase {
	public WebDriverBase() {
	}

	public enum LocatorType {
		XPath, Name, Class, ID
	};

	protected static SeleniumWebDriver seleniumWebDriver = null;
	protected static SeleniumWebElement seleniumWebElement = null;
	protected static DataValidations dataValidations = null;
	protected static FindWebElements findWebElements = null;
	protected static ReportManager reportManager = null;
	protected static JSONLoader configLoader = null;
	protected static WebBrowser webBrowser = null;
	protected static String lastFunctionName = "";
	protected static boolean assignCategory = false;
	protected static String suite = "default";
	protected static String env = "qa";
	protected static String resultLocation;
	protected static String category = "DefaultTestCategory";
	protected static int index = 1;

	protected static RestAssuredManager restAssuredManager = null;
	protected static Database database = null;
	protected static String appiumHost = null;
	protected static boolean stepNameIsSet = false;
	protected static boolean objectInitiated = false;
	
	protected static String setStepNameForLocator_delete(String functionName) {
		String stepName = Thread.currentThread().getStackTrace()[3].getMethodName();
		reportManager.setStepName(stepName + "." + functionName);
		return stepName;
	}
	
	protected static void initiateClassObjects() {
		if (configLoader == null) {
			configLoader = new JSONLoader();
			configLoader.loadConfig();
			ORLoader.loadObjectRepositories();
		}
		if (reportManager == null) {
			reportManager = new ReportManager(configLoader.getReportType());
			resultLocation = reportManager.startReports(configLoader.getReportName(suite));
		}
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
		if(dataValidations == null) {
			dataValidations = new DataValidations(reportManager);
		}
		if(webBrowser == null) {
			webBrowser = new WebBrowser();
		}
	}
	
	public static SeleniumWebDriver getSeleniumWebDriver() {
		return seleniumWebDriver;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}
	

	public static void teardownExecution() {
		seleniumWebDriver.close();
		if (reportManager != null) {
			endReports();
		}else {
			System.out.println("Report is already closed or not created.");
		}
	}
	
	public static void endReports() {
		reportManager.endTest();
		reportManager.flush();
		reportManager.close();
		reportManager = null;
	}
	
	protected static String setFullStepName(String stepName) {
		initiateClassObjects();
		String parentFunction = Thread.currentThread().getStackTrace()[3].getMethodName();
		if (!lastFunctionName.equalsIgnoreCase(parentFunction)) {
			lastFunctionName = parentFunction;
			parentFunction = parentFunction.replace("_", " ");
			parentFunction = stepName.trim() + " " + parentFunction;
			reportManager.setStepName(parentFunction.trim(), true);
		}
		return lastFunctionName;
	}

}
