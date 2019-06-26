package com.autoexsel.mobile.wrapper;

import com.autoexsel.mobile.driver.AppiumMobileDriver;
import com.autoexsel.report.manager.ReportManager;

public class AppiumWrapperBase {
	protected static AppiumMobileDriver appiumMobileDriver;
	protected static ReportManager reportManager;
	protected String locator;
	protected String alias;
	protected String separator;
	protected String strActual;
	protected double dblActual;
	protected float floatActual;
	protected int noOfElements;
	protected int intActual;

	public static enum Assert {
		TEXT, COLOR, FONT_SIZE
	};

	protected void logSuccess(String actualValue, String expected, boolean condition) {
		if (condition) {
			reportManager.reportPass("Actual: " + actualValue + ", Expected: " + expected);
		} else {
			reportManager.reportFail("Actual: " + actualValue + ", Expected: " + expected);
		}
	}

	protected void logUnSuccess(String actualValue, String expected, boolean condition) {
		if (!condition) {
			reportManager.reportPass("Actual: " + actualValue + ", Expected: " + expected);
		} else {
			reportManager.reportFail("Actual: " + actualValue + ", Expected: " + expected);
		}
	}

}
