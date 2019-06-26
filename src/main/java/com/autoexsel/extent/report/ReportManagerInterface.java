package com.autoexsel.extent.report;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public interface ReportManagerInterface {

	public String startReports(String fileName);

	public void startTest(String testName);

	public void assigneCategory(String category);

	public void setStepName(String stepName);

	public void setStepName(String stepName, boolean oneTime);

	public void reportPass(String message);

	public void reportFail(String message);

	public void reportInfo(String message);

	public void reportWarning(String message);

	public void reportError(String message);

	public void reportSkip(String message);

	public void reportPass(String stepName, String message);

	public void reportFail(String stepName, String message);

	public void reportInfo(String stepName, String message);

	public void reportWarning(String stepName, String message);

	public void reportError(String stepName, String message);

	public void reportSkip(String stepName, String message);

	public void addScreenCapture(Object driver);

	public void takeScreenshot(Object driver, String fileName);

	public String takeScreenshotAsText(Object driver, String fileName);

	public void addScreenCapture();

	public void flush();

	public void endTest();

	public void close();

}
