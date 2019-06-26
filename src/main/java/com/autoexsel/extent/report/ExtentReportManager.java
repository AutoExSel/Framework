package com.autoexsel.extent.report;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ExtentReportManager implements ReportManagerInterface{

	protected ExtentReports reports = null;
	protected ExtentTest test = null;
	protected boolean oneTime = true;
	protected String stepName = "";
	
	public String startReports(String location, String fileName) {
		reports = new ExtentReports(location+"/"+fileName+".html", true);
		return location+"/"+fileName+".html";
	}
	public String startReports(String fileName) {
		File resultDir = new File(getRoot() + "/results/html");
		if (!resultDir.exists()) {
			resultDir.mkdirs();
		}
		String resultLocation = resultDir.getAbsolutePath() + "/" + fileName + ".html";
		System.out.println("Report Location: "+resultLocation);
		reports = new ExtentReports(resultLocation, true);
		return resultLocation;
	}
	
	public void startTest(String testName) {
		test = reports.startTest(testName);
		this.stepName = "Pre-Requisite";
	}

	public void setStepName(String stepName) {
//		System.out.println("Step Name: "+ stepName);
		if(this.oneTime == false) {
			this.stepName = stepName;
		}
	}
	public void setStepName(String stepName, boolean oneTime) {
		this.stepName = stepName;
		this.oneTime = oneTime;
	}
	public void reportPass(String message) {
		test.log(LogStatus.PASS, getStepName(stepName), message);
	}

	public void reportFail(String message) {
		test.log(LogStatus.FAIL, getStepName(stepName), message);
	}

	public void reportInfo(String message) {
		test.log(LogStatus.INFO, getStepName(stepName), message);
	}

	public void reportWarning(String message) {
		test.log(LogStatus.WARNING, getStepName(stepName), message);
	}

	public void reportError(String message) {
		test.log(LogStatus.ERROR, getStepName(stepName), message);
	}

	public void reportSkip(String message) {
		test.log(LogStatus.SKIP, getStepName(stepName), message);
	}
	public void reportPass(String stepName, String message) {
		if(test != null)
			test.log(LogStatus.PASS, getStepName(stepName), message);
	}

	public void reportFail(String stepName, String message) {
		test.log(LogStatus.FAIL, getStepName(stepName), message);
	}

	public void reportInfo(String stepName, String message) {
		test.log(LogStatus.INFO, getStepName(stepName), message);
	}

	public void reportWarning(String stepName, String message) {
		test.log(LogStatus.WARNING, getStepName(stepName), message);
	}

	public void reportError(String stepName, String message) {
		test.log(LogStatus.ERROR, getStepName(stepName), message);
	}

	public void reportSkip(String stepName, String message) {
		test.log(LogStatus.SKIP, getStepName(stepName), message);
	}
	
	private String getStepName(String stepName) {
		if(this.stepName != stepName) {
			return this.stepName+"."+stepName;
		}else {
			return this.stepName;
		}
	}

	public void endTest() {
		if(test != null)
			reports.endTest(test);
	}
	
	public void flush() {
		if(reports!= null) {
			reports.flush();
		}
	}
	
	public void close() {
		if(reports.getReportId() == null) {
			reports.close();
		}
	}
	public String getRoot() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getAbsolutePath();
	}
	public void assigneCategory(String category) {
		// TODO Auto-generated method stub
		
	}
	public void addScreenCapture(Object driver) {
		// TODO Auto-generated method stub
		
	}
	public void takeScreenshot(Object driver, String fileName) {
		// TODO Auto-generated method stub
		
	}
	public String takeScreenshotAsText(Object driver, String fileName) {
		return fileName;
		// TODO Auto-generated method stub
		
	}
	public void addScreenCapture() {
		// TODO Auto-generated method stub
		
	}
}
