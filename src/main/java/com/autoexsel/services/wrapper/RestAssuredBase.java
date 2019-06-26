package com.autoexsel.services.wrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.report.manager.ReportManager;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredBase {
	protected static RestAssuredValidation restAssuredValidation = null;
	protected static RestAssuredMethods restAssuredMethods = null;
	protected static ReportManager reportManager = null;
	protected static JSONLoader configLoader = null;
	protected static String responseFileLocation = null;
	protected static String authorization = null;
	protected static String baseURI = null;
	protected static String suite = "sprint2";
	protected static String env = "qa";
	protected String strActual;
	static RequestSpecification request;
	static Response response;
	

	public static void setBaseURI(String serviceURL) {
		RestAssured.baseURI = serviceURL;
	}

	protected static String initializeRequestSpec(String serviceURL) {
		if (!serviceURL.startsWith("http") && JSONLoader.config != null) {
			try {
//				baseURI = JSONLoader.config.getString("base-uri");
				baseURI = JSONLoader.getConfigValue("env/"+env+"/base-uri");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			request = new RequestSpecBuilder().setBaseUri(baseURI).build();
		} else if (serviceURL.startsWith("http")) {
			request = new RequestSpecBuilder().setBaseUri(serviceURL).build();
		}
		if (JSONLoader.config != null) {
			try {
//				authorization = JSONLoader.config.getString("authorization");
				authorization = JSONLoader.getConfigValue("env/"+env+"/authorization");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			request.header("Authorization", authorization);
		}
		if (JSONLoader.config != null && !JSONLoader.config.isNull(serviceURL)) {
			serviceURL = JSONLoader.config.getString(serviceURL);
		}
		return serviceURL;
	}

	protected static String buildServiceURL(String serviceURL) {
		String value = "";
		String[] stringArr = serviceURL.split("{");
		for (int i = 0; i < stringArr.length; i++) {
			if (stringArr[i].contains("}")) {
				String key = stringArr[i].substring(0, stringArr[i].indexOf("}"));
				stringArr[i] = stringArr[i].replace(key + "}", value);
			}
		}
		serviceURL = String.join("", stringArr);
		System.out.println("serviceURL: " + serviceURL);
		return serviceURL;
	}

	public static void authenticate(String url, String username, String password) {
		setBaseURI(url);
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName(username);
		authScheme.setPassword(password);
		RestAssured.authentication = authScheme;
	}

	public static void premitiveAuthentication(String username, String password) {
		RestAssured.preemptive().basic(username, password);
	}

	public JSONObject getJSONFileToObject(String jsonFile) {
		JSONObject jsonObject = new JSONObject();
		if (!jsonFile.endsWith(".json")) {
			System.out.println("!!!!!!! Warning: Input data must be in form of JSON file.");
		} else {

		}
		return jsonObject;
	}

	public static void writeResponseToFile(String serviceURL, String jsonString) {
		try {
			File directory = new File(getRoot() + "/data/response/");
			if (!directory.exists()) {
				directory.mkdir();
			}
			int index = serviceURL.split("/").length;
			File file = new File(getRoot() + "/data/response/" +serviceURL.split("/")[index-1] +"-"+(new Date()).getTime() + ".json");
			responseFileLocation = file.getAbsolutePath();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(jsonString);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void setStepNameDefault() {
		String stepName = Thread.currentThread().getStackTrace()[4].getMethodName();
		reportManager.setStepName(stepName);
	}

	protected static String getRoot() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getAbsolutePath();
	}
}
