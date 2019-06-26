package com.autoexsel.data.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ORLoader {

	public static void printMissingRepositories(String locatorType) {
		if (locatorType.contains("_")) {
			locatorType = locatorType.replace("_", " ");
		}
		loadObjectRepositoriesAndPrintMissingLocators(true, locatorType.toLowerCase());
	}

	public static void loadObjectRepositories(String locatorType) {
		if (locatorType.contains("_")) {
			locatorType = locatorType.replace("_", " ");
		}
		loadObjectRepositoriesAndPrintMissingLocators(false, locatorType.toLowerCase());
	}
	public static void loadObjectRepositories() {
		loadObjectRepositoriesAndPrintMissingLocators(false, "");
	}

	public static void loadObjectRepositoriesAndPrintMissingLocators(boolean printLocators, String locatorType) {
		try {
			String orSeparator = JSONLoader.getConfigValue("or-separator");
			if (orSeparator.equals("")) {
				orSeparator = "==";
			}
			System.out.println("############### OBJECT REPOSITORIES ###############");
			// Fetch all class file name in which OR logical name is maintained.
			Class[] repoClasses = ORClassLoader.getClasses(getPackageName());
			String orPath = getORPath();
//			System.out.println("Repositories Path: " + getRoot() + "/" + orPath + "/");
			for (int i = 0; i < repoClasses.length; i++) {
				String classname = repoClasses[i].getSimpleName();
				String jsonPath = getRoot() + "/" + orPath + "/" + classname + ".json";
				try {
					File jsonFile = new File(jsonPath);
					boolean jsonExist = jsonFile.exists();
					if (!jsonExist) {
						Field[] allFields = repoClasses[i].getDeclaredFields();
						String key = "";
						for (Field field : allFields) {
							key = field.getName();
							if (field.getType().toString().equals("class java.lang.String")) {
								field.setAccessible(true);
								field.set(repoClasses[i], key + orSeparator + field.get(repoClasses[i]));
							}
						}
						if (printLocators) {
							int j = 1;
							boolean close = false;
							int fieldLen = allFields.length;
							for (Field field : allFields) {
								key = field.getName();
								if (j == 1) {
									close = true;
									System.out.println("   " + classname + ".json misssing locator(s) ......");
									System.out.println("{");
									if(fieldLen == 1) {
										System.out.println("	\"" + key + "\" : {\"" + locatorType + "\" : \"\"}");
									}else {
										System.out.println("	,\"" + key + "\" : {\"" + locatorType + "\" : \"\"}");
									}
								} else {
									System.out.println("	,\"" + key + "\" : {\"" + locatorType + "\" : \"\"}");
								}
								j++;
							}
							if(close) {
								System.out.println("}");
							}
						}

					} else {
						InputStream is = new FileInputStream(jsonPath);
						String jsonTxt = IOUtils.toString(is, "UTF-8");
						JSONObject json = new JSONObject(jsonTxt);
						Field[] allFields = repoClasses[i].getDeclaredFields();
						String key = "";
						int j = 1;
						for (Field field : allFields) {
							key = field.getName();
							if (!json.isNull(key)) {
								JSONObject objDesc = json.getJSONObject(key);
								Set<String> entries = objDesc.keySet();
								for (String type : entries) {
									try {
										if (field.getType().toString().equals("class java.lang.String")
												&& field.get(repoClasses[i]) == null) {
											field.setAccessible(true);
											field.set(repoClasses[i],
													key + orSeparator + type + orSeparator + objDesc.get(type));
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							} else {
								if (field.get(repoClasses[i]) != null) {
									field.setAccessible(true);
									field.set(repoClasses[i], key + orSeparator + field.get(repoClasses[i]));
								}
								if (j == 1) {
									System.out.println("" + classname + ".json misssing locator(s) ......");
									System.out.println("	,\"" + key + "\" : {\"" + locatorType + "\" : \"\"}");
								} else {
									System.out.println("	,\"" + key + "\" : {\"" + locatorType + "\" : \"\"}");
								}
								j++;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getRootParent() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getParent();
	}

	public static String getRoot() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getAbsolutePath();
	}

	public static String getORPath() {
		String orPath = "object-repositories";
		if (JSONLoader.config != null && !JSONLoader.config.isNull("or-path")) {
			try {
				orPath = JSONLoader.config.getString("or-path");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
//			System.out
//					.println("Either config is not loaded or 'or-path' is not defined in config.json");
		}
		return orPath;
	}

	public static String getPackageName() {
		String packageName = "not.defined";

		if (JSONLoader.config != null && !JSONLoader.config.isNull("step-definition")) {
			try {
				packageName = JSONLoader.config.getString("step-definition");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Either config is not loaded or 'step-definition' path is not defined in config.json");
		}
		return packageName;
	}
}
