package com.autoexsel.services.wrapper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.report.manager.ReportManager;

public class RestAssuredValidation extends RestAssuredBase {
	private String jsonKey = "";

	public RestAssuredValidation(JSONLoader configLdr, ReportManager reportMgr) {
		configLoader = configLdr;
		reportManager = reportMgr;
	}

	public boolean verifyResponseEqualsTo(String expected) {
		strActual = response.getBody().asString();
		System.out.println("Retrieved respose body:");
		System.out.println(strActual);
		return equalsToLogger(strActual, expected, true);
	}

	public void verifyResponseFileEqualsTo(String jsonFileName) {
		if (jsonFileName.endsWith(".json")) {
			jsonFileName = getRoot() + "/data/api-testdata/" + jsonFileName;
		} else {
			jsonFileName = getRoot() + "/data/api-testdata/" + jsonFileName + ".json";
		}

		strActual = response.getBody().asString();
		JSONObject js1 = new JSONObject(strActual);
		JSONObject js2 = JSONLoader.convertJSONFileToObject(jsonFileName);
		boolean isJSONEqual = verifyJSONEqualTo(js1, js2);
		if (!isJSONEqual) {
			reportManager.reportFail("Actual and expected JSON does not match");
		}
	}

	public void verifyResponseFileEqualsTo(String jsonFileName, String jPath) {
		if (jsonFileName.endsWith(".json")) {
			jsonFileName = getRoot() + "/data/api-testdata/" + jsonFileName;
		} else {
			jsonFileName = getRoot() + "/data/api-testdata/" + jsonFileName + ".json";
		}
		strActual = response.getBody().asString();
		JSONObject jsonObj = new JSONObject(strActual);
		Object js1 = new JSONArray(configLoader.getValueFromJSONObject(jsonObj, jPath).get(0).toString());
		Object js2 = JSONLoader.convertJSONFileToArray(jsonFileName);

		boolean isJSONEqual = verifyJSONEqualTo(js1, js2);
		if (!isJSONEqual) {
			reportManager.reportFail("Actual and expected JSON does not match");
		}
	}

	public void verifyResponseFileEqualsTo(String jsonFileName, String jPath, String except) {
		Object js1 = null, js2 = null;
		if (jsonFileName.endsWith(".json")) {
			jsonFileName = getRoot() + "/data/api-testdata/" + jsonFileName;
		} else {
			jsonFileName = getRoot() + "/data/api-testdata/" + jsonFileName + ".json";
		}
		strActual = response.getBody().asString();
		JSONObject jsonObj = new JSONObject(strActual);
		Object actual = configLoader.getValueFromJSONObject(jsonObj, jPath).get(0);
		System.out.println("actual: "+actual);
		if(actual.toString().contains("null")) {
			System.out.println("!!!!!!!!!! Response body is null, hence can't verify the expected results. !!!!!!!!!!");
			return;
		}
		if (actual instanceof JSONObject) {
			
		}else if (actual instanceof JSONArray) {
			js1 = new JSONArray(actual.toString());
			js2 = (Object) JSONLoader.convertJSONFileToArray(jsonFileName);
		}
		boolean isJSONEqual = verifyJSONEqualTo(js1, js2, except);
		if (!isJSONEqual) {
			reportManager.reportFail(
					"Actual " + jsonFileName + " and expected " + responseFileLocation + " JSON does not match.");
		}
	}

	public List<Object> getValueFromJSONObject(String jPath) {
		strActual = response.getBody().asString();
		JSONObject jsonObj = new JSONObject(strActual);
		return configLoader.getValueFromJSONObject(jsonObj, jPath);
	}

	public Object getLastValueFromJSONObject(String jPath) {
		strActual = response.getBody().asString();
		JSONObject jsonObj = new JSONObject(strActual);
		return configLoader.getLastValueFromJSONObject(jsonObj, jPath);
	}

	public Object getFirstValueFromJSONObject(String jPath) {
		strActual = response.getBody().asString();
		JSONObject jsonObj = new JSONObject(strActual);
		return configLoader.getFirstValueFromJSONObject(jsonObj, jPath);
	}

	public boolean verifyResponseContains(String expected) {
		strActual = response.getBody().asString();
		System.out.println("Retrieved respose body:");
		System.out.println(strActual);
		return containsLogger(strActual, expected, true);
	}

