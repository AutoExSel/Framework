package com.autoexsel.report.manager;

import org.openqa.selenium.WebDriver;

import com.autoexsel.extent.report.AventStackExtentReport;
import com.autoexsel.extent.report.ExtentReportManager;
import com.autoexsel.extent.report.ReportManagerInterface;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ReportManager {
	static ReportManagerInterface reportManager;

	public ReportManager(String reportType) {
		if (reportType.equalsIgnoreCase("aventstack")) {
			reportManager = new AventStackExtentReport();
			System.out.println("aventstack.extentreports is initialized successfully.");
		}else if (reportType.equalsIgnoreCase("extentreports")) {
			reportManager = new ExtentReportManager();
			System.out.println("relevantcodes.extentreports is initialized successfully.");
		} else {
			System.out.println("Report manager is not initialized.");
		}
	}

	public String startReports(String fileName) {
		return reportManager.startReports(fileName);
	}

	public void startTest(String testName) {
		reportManager.startTest(testName);
	}
	
	public void assignCatetory(String category) {
		reportManager.assigneCategory(category);
	}

	public void setStepName(String stepName) {
		reportManager.setStepName(stepName);
	}

	public void setStepName(String stepName, boolean oneTime) {
		reportManager.setStepName(stepName, oneTime);
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

	public void reportWarning(String message) {
		reportManager.reportWarning(message);
	}

	public void reportError(String message) {
		reportManager.reportError(message);
	}

	public void reportSkip(String message) {
		reportManager.reportSkip(message);
	}

	public void reportPass(String stepName, String message) {
		reportManager.reportPass(stepName, message);
	}

	public void reportFail(String stepName, String message) {
		reportManager.reportFail(stepName, message);
	}

	public void reportInfo(String stepName, String message) {
		reportManager.reportInfo(stepName, message);
	}

	public void reportWarning(String stepName, String message) {
		reportManager.reportWarning(stepName, message);
	}

	public void reportError(String stepName, String message) {
		reportManager.reportError(stepName, message);
	}

	public void reportSkip(String stepName, String message) {
		reportManager.reportSkip(stepName, message);
	}
	public void addScreenCapture(AppiumDriver<MobileElement> driver) {
		reportManager.addScreenCapture(driver);
	}
	public void takeScreenshot(AppiumDriver<MobileElement> driver, String fileName) {
		reportManager.takeScreenshot(driver, fileName);
	}
	public void addScreenCapture() {
		reportManager.addScreenCapture();
	}
	public void takeScreenshot(WebDriver driver, String fileName) {
//		reportManager.takeScreenshotAsText(driver, fileName);
		reportManager.takeScreenshot(driver, fileName);
	}
	
	public void flush() {
		reportManager.flush();
	}

	public void endTest() {
		reportManager.endTest();
	}

	public void close() {
		reportManager.close();
	}

}
