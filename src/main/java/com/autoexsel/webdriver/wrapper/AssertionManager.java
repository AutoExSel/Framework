package com.autoexsel.webdriver.wrapper;

import org.openqa.selenium.WebElement;

public class AssertionManager extends WebDriverWrapperBase {
	
	public boolean isDisabled() {
		return false;
		
	}
	public boolean isDeselected() {
		return false;
		
	}
	public boolean isAllEnabled() {
		return false;
		
	}
	public boolean isAllDisabled() {
		return false;
		
	}
	public boolean isAllSelected() {
		return false;
		
	}
	public boolean isAllDisplayed() {
		return false;
		
	}
	public boolean isAllDeselected() {
		return false;
		
	}
	public boolean isDeselected(int index) {
		return false;
		
	}
	
	public boolean isDisplayed() {
		setStepNameDefault();
		boolean returnText = this.webElement.isDisplayed();
		reportManager.reportPass(
				"Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public boolean isEnabled() {
		setStepNameDefault();
		boolean returnText = this.webElement.isEnabled();
		reportManager.reportPass(
				"Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public boolean isSelected() {
		setStepNameDefault();
		boolean returnText = this.webElement.isSelected();
		reportManager.reportPass(
				"Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}
	
	public boolean verifyEqualsTo(String expected, Assert type) {
		setStepNameDefault();
		switch (type) {
		case TEXT:
			strActual = this.webElement.getText();
			break;

		case FONT_SIZE:
			strActual = this.webElement.getAttribute("font-size");
			break;
		case COLOR:
			strActual = this.webElement.getAttribute("color");
			break;
		default:
			strActual = this.webElement.getText();
			break;
		}
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, true);
		return testResult;
	}

	public boolean verifyNotEqualsTo(String expected, Assert type) {
		setStepNameDefault();
		switch (type) {
		case TEXT:
			strActual = this.webElement.getText();
			break;

		case FONT_SIZE:
			strActual = this.webElement.getAttribute("font-size");
			break;
		case COLOR:
			strActual = this.webElement.getAttribute("color");
			break;
		default:
			strActual = this.webElement.getText();
			break;
		}
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, false);
		return testResult;
	}

	public boolean verifyEqualsTo(String expected) {
		setStepNameDefault();
		strActual = this.webElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, true);
		return testResult;
	}

	public boolean verifyNotEqualsTo(String expected) {
		setStepNameDefault();
		strActual = this.webElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, false);
		return testResult;
	}

	public boolean verifyContains(String expected) {
		setStepNameDefault();
		strActual = this.webElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		reportManager.reportInfo("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = containsLogger(strActual, expected, true);
		return testResult;
	}

	public boolean verifyNotContains(String expected) {
		setStepNameDefault();
		strActual = this.webElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = containsLogger(strActual, expected, false);
		return testResult;
	}

	public void verifyAllEqualsTo(String[] expected) {
		setStepNameDefault();
		int i = 0;
		for (WebElement webElement : this.webElements) {
			strActual = webElement.getText();
			System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
			equalsToLogger(strActual, expected[i], true);
			i++;
		}
	}

	public void verifyAllNotEqualsTo(String[] expected) {
		setStepNameDefault();
		int i = 0;
		for (WebElement webElement : this.webElements) {
			strActual = webElement.getText();
			System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
			equalsToLogger(strActual, expected[i], false);
			i++;
		}
	}

	public void verifyAllContains(String[] expected) {
		setStepNameDefault();
		for (int i = 0; i < genericWebElements.size(); i++) {
			genericWebElements.get(i).verifyContains(expected[i]);
		}
	}

	public void verifyAllNotContains(String[] expected) {
		setStepNameDefault();
		for (int i = 0; i < genericWebElements.size(); i++) {
			genericWebElements.get(i).verifyNotContains(expected[i]);
		}
	}

	private boolean equalsToLogger(String actualValue, String expected, boolean condition) {
		boolean testResult = false;
		if (strActual.equals(expected)) {
			testResult = true;
			logSuccess(strActual, expected, condition);
		} else {
			logUnSuccess(strActual, expected, condition);
		}
		return testResult;
	}

	private boolean containsLogger(String actualValue, String expected, boolean condition) {
		boolean testResult = false;
		if (strActual.toString().contains(expected.toString())) {
			testResult = true;
			logSuccess(strActual, expected, condition);
		} else {
			logUnSuccess(strActual, expected, condition);
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

	private void setStepNameDefault() {
		reportManager.addScreenCapture();
		int noOfElements = this.webElements.size();
		if (noOfElements == 0) {
			System.out.println("Locator " + alias + separator + locator + " is not found.");
			reportManager.reportFail("Locator " + alias + separator + locator + " is not found.");
		} else if (noOfElements > 1) {
			for (int i = 0; i < noOfElements; i++) {
				if (this.webElements.get(i).isDisplayed()) {
					this.webElement = this.webElements.get(i);
					System.out.println(noOfElements + " elements found for " + locator + ", element " + (i + 1)
							+ " is displayed.");
					break;
				}
			}
		}
	}

}
