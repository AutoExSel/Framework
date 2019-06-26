package com.autoexsel.services.wrapper;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.report.manager.ReportManager;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredMethods extends RestAssuredBase {
	JSONLoader configLoader;
	ReportManager reportManager;

	public RestAssuredMethods(JSONLoader configLoader, ReportManager reportManager) {
		this.configLoader = configLoader;
		this.reportManager = reportManager;
	}

	public Response getResponseInstance() {
		return response;
	}

	public Response get(String serviceURL, String username, String password) {
		setBaseURI(serviceURL);
		premitiveAuthentication(username, password);
		return get(serviceURL);
	}
	
	public Response post(String serviceURL, String jsonFileName, String username, String password) {
		setBaseURI(serviceURL);
		premitiveAuthentication(username, password);
		return post(serviceURL, jsonFileName);
	}

	public Response post(String serviceURL, JSONObject jsonObject, String username, String password) {
		setBaseURI(serviceURL);
		premitiveAuthentication(username, password);
		return post(serviceURL, jsonObject);
	}

	public Response post(String serviceURL, String jsonFileName) {
		JSONObject jsonObject = null;
		if(jsonFileName.startsWith("{")) {
			jsonObject = new JSONObject(jsonFileName);
		}else if (jsonFileName.endsWith(".json")) {
			jsonFileName = getRoot() + "/data/request/" + jsonFileName;
			jsonObject = JSONLoader.convertJSONFileToObject(jsonFileName);
		} else {
			jsonFileName = getRoot() + "/data/request/" + jsonFileName + ".json";
			jsonObject = JSONLoader.convertJSONFileToObject(jsonFileName);
		}
		return post(serviceURL, jsonObject);
	}
	
	public Response post(String serviceURL, String jsonFileName, Map<String, Object> requestParam) {
		if (jsonFileName.endsWith(".json")) {
			jsonFileName = getRoot() + "/data/request/" + jsonFileName;
		} else {
			jsonFileName = getRoot() + "/data/request/" + jsonFileName + ".json";
		}
		JSONObject jsonObject = JSONLoader.convertJSONFileToObject(jsonFileName);
		Set<String> keys = requestParam.keySet();
		for (String jsonKey : keys) {
			Object paramValue = requestParam.get(jsonKey);
			if (paramValue.getClass().toString().equals("class java.lang.Boolean")) {
				jsonObject.put(jsonKey, Boolean.valueOf(paramValue.toString()));
			}else if (paramValue.getClass().toString().equals("class java.lang.Integer")) {
				jsonObject.put(jsonKey, Integer.valueOf(paramValue.toString()));
			}else if (paramValue.getClass().toString().equals("class java.lang.String")) {
				jsonObject.put(jsonKey, paramValue.toString());
			}
		}
		return post(serviceURL, jsonObject);
	}
	
	public Response patch(String serviceURL, String jsonFileName) {
		JSONObject jsonObject = null;
		if(jsonFileName.startsWith("{")) {
			jsonObject = new JSONObject(jsonFileName);
			System.out.println("Request body: "+jsonObject.toString());
		}else if (jsonFileName.endsWith(".json")) {
			jsonFileName = getRoot() + "/data/request/" + jsonFileName;
			jsonObject = JSONLoader.convertJSONFileToObject(jsonFileName);
		} else {
			jsonFileName = getRoot() + "/data/request/" + jsonFileName + ".json";
			jsonObject = JSONLoader.convertJSONFileToObject(jsonFileName);
		}
		return patch(serviceURL, jsonObject);
	}

	public Response get(String endPoint) {
		System.out.println("End point URL => " + baseURI+endPoint);
		response = RestAssured.given().spec(request).get(endPoint);
		System.out.println("Status code =>  " + response.getStatusCode());
		System.out.println("Response Body => " + response.getBody().asString());
		reportManager.reportPass("End point URL => " + baseURI+endPoint);
		reportManager.reportPass("Status code =>  " + response.getStatusCode());
		reportManager.reportPass("Response Body => " + response.getBody().asString());
		return response;
	}
	
	public Response patch(String serviceURL, JSONObject jsonObject) {
		request.header("Content-Type", "application/json");
		request.body(jsonObject.toString());
		System.out.println("End point URL => " + baseURI+serviceURL);
		response = RestAssured.given().spec(request).patch(serviceURL);
		System.out.println("Status code =>  " + response.getStatusCode());
		System.out.println("Response Body => " + response.getBody().asString());
		reportManager.reportPass("End point URL => " + baseURI+serviceURL);
		reportManager.reportPass("Status code =>  " + response.getStatusCode());
		reportManager.reportPass("Response Body => " + response.getBody().asString());
		return response;
	}
	
	public Response post(String serviceURL, JSONObject jsonObject) {
		request.header("Content-Type", "application/json");
		request.body(jsonObject.toString());
		System.out.println("End point URL => " + baseURI+serviceURL);
		System.out.println("Request JSON => "+jsonObject.toString());
		response = RestAssured.given().spec(request).post(serviceURL);
		System.out.println("Status code =>  " + response.getStatusCode());
		System.out.println("Response Body => " + response.getBody().asString());
		reportManager.reportPass("End point URL => " + baseURI+serviceURL);
		reportManager.reportPass("Status code =>  " + response.getStatusCode());
		reportManager.reportPass("Response Body => " + response.getBody().asString());
		return response;
	}

}
