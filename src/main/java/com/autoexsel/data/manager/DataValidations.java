package com.autoexsel.data.manager;

import com.autoexsel.report.manager.ReportManager;

public class DataValidations {

	protected Object actual = null;
	protected ReportManager reportManager;
	
	public DataValidations(ReportManager reportManager) {
		this.reportManager = reportManager;
	}
	public void setActualValue(Object actual) {
		this.actual = actual;
	}

	public boolean equalsTo(Object expected) {
		boolean testResult = false;
		if (expected.getClass().toString().equals("class java.lang.Boolean")) {
			boolean expectedValue = Boolean.valueOf(expected.toString());
			boolean actualValue = Boolean.valueOf(this.actual.toString());
			testResult = booleanEqualsToLogger(actualValue, expectedValue, true);
		} else if (actual.getClass().toString().equals("class java.lang.Integer")) {
			int expectedValue = Integer.valueOf(expected.toString());
			int actualValue = Integer.valueOf(this.actual.toString());
			testResult = intEqualsToLogger(actualValue, expectedValue, true);
		} else {
			String expectedValue = expected.toString();
			String actualValue = this.actual.toString();
			testResult = equalsToLogger(actualValue, expectedValue, true);
		}
		return testResult;
	}

	public boolean contains(String expected) {
		String expectedValue = expected.toString();
		String actualValue = this.actual.toString();
		return containsToLogger(actualValue, expectedValue, true);
	}

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

	private boolean containsToLogger(String actualValue, String expected, boolean condition) {
		boolean testResult = false;
		if (actualValue.toString().trim().contains(expected.toString().trim())) {
			testResult = true;
			logSuccess(actualValue, expected, condition);
		} else {
			logUnSuccess(actualValue, expected, condition);
		}
		return testResult;
	}

	private boolean booleanEqualsToLogger(boolean actualValue, boolean expected, boolean condition) {
		boolean testResult = false;
		if (actualValue && expected) {
			testResult = true;
			logSuccess(actualValue + "", expected + "", condition);
		} else {
			logUnSuccess(actualValue + "", expected + "", condition);
		}
		return testResult;
	}

	private boolean intEqualsToLogger(int actualValue, int expected, boolean condition) {
		boolean testResult = false;
		if (actualValue == expected) {
			testResult = true;
			logSuccess(actualValue + "", expected + "", condition);
		} else {
			logUnSuccess(actualValue + "", expected + "", condition);
		}
		return testResult;
	}
	
	private void logSuccess(String actualValue, String expected, boolean condition) {
		if (condition) {
			this.reportManager.reportPass("Actual: " + actualValue + ", Expected: " + expected);
		} else {
			this.reportManager.reportFail("Actual: " + actualValue + ", Expected: " + expected);
		}
	}

	private void logUnSuccess(String actualValue, String expected, boolean condition) {
		if (!condition) {
			this.reportManager.reportPass("Actual: " + actualValue + ", Expected: " + expected);
		} else {
			this.reportManager.reportFail("Actual: " + actualValue + ", Expected: " + expected);
		}
	}
}