	private boolean verifyJSONEqualTo(Object obj1, Object obj2) throws JSONException {
		boolean result = true;
		if (!obj1.getClass().equals(obj2.getClass())) {
			result = false;
		}
		if (obj1 instanceof JSONObject) {
			JSONObject jsonObj1 = (JSONObject) obj1;
			JSONObject jsonObj2 = (JSONObject) obj2;
			String[] names = JSONObject.getNames(jsonObj1);
			String[] names2 = JSONObject.getNames(jsonObj2);
			if (names.length != names2.length) {
				System.out.println("JSON length for both input does not match.");
				result = false;
			}
			for (String fieldName : names) {
				jsonKey = fieldName;
				Object obj1FieldValue = jsonObj1.get(fieldName);
				Object obj2FieldValue = jsonObj2.get(fieldName);
				if (!verifyJSONEqualTo(obj1FieldValue, obj2FieldValue)) {
					result = false;
				}
			}
		} else if (obj1 instanceof JSONArray) {
			JSONArray obj1Array = (JSONArray) obj1;
			JSONArray obj2Array = (JSONArray) obj2;
			int indexMax = obj1Array.length();
			if (obj1Array.length() != obj2Array.length()) {
				result = false;
				if (obj1Array.length() < obj2Array.length()) {
					indexMax = obj2Array.length();
				}
			}
			for (int i = 0; i < indexMax; i++) {
				if (!verifyJSONEqualTo(obj1Array.get(i), obj2Array.get(i))) {
					result = false;
				}
			}
		} else {
			if (!obj1.equals(obj2)) {
				System.out.println("False: " + jsonKey + ": " + obj1 + ", " + jsonKey + ": " + obj2);
				result = false;
			} else {
				System.out.println("True: " + jsonKey + ": " + obj1 + ", " + jsonKey + ": " + obj2);
			}
			equalsToLogger(jsonKey + ": " + obj1, jsonKey + ": " + obj2, true);
		}
		return result;
	}

