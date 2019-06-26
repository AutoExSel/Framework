package com.autoexsel.services.wrapper;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.data.manager.ORLoader;
import com.autoexsel.report.manager.ReportManager;

import io.restassured.response.Response;

public class RestAssuredManager extends RestAssuredBase {

	public static void startTest(String testName) {
		initiateClassObjects();
		reportManager.startTest(testName);
	}

	public static Response httpGet(String endPoint, boolean writeToFile) {
		endPoint = initializeRequestSpec(endPoint);
		restAssuredMethods.get(endPoint);
		if (writeToFile) {
			writeResponseToFile(endPoint, response.getBody().asString());
		}
		return response;
	}
	public static Response httpGet(String endPoint, String key, String value, boolean writeToFile) {
		endPoint = initializeRequestSpec(endPoint);
		endPoint = endPoint.replace("{"+key+"}", value);
		restAssuredMethods.get(endPoint);
		if (writeToFile) {
			writeResponseToFile(endPoint, response.getBody().asString());
		}
		return response;
	}
	
	public static Response httpPatch(String endPoint, String key, String value, String jsonFileName, boolean writeToFile) {
		endPoint = initializeRequestSpec(endPoint);
		endPoint = endPoint.replace("{"+key+"}", value);
		restAssuredMethods.patch(endPoint, jsonFileName);
		if (writeToFile) {
			writeResponseToFile(endPoint, response.getBody().asString());
		}
		return response;
	}

	public static Response httpPost(String endPoint, String requestJSONFile, boolean writeToFile) {
		endPoint = initializeRequestSpec(endPoint);
		restAssuredMethods.post(endPoint, requestJSONFile);
		if (writeToFile) {
			writeResponseToFile(endPoint, response.getBody().asString());
		}
		return response;
	}

	public static Response httpPost(String endPoint, String requestJSONFile, Map<String, Object> requestParam, boolean writeToFile) {
		endPoint = initializeRequestSpec(endPoint);
		restAssuredMethods.post(endPoint, requestJSONFile, requestParam);
		if (writeToFile) {
			writeResponseToFile(endPoint, response.getBody().asString());
		}
		return response;
	}

	public static List<Object> getValueFromJSON(String jPath) {
		return restAssuredValidation.getValueFromJSONObject(jPath);
	}

	public static Object getLastValueFromJSON(String jPath) {
		return restAssuredValidation.getLastValueFromJSONObject(jPath);
	}

	public static Object getFirstValueFromJSON(String jPath) {
		return restAssuredValidation.getFirstValueFromJSONObject(jPath);
	}

	public static void verifyResponseEqualsTo(String expected) {
		restAssuredValidation.verifyResponseEqualsTo(expected);
	}

	public static void verifyResponseFileEqualsTo(String fileName) {
		restAssuredValidation.verifyResponseFileEqualsTo(fileName);
	}

	public static void verifyResponseFileEqualsTo(String fileName, String jPath) {
		restAssuredValidation.verifyResponseFileEqualsTo(fileName, jPath);
	}

	public static void verifyResponseFileEqualsTo(String fileName, String jPath, String except) {
		restAssuredValidation.verifyResponseFileEqualsTo(fileName, jPath, except);
	}

	public static void tearDown() {
		reportManager.flush();
		reportManager.close();
	}

	private static void initiateClassObjects() {
		if (configLoader == null) {
			configLoader = new JSONLoader();
			configLoader.loadConfig();
			ORLoader.loadObjectRepositories();
		}
		if (reportManager == null) {
			reportManager = new ReportManager(getReportType());
			reportManager.startReports(getReportName());
		}
		restAssuredValidation = new RestAssuredValidation(configLoader, reportManager);
		restAssuredMethods = new RestAssuredMethods(configLoader, reportManager);
	}

	protected static String getReportType() {
		String reportType = "aventstack";
		if (JSONLoader.config != null && !JSONLoader.config.isNull("suite-name")) {
			try {
				reportType = JSONLoader.config.getString("report-type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return reportType;
	}

	private static String getReportName() {
		String result = "defaultSuite";
		if (JSONLoader.config != null && !JSONLoader.config.isNull("suite-name")) {
			try {
				result = JSONLoader.config.getString("suite-name");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void endTest() {
		reportManager.endTest();
	}

	public static void setupReportAndConfigManager(ReportManager reportMgr, JSONLoader configLdr) {
		if (reportManager == null) {
			reportManager = reportMgr;
		}
		if (configLoader == null) {
			configLoader = configLdr;
		}
		restAssuredValidation = new RestAssuredValidation(configLoader, reportManager);
		restAssuredMethods = new RestAssuredMethods(configLoader, reportManager);

	}

	public static void setStepName(String stepName) {
		reportManager.setStepName(stepName.trim() + " " + currentFunction(), true);
	}

	protected static String currentFunction() {
		String functionName = Thread.currentThread().getStackTrace()[3].getMethodName();
		functionName = functionName.replace("_", " ");
		return functionName;
	}
}
