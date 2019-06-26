package com.autoexsel.data.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONLoader {
	public static JSONObject config = null;

	public JSONObject loadConfig() {
		String jsonPath = getRoot() + "/config.json";
		config = convertJSONFileToObject(jsonPath);
		return config;
	}

	public static JSONObject convertJSONFileToObject(String jsonPath) {
		File f = new File(jsonPath);
		JSONObject jsonObj = null;
		if (f.exists()) {
			InputStream is = null;
			try {
				is = new FileInputStream(jsonPath);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			String jsonTxt = null;
			try {
				jsonTxt = IOUtils.toString(is, "UTF-8");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				jsonObj = new JSONObject(jsonTxt);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("Config Location: "+jsonPath);
		} else {
			System.out.println("Config Location: "+jsonPath + " does not exist.");
		}
		return jsonObj;
	}

	public static JSONArray convertJSONFileToArray(String jsonPath) {
		System.out.println("JSON Location: " + jsonPath);
		JSONArray jsonArrayObj = null;
		File f = new File(jsonPath);
		if (f.exists()) {
			InputStream is = null;
			try {
				is = new FileInputStream(jsonPath);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			String jsonTxt = null;
			try {
				jsonTxt = IOUtils.toString(is, "UTF-8");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				jsonArrayObj = new JSONArray(jsonTxt);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(jsonPath + " does not exist.");
		}
		return jsonArrayObj;
	}

	public static String getConfigValue(String key) {
		return getConfigValue(config, key);
	}

	public static String getConfigValue(JSONObject jsonObj, String jPath) {
		if(jsonObj == null) {
			return "";
		}
		String[] nestedPath = jPath.split("/");
		Object obj1 = jsonObj;
		String jsonKey = "";
		Object obj1FieldValue = null;
		for (int i = 0; i < nestedPath.length; i++) {
			int index = 0;
			boolean indexExists = false;
			jsonKey = nestedPath[i];
			if (jsonKey.contains("[") && jsonKey.contains("]")) {
				indexExists = true;
				index = Integer.parseInt(jsonKey.toString().substring(jsonKey.indexOf("[") + 1, jsonKey.indexOf("]")))
						- 1;
				jsonKey = jsonKey.substring(0, jsonKey.indexOf("["));
			}
			JSONObject jsonObj1 = (JSONObject) obj1;
			if (!jsonObj1.isNull(jsonKey)) {
				obj1FieldValue = jsonObj1.get(jsonKey);
			} else {
				obj1FieldValue = "";
			}
			if (obj1FieldValue instanceof JSONObject) {
				obj1 = obj1FieldValue;
			} else if (obj1FieldValue instanceof JSONArray) {
				JSONArray obj1Array = (JSONArray) obj1FieldValue;
				if ((i < nestedPath.length - 2) || indexExists) {
					obj1 = obj1Array.get(index);
				} else {
					obj1 = obj1Array;
				}
			}
		}
		return obj1FieldValue.toString();
	}

	public static Object getChildNodeFromJSON(JSONObject jsonObj, String jPath) {
		String[] nestedPath = jPath.split("/");
		Object obj1 = jsonObj;
		Object obj1FieldValue = null;
		for (int i = 0; i < nestedPath.length; i++) {
			int index = 0;
			boolean indexExists = false;
			String jsonKey = nestedPath[i];
			if (jsonKey.contains("[") && jsonKey.contains("]")) {
				indexExists = true;
				index = Integer.parseInt(jsonKey.toString().substring(jsonKey.indexOf("[") + 1, jsonKey.indexOf("]")))
						- 1;
				jsonKey = jsonKey.substring(0, jsonKey.indexOf("["));
			}
			JSONObject jsonObj1 = (JSONObject) obj1;
			obj1FieldValue = jsonObj1.get(jsonKey);
			if (obj1FieldValue instanceof JSONObject) {
				obj1 = obj1FieldValue;
			} else if (obj1FieldValue instanceof JSONArray) {
				JSONArray obj1Array = (JSONArray) obj1FieldValue;
				if ((i < nestedPath.length - 2) || indexExists) {
					obj1 = obj1Array.get(index);
				} else {
					obj1 = obj1Array;
				}
			}
		}
		return obj1;
	}

	private String getRoot() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getAbsolutePath();
	}

	// data/questions[2]/answerOptions[1]/id
	public List<Object> getValueFromJSONObject(JSONObject jsonObj, String jPath) {
		List<Object> jsonValues = new ArrayList<Object>();
		String[] nestedPath = jPath.split("/");
		Object obj1 = getValueFromJSON(jsonObj, jPath);
		if (obj1 instanceof JSONObject) {
			JSONObject jsonObj1 = (JSONObject) obj1;
//			System.out.println("JSON Object: "+jsonObj1);
			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
		} else if (obj1 instanceof JSONArray) {
			JSONArray obj1Array = (JSONArray) obj1;
//			System.out.println("JSON Array: "+obj1Array);
			for (int i = 0; i < obj1Array.length(); i++) {
				JSONObject jsonObj1 = (JSONObject) obj1Array.get(i);
				jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
			}
		}
		return jsonValues;
	}

	// data/date
	public JSONObject setValueInJSONObject_Old(Object jsonObj, String jPath, String jsonValue) {
		Object obj1 = null;
		String[] nestedPath = jPath.split("/");
		for (int i = 0; i < nestedPath.length - 1; i++) {
			obj1 = ((JSONObject)jsonObj).get(nestedPath[i]);
			if (obj1 instanceof JSONObject) {
//				((JSONObject)jsonObj).getJSONObject(nestedPath[i]);
				jsonObj = ((JSONObject)jsonObj).getJSONObject(nestedPath[i]);
			} else if (obj1 instanceof JSONArray) {
//				((JSONObject)jsonObj).getJSONArray(nestedPath[i]);
				jsonObj = ((JSONObject)jsonObj).getJSONArray(nestedPath[i]);
			}
		}
		JSONArray obj1Array = (JSONArray) obj1;
		String[] jsonValueArr = jsonValue.split("/");
		for (int j = 0; j < obj1Array.length(); j++) {
			JSONObject jsonObj1 = (JSONObject) obj1Array.get(j);
			jsonObj1.put(nestedPath[nestedPath.length - 1], jsonValueArr[j]);
		}
		return null;
	}

	// data/questions[2]/answerOptions[1]/id
	public Object getFirstValueFromJSONObject(JSONObject jsonObj, String jPath) {
		ArrayList<Object> jsonValues = new ArrayList<Object>();
		String[] nestedPath = jPath.split("/");
		Object obj1 = getValueFromJSON(jsonObj, jPath);
		if (obj1 instanceof JSONObject) {
			JSONObject jsonObj1 = (JSONObject) obj1;
			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
		} else if (obj1 instanceof JSONArray) {
			JSONArray obj1Array = (JSONArray) obj1;
			JSONObject jsonObj1 = (JSONObject) obj1Array.get(0);
			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
		}
		return jsonValues.get(0);
	}

	// data/questions[2]/answerOptions[1]/id
	public Object getLastValueFromJSONObject(JSONObject jsonObj, String jPath) {
		ArrayList<Object> jsonValues = new ArrayList<Object>();
		String[] nestedPath = jPath.split("/");
		Object obj1 = getValueFromJSON(jsonObj, jPath);
		if (obj1 instanceof JSONObject) {
			JSONObject jsonObj1 = (JSONObject) obj1;
			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
		} else if (obj1 instanceof JSONArray) {
			JSONArray obj1Array = (JSONArray) obj1;
			JSONObject jsonObj1 = (JSONObject) obj1Array.get(obj1Array.length() - 1);
			jsonValues.add(jsonObj1.get(nestedPath[nestedPath.length - 1]));
		}
		return jsonValues.get(jsonValues.size() - 1);
	}

	public Object getValueFromJSON(JSONObject jsonObj, String jPath) {
		String jsonKey = "";
		Object obj1 = jsonObj;
		Object obj1FieldValue = null;
		String[] nestedPath = jPath.split("/");
		for (int i = 0; i < nestedPath.length - 1; i++) {
			int index = 0;
			boolean indexExists = false;
			jsonKey = nestedPath[i];
			if (jsonKey.contains("[") && jsonKey.contains("]")) {
				indexExists = true;
				index = Integer.parseInt(jsonKey.toString().substring(jsonKey.indexOf("[") + 1, jsonKey.indexOf("]")))
						- 1;
				jsonKey = jsonKey.substring(0, jsonKey.indexOf("["));
			}
			JSONObject jsonObj1 = (JSONObject) obj1;
			obj1FieldValue = jsonObj1.get(jsonKey);
			if (obj1FieldValue instanceof JSONObject) {
				obj1 = obj1FieldValue;
			} else if (obj1FieldValue instanceof JSONArray) {
				JSONArray obj1Array = (JSONArray) obj1FieldValue;
				if ((i < nestedPath.length - 2) || indexExists) {
					obj1 = obj1Array.get(index);
				} else {
					obj1 = obj1Array;
				}
			}
		}
		return obj1;
	}
	public static void main(String[] args) {
		JSONObject jsonObj = new JSONObject("{\r\n" + 
				"	\"applicationId\": 1,\r\n" + 
				"	\"termsAndConditionsId\": 1,\r\n" + 
				"	\"consent\": false,\r\n" + 
				"	\"date\": {\r\n" + 
				"		\"id\": [\r\n" + 
				"			{\r\n" + 
				"				\"name\": \"Ashok\"\r\n" + 
				"			},\r\n" + 
				"			{\r\n" + 
				"				\"country\": \"India\"\r\n" + 
				"			}\r\n" + 
				"		]\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"");
		setValueInJSONObject_update(jsonObj, "data/id/name", "name");
	}
	// data/date
	public static JSONObject setValueInJSONObject_update(Object jsonObj, String jPath, String jsonValue) {
			Object obj1 = null;
			String[] nestedPath = jPath.split("/");
			for (int i = 0; i < nestedPath.length - 1; i++) {
				obj1 = ((JSONObject)jsonObj).get(nestedPath[i]);
				if (obj1 instanceof JSONObject) {
//					((JSONObject)jsonObj).getJSONObject(nestedPath[i]);
					jsonObj = ((JSONObject)jsonObj).getJSONObject(nestedPath[i]);
				} else if (obj1 instanceof JSONArray) {
//					((JSONObject)jsonObj).getJSONArray(nestedPath[i]);
					jsonObj = ((JSONObject)jsonObj).getJSONArray(nestedPath[i]);
				}
			}
			JSONArray obj1Array = (JSONArray) obj1;
			String[] jsonValueArr = jsonValue.split("/");
			for (int j = 0; j < obj1Array.length(); j++) {
				JSONObject jsonObj1 = (JSONObject) obj1Array.get(j);
				jsonObj1.put(nestedPath[nestedPath.length - 1], jsonValueArr[j]);
			}
			return null;
		}
		

	public String getReportType() {
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

	public String getReportName(String suite) {
		String result = "defaultSuite";
		if (JSONLoader.config != null && !JSONLoader.config.isNull("suite/" + suite)) {
			try {
				result = JSONLoader.getConfigValue("suite/" + suite);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