	private boolean verifyJSONEqualTo(Object obj1, Object obj2, String except) throws JSONException {
		boolean result = true;
		if (!obj1.getClass().equals(obj2.getClass())) {
			result = false;
		}
		if (obj1 instanceof JSONObject) {
			JSONObject jsonObj1 = (JSONObject) obj1;
			JSONObject jsonObj2 = (JSONObject) obj2;
			String[] names = JSONObject.getNames(jsonObj1);
			String[] names2 = JSONObject.getNames(jsonObj2);
			if (names.length != names2.length) {
				System.out.println("JSON Object length does not match. Actual length: " + names.length
						+ ", expected length: " + names2.length);
				reportManager.reportFail("JSON Object length does not match. Actual length: " + names.length
						+ ", expected length: " + names2.length);
				result = false;
			}
			for (String fieldName : names) {
				jsonKey = fieldName;
				Object obj1FieldValue = jsonObj1.get(fieldName);
				Object obj2FieldValue = jsonObj2.get(fieldName);
				if (!verifyJSONEqualTo(obj1FieldValue, obj2FieldValue, except)) {
					result = false;
				}
			}
		} else if (obj1 instanceof JSONArray) {
			JSONArray obj1Array = (JSONArray) obj1;
			JSONArray obj2Array = (JSONArray) obj2;
			int indexMax = obj1Array.length();
			if (obj1Array.length() != obj2Array.length()) {
				System.out.println("JSON Array length does not match. Actual length: " + obj1Array.length()
						+ ", expected length: " + obj2Array.length());
				reportManager.reportFail("JSON Array length does not match. Actual length: " + obj1Array.length()
						+ ", expected length: " + obj2Array.length());
				result = false;
				if (obj2Array.length() < obj1Array.length()) {
					indexMax = obj2Array.length();
				}
			}
			for (int i = 0; i < indexMax; i++) {
				if (!verifyJSONEqualTo(obj1Array.get(i), obj2Array.get(i), except)) {
					result = false;
				}
			}
		} else if (!jsonKey.equalsIgnoreCase(except)) {
			if (!obj1.equals(obj2)) {
				System.out.println("False: " + jsonKey + ": " + obj1 + ", " + jsonKey + ": " + obj2);
				result = false;
			} else {
				System.out.println("True: " + jsonKey + ": " + obj1 + ", " + jsonKey + ": " + obj2);
			}
			equalsToLogger(jsonKey + ": " + obj1, jsonKey + ": " + obj2, true);
		}
		return result;
	}

//
//	// data/questions[2]/answerOptions[1]/id
//	public List<Object> getValueFromJSONObject(String jPath) {
//		List<Object> jsonValues = new ArrayList<Object>();
//		String[] nestedPath = jPath.split("/");
//		Object obj1 = getValueFromJSON(jPath);
//		if (obj1 instanceof JSONObject) {
//			JSONObject jsonObj1 = (JSONObject) obj1;
////			System.out.println("JSON Object: "+jsonObj1);
//			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
//		} else if (obj1 instanceof JSONArray) {
//			JSONArray obj1Array = (JSONArray) obj1;
////			System.out.println("JSON Array: "+obj1Array);
//			for (int i = 0; i < obj1Array.length(); i++) {
//				JSONObject jsonObj1 = (JSONObject) obj1Array.get(i);
//				jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
//			}
//		}
//		return jsonValues;
//	}
//
//	// data/questions[2]/answerOptions[1]/id
//	public Object getFirstValueFromJSONObject(String jPath) {
//		ArrayList<Object> jsonValues = new ArrayList<Object>();
//		String[] nestedPath = jPath.split("/");
//		Object obj1 = getValueFromJSON(jPath);
//		if (obj1 instanceof JSONObject) {
//			JSONObject jsonObj1 = (JSONObject) obj1;
//			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
//		} else if (obj1 instanceof JSONArray) {
//			JSONArray obj1Array = (JSONArray) obj1;
//			JSONObject jsonObj1 = (JSONObject) obj1Array.get(0);
//			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
//		}
//		return jsonValues.get(0);
//	}
//
//	// data/questions[2]/answerOptions[1]/id
//	public Object getLastValueFromJSONObject(String jPath) {
//		ArrayList<Object> jsonValues = new ArrayList<Object>();
//		String[] nestedPath = jPath.split("/");
//		Object obj1 = getValueFromJSON(jPath);
//		if (obj1 instanceof JSONObject) {
//			JSONObject jsonObj1 = (JSONObject) obj1;
//			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
//		} else if (obj1 instanceof JSONArray) {
//			JSONArray obj1Array = (JSONArray) obj1;
//			JSONObject jsonObj1 = (JSONObject) obj1Array.get(obj1Array.length() - 1);
//			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
//		}
//		return jsonValues.get(jsonValues.size() - 1);
//	}
//
//	private Object getValueFromJSON(String jPath) {
//		String[] nestedPath = jPath.split("/");
//		strActual = response.getBody().asString();
//		Object obj1 = new JSONObject(strActual);
//		String jsonKey = "";
//		Object obj1FieldValue = null;
//		for (int i = 0; i < nestedPath.length - 1; i++) {
//			int index = 0;
//			boolean indexExists = false;
//			jsonKey = nestedPath[i];
////			System.out.println("jsonKey: "+jsonKey);
//			if (jsonKey.contains("[") && jsonKey.contains("]")) {
//				indexExists = true;
//				index = Integer.parseInt(jsonKey.toString().substring(jsonKey.indexOf("[")+1, jsonKey.indexOf("]"))) - 1;
//				jsonKey = jsonKey.substring(0, jsonKey.indexOf("["));
//			}
//			JSONObject jsonObj1 = (JSONObject) obj1;
//			obj1FieldValue = jsonObj1.get(jsonKey);
//			if (obj1FieldValue instanceof JSONObject) {
//				obj1 = obj1FieldValue;
//			} else if (obj1FieldValue instanceof JSONArray) {
//				JSONArray obj1Array = (JSONArray) obj1FieldValue;
//				if((i < nestedPath.length - 2) || indexExists) {
//					obj1 = obj1Array.get(index);
//				}else {
//					obj1 = obj1Array;
//				}
//			}
//		}
//		return obj1;
//	}
	
	private boolean equalsToLogger(String actualValue, String expected, boolean condition) {
		boolean testResult = false;
		if (actualValue.toString().trim().equals(expected.toString().trim())) {
			testResult = true;
			logSuccess(actualValue, expected, condition);
		} else {
			logUnSuccess(actualValue, expected, condition);
		}
		return testResult;
	}

	private boolean containsLogger(String actualValue, String expected, boolean condition) {
		boolean testResult = false;
		if (actualValue.toString().trim().contains(expected.toString().trim())) {
			testResult = true;
			logSuccess(actualValue, expected, condition);
		} else {
			logUnSuccess(actualValue, expected, condition);
		}
		return testResult;
	}

	private void logSuccess(String actualValue, String expected, boolean condition) {
		if (condition) {
			reportManager.reportPass("Actual: " + actualValue + ", Expected: " + expected);
		} else {
			reportManager.reportFail("Actual: " + actualValue + ", Expected: " + expected);
		}
	}

	private void logUnSuccess(String actualValue, String expected, boolean condition) {
		if (!condition) {
			reportManager.reportPass("Actual: " + actualValue + ", Expected: " + expected);
		} else {
			reportManager.reportFail("Actual: " + actualValue + ", Expected: " + expected);
		}
	}

}
