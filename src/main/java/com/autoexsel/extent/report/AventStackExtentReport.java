package com.autoexsel.extent.report;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AventStackExtentReport implements ReportManagerInterface {
	protected ExtentHtmlReporter htmlReporter;
	protected ExtentReports extentReports;
	protected ExtentTest parentTest;
	protected ExtentTest childTest;
	protected String sceenshot;
	protected String stepName;
	protected String preStepName = "";
	
	public String startReports(String fileName) {
		File resultDir = new File(getRoot() + "/results/html");
		if (!resultDir.exists()) {
			resultDir.mkdirs();
		}
		String resultLocation = resultDir.getAbsolutePath() + "/" + fileName + ".html";
		System.out.println("Report Location: " + resultLocation);
		htmlReporter = new ExtentHtmlReporter(resultLocation);

		htmlReporter.config().setReportName(getPageTitle(fileName));
		htmlReporter.config().setDocumentTitle(getPageTitle(fileName));
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(false);

		extentReports = new ExtentReports();
		extentReports.attachReporter(htmlReporter);

		return resultLocation;
	}

	public String getPageTitle(String fileName) {
		String[] r = (fileName.substring(0, 1).toUpperCase() + fileName.substring(1)).split("(?=\\p{Upper})");
		return String.join(" ", r);
	}

	public void startTest(String testName) {
		preStepName = "";
		parentTest = extentReports.createTest(testName);
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
		childTest = parentTest.createNode(stepName);
	}

	public void setStepName(String stepName, boolean oneTime) {
		this.stepName = stepName;
		childTest = parentTest.createNode(stepName);
	}

	public void reportPass(String message) {
		childTest.log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}

	public void reportFail(String message) {
		childTest.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}

	public void reportInfo(String message) {
		childTest.log(Status.INFO, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}

	public void reportWarning(String message) {
		childTest.log(Status.WARNING, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}

	public void reportError(String message) {
		childTest.log(Status.ERROR, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}

	public void reportSkip(String message) {
		childTest.log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}

	public void assigneCategory(String category) {
		parentTest.assignCategory(category);
	}

	public void addScreenCapture(Object driver) {
		try {
			File screenShots = new File(getRoot() + "/results/screenshots");
			if (!screenShots.exists()) {
				screenShots.mkdirs();
			}
			String screenPath = getRoot() + "/results/screenshots/" + sceenshot + ".png";
			if(!this.stepName.equals(preStepName)) {
				preStepName = this.stepName;
				childTest.addScreenCaptureFromPath(screenPath);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void takeScreenshot(Object driver, String fileName) {
		sceenshot = fileName;
		try {
			File screenShots = new File(getRoot() + "/results/screenshots");
			if (!screenShots.exists()) {
				screenShots.mkdirs();
			}
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File finalDestination = new File(getRoot() + "/results/screenshots/" + fileName + ".png");
			System.out.println(getRoot() + "/results/screenshots/" + fileName + ".png");
			if (finalDestination.exists()) {
				finalDestination.delete();
			}
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String takeScreenshotAsText(Object driver, String fileName) {
		sceenshot = fileName;
		File screenShots = new File(getRoot() + "/results/screenshots");
		if (!screenShots.exists()) {
			screenShots.mkdirs();
		}
		TakesScreenshot ts = (TakesScreenshot) driver;
		String source = ts.getScreenshotAs(OutputType.BASE64);
//		childTest.log(Status.INFO, source);
		childTest.log(Status.SKIP, MarkupHelper.createCodeBlock(source));
		return source;
	}
	public void addScreenCapture() {
		try {
			File screenShots = new File(getRoot() + "/results/screenshots");
			if (!screenShots.exists()) {
				screenShots.mkdirs();
			}
			String screenPath = getRoot() + "/results/screenshots/" + sceenshot + ".png";
			if(!this.stepName.equals(preStepName)) {
				preStepName = this.stepName;
				childTest.addScreenCaptureFromPath(screenPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void reportPass(String stepName, String message) {
		// TODO Auto-generated method stub

	}

	public void reportFail(String stepName, String message) {
		// TODO Auto-generated method stub

	}

	public void reportInfo(String stepName, String message) {
		// TODO Auto-generated method stub

	}

	public void reportWarning(String stepName, String message) {
		// TODO Auto-generated method stub

	}

	public void reportError(String stepName, String message) {
		// TODO Auto-generated method stub

	}

	public void reportSkip(String stepName, String message) {
		// TODO Auto-generated method stub
	}

	public void endTest() {
		// TODO Auto-generated method stub
	}
	public void close() {
		if (extentReports != null) {
			extentReports.close();
		}
	}

	public void flush() {
		if (extentReports != null)
			extentReports.flush();
	}

	public String getRoot() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getAbsolutePath();
	}


}
