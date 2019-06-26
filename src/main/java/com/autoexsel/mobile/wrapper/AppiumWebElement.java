package com.autoexsel.mobile.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.autoexsel.mobile.driver.AppiumDriverBase;
import com.autoexsel.mobile.driver.AppiumMobileDriver;

import cucumber.api.DataTable;
import io.appium.java_client.MobileElement;

public class AppiumWebElement extends AppiumWrapperBase implements AppiumMobileElement {
	protected WebElement mobileElement;
	protected List<WebElement> mobileElements;

	public AppiumWebElement(AppiumMobileDriver appiumMobileDriver) {
		AppiumWrapperBase.appiumMobileDriver = appiumMobileDriver;
		reportManager = appiumMobileDriver.getReportManager();
	}

	public AppiumWebElement as(String as) {
		this.alias = "'" + as + "', ";
		this.separator = " ";
		return this;
	}

	public void setWebElements(By locator) {
		this.locator = locator.toString();
		System.out.println("Locator for Android Web: " + locator);
		this.mobileElements = appiumMobileDriver.getWebDriver().findElements(locator);
		if (this.mobileElements.size() == 0) {
			this.mobileElement = null;
		} else {
			this.mobileElement = this.mobileElements.get(0);
		}
	}

	public void setWebElements(MobileElement element) {
		this.locator = null;
		this.mobileElements.add(element);
		this.mobileElement = element;
	}

	public void setWebElements(List<MobileElement> elements) {
		this.locator = null;
		this.mobileElements.addAll(elements);
		if (this.mobileElements.size() == 0) {
			this.mobileElement = null;
		} else {
			this.mobileElement = this.mobileElements.get(0);
		}
	}

	public List<AppiumMobileElement> getAllWebElements() {
		ArrayList<AppiumMobileElement> genericMobileElements = new ArrayList<AppiumMobileElement>();
		for (WebElement androidElement : this.mobileElements) {
			AppiumWebElement genericMobileElement = new AppiumWebElement(appiumMobileDriver);
			genericMobileElement.mobileElements = this.mobileElements;
			genericMobileElement.mobileElement = androidElement;
			genericMobileElement.locator = locator;
			genericMobileElement.separator = separator;
			genericMobileElement.alias = AppiumDriverBase.alias;
			genericMobileElements.add(genericMobileElement);
		}
		return genericMobileElements;
	}

	public void click() {
		setStepNameDefault();
		this.mobileElement.click();
		reportManager.reportPass("click", "Clicked on " + alias + separator + this.locator);
		System.out.println("Clicked on " + alias + separator + this.locator);
	}

	public void submit() {
		setStepNameDefault();
		this.mobileElement.submit();
		reportManager.reportPass("submit", "Submitted " + alias + separator + this.locator);
		System.out.println("Submitted " + alias + separator + this.locator);
	}

	public void sendKeys(String keys) {
		setStepNameDefault();
		this.mobileElement.sendKeys(keys);
		reportManager.reportPass("sendKeys", "Entered \"" + keys + "\" in " + alias + " " + this.locator);
		System.out.println("Entered \"" + keys + "\" in " + alias + " " + this.locator);
	}

	public String getText() {
		setStepNameDefault();
		String returnText;
		if (this.mobileElements == null) {
			returnText = this.mobileElement.getText();
		} else {
			returnText = this.mobileElement.getText();
		}
		reportManager.reportPass("getText", "Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String getAttribute(String attr) {
		setStepNameDefault();
		String returnText = this.mobileElement.getAttribute(attr);
		reportManager.reportPass("getAttribute", "Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String getCssValue(String attr) {
		setStepNameDefault();
		String returnText = mobileElement.getCssValue(attr);
		reportManager.reportPass("getCssValue", "Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String getTagName() {
		setStepNameDefault();
		String returnText = mobileElement.getTagName();
		reportManager.reportPass("getTagName", "Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public boolean isDisplayed() {
		setStepNameDefault();
		reportManager.addScreenCapture(appiumMobileDriver.getMobileDriver());
		boolean returnText = false;
		try {
			returnText = this.mobileElement.isDisplayed();
		} catch (Exception e) {
		}
		String logMessage = "Verified isDisplayed \"" + returnText + "\" from " + alias + " " + this.locator;
		if (returnText) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return returnText;
	}

	public boolean isEnabled() {
		setStepNameDefault();
		boolean returnText = mobileElement.isEnabled();
		reportManager.reportPass("isEnabled",
				"Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public boolean isSelected() {
		setStepNameDefault();
		boolean returnText = mobileElement.isSelected();
		reportManager.reportPass("isSelected",
				"Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Returned value \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String clear() {
		setStepNameDefault();
		String returnText = mobileElement.getText();
		mobileElement.clear();
		reportManager.reportPass("clear", "Cleared \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Cleared \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public boolean verifyEqualsTo(String expected, Assert type) {
		setStepNameDefault();
		switch (type) {
		case TEXT:
			strActual = this.mobileElement.getText();
			break;

		case FONT_SIZE:
			strActual = this.mobileElement.getAttribute("font-size");
			break;
		case COLOR:
			strActual = this.mobileElement.getAttribute("color");
			break;
		default:
			strActual = this.mobileElement.getText();
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
			strActual = this.mobileElement.getText();
			break;

		case FONT_SIZE:
			strActual = this.mobileElement.getAttribute("font-size");
			break;
		case COLOR:
			strActual = this.mobileElement.getAttribute("color");
			break;
		default:
			strActual = this.mobileElement.getText();
			break;
		}
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, false);
		return testResult;
	}

	public boolean verifyEqualsTo(String expected) {
		setStepNameDefault();
		strActual = this.mobileElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, true);
		return testResult;
	}

	public boolean verifyNotEqualsTo(String expected) {
		setStepNameDefault();
		strActual = this.mobileElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = equalsToLogger(strActual, expected, false);
		return testResult;
	}

	public boolean verifyContains(String expected) {
		setStepNameDefault();
		strActual = this.mobileElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = containsLogger(strActual, expected, true);
		return testResult;
	}

	public boolean verifyNotContains(String expected) {
		setStepNameDefault();
		strActual = this.mobileElement.getText();
		System.out.println("Retrieved \"" + strActual + "\" from " + alias + separator + this.locator);
		boolean testResult = containsLogger(strActual, expected, false);
		return testResult;
	}

	public boolean verifyAllEqualsTo(List<String> expected) {
		setStepNameDefault();
		int i = 0;
		boolean testResult = true;
		for (WebElement androidElement : this.mobileElements) {
			strActual = androidElement.getText();
			System.out.println(
					"Retrieved \"" + strActual + "\" from " + (i + 1) + " of " + alias + separator + this.locator);
			reportManager
					.reportInfo("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			boolean temp = equalsToLogger(strActual, expected.get(i), true);
			if (!temp) {
				testResult = false;
			}
			i++;
		}
		return testResult;
	}

	public boolean verifyAllEqualsTo(DataTable expected) {
		List<List<String>> data = expected.raw();
		List<String> pobQuestions = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			pobQuestions.add(data.get(i).get(0));
		}
		return verifyAllEqualsTo(pobQuestions);
	}

	public boolean verifyAllContains(DataTable expected) {
		List<List<String>> data = expected.raw();
		List<String> pobQuestions = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			pobQuestions.add(data.get(i).get(0));
		}
		return verifyAllContains(pobQuestions);
	}

	public boolean verifyAllNotEqualsTo(List<String> expected) {
		setStepNameDefault();
		int i = 0;
		boolean testResult = true;
		for (WebElement androidElement : this.mobileElements) {
			strActual = androidElement.getText();
			System.out.println("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			reportManager
					.reportInfo("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			boolean temp = equalsToLogger(strActual, expected.get(i), false);
			if (!temp) {
				testResult = false;
			}
			i++;
		}
		return testResult;
	}

	public boolean verifyAllContains(List<String> expected) {
		setStepNameDefault();
		int i = 0;
		boolean testResult = true;
		for (WebElement androidElement : this.mobileElements) {
			strActual = androidElement.getText();
			System.out.println("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			reportManager
					.reportInfo("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			boolean temp = containsLogger(strActual, expected.get(i), true);
			if (!temp) {
				testResult = false;
			}
			i++;
		}
		return testResult;
	}

	public boolean verifyAllNotContains(List<String> expected) {
		setStepNameDefault();
		int i = 0;
		boolean testResult = true;
		for (WebElement androidElement : this.mobileElements) {
			strActual = androidElement.getText();
			System.out.println("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			reportManager
					.reportInfo("Retrieved \"" + strActual + "\" from " + (i + 1) + alias + separator + this.locator);
			boolean temp = containsLogger(strActual, expected.get(i), false);
			if (!temp) {
				testResult = false;
			}
			i++;
		}
		return testResult;
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

	private void setStepNameDefault() {
		int noOfElements = this.mobileElements.size();
		if (noOfElements == 0) {
			System.out.println("Locator " + alias + separator + locator + " is not found.");
			reportManager.reportFail("Locator " + alias + separator + locator + " is not found.");
		} else if (noOfElements > 1) {
			System.out.println(noOfElements + " elements found on page, finding displayed element.");
			for (int i = 0; i < noOfElements; i++) {
				if (this.mobileElements.get(i).isDisplayed()) {
					this.mobileElement = this.mobileElements.get(i);
					System.out.println(
							noOfElements + " elements found for " + locator + ", element " + (i + 1) + " is visible.");
					break;
				}
			}
		}
	}

	public List<AppiumMobileElement> getAllMobileElements() {
		ArrayList<AppiumMobileElement> genericMobileElements = new ArrayList<AppiumMobileElement>();
		for (WebElement androidElement : this.mobileElements) {
			AppiumWebElement genericMobileElement = new AppiumWebElement(appiumMobileDriver);
			genericMobileElement.mobileElements = this.mobileElements;
			genericMobileElement.mobileElement = androidElement;
			genericMobileElement.locator = locator;
			genericMobileElement.separator = separator;
			genericMobileElement.alias = alias;
			genericMobileElements.add(genericMobileElement);
		}
		return genericMobileElements;
	}

	public void clickAll() {
		setStepNameDefault();
		int i = 1;
		for (WebElement androidElement : this.mobileElements) {
			androidElement.click();
			String logMessage = "Clicked on " + i + " of " + alias + separator + this.locator;
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
			i++;
		}
	}

	public void click(int index) {
		setStepNameDefault();
		if (index == 0) {
			index++;
		}
		this.mobileElements.get(index - 1).click();
		String logMessage = "Clicked on " + index + " of " + alias + separator + this.locator;
		reportManager.reportPass(logMessage);
		System.out.println(logMessage);
	}

	public AppiumMobileElement scrollDownUntilVisible() {
		JavascriptExecutor js = appiumMobileDriver.getWebDriver();
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		int counter = 10;
		while (!this.mobileElement.isDisplayed()) {
			if (counter >= 0) {
				js.executeScript("mobile: scroll", scrollObject);
				counter--;
			}
		}
		reportManager.reportPass("scrollDownToView " + alias + separator + this.locator);
		System.out.println("scrollDownToView " + alias + separator + this.locator);
		return this;
	}

	public AppiumMobileElement scrollDownUntilVisible(String name, String value) {
		JavascriptExecutor js = appiumMobileDriver.getWebDriver();
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		int counter = 10;
		while (!this.mobileElement.getAttribute(name).toString().equals(value)) {
			if (counter >= 0) {
				js.executeScript("mobile: scroll", scrollObject);
				counter--;
			}
		}
		reportManager.reportPass("scrollDownToView " + alias + separator + this.locator);
		System.out.println("scrollDownToView " + alias + separator + this.locator);
		return this;
	}

	public void setValue(String value) {
		setStepNameDefault();
		this.mobileElement.click();
		this.mobileElement.sendKeys(value);
		reportManager.reportPass("Entered \"" + value + "\" in " + alias + " " + this.locator);
		System.out.println("Entered \"" + value + "\" in " + alias + " " + this.locator);
	}

	public void setValue(float value) {
		setStepNameDefault();
		this.mobileElement.click();
		this.mobileElement.sendKeys(Float.toString(value));
		reportManager.reportPass("Entered \"" + value + "\" in " + alias + " " + this.locator);
		System.out.println("Entered \"" + value + "\" in " + alias + " " + this.locator);
	}

	public boolean waitTillVisible() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnText = mobileElement.isDisplayed();
		int counter = 20;
		while (returnText) {
			try {
				Thread.sleep(500);
				returnText = mobileElement.isDisplayed();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter--;
			if (counter >= 0) {
				returnText = false;
			}
		}
		String logMessage = "Verified isDisplayed \"" + returnText + "\" from " + alias + " " + this.locator;
		if (!returnText) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return !returnText;
	}

	public AppiumMobileElement waitUntillVisible() {
		setStepNameDefault();
		reportManager.addScreenCapture(appiumMobileDriver.getMobileDriver());
		int counter = 20;
		while (!this.mobileElement.isDisplayed()) {
			if (counter >= 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				counter--;
			}
		}
		return this;
	}

	public boolean isAllDisplayed() {
		setStepNameDefault();
		reportManager.addScreenCapture(appiumMobileDriver.getMobileDriver());
		boolean allElement = true;
		int i = 1;
		for (WebElement androidElement : this.mobileElements) {
			String logMessage = "Verified isDisplayedAll for element " + i + " of " + alias + separator + this.locator;
			boolean returnValue = false;
			try {
				returnValue = androidElement.isDisplayed();
			} catch (Exception e) {
				allElement = false;
			}
			if (returnValue) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else {
				reportManager.reportFail(logMessage);
				System.out.println(logMessage);
			}
			i++;
		}
		return allElement;
	}

	public boolean isAllEnabled() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnValue = true;
		int i = 1;
		for (WebElement androidElement : this.mobileElements) {
			String logMessage = "Verified isEnabledAll for element no. " + i + " of " + alias + separator
					+ this.locator;
			if (androidElement.isEnabled()) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else {
				returnValue = false;
				reportManager.reportFail(logMessage);
				System.out.println(logMessage);
			}
			i++;
		}
		return returnValue;
	}

	public boolean isDisabled() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnText = mobileElement.isEnabled();
		String logMessage = "Verified isDisabled \"" + !returnText + "\" from " + alias + " " + this.locator;
		if (!returnText) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return !returnText;
	}

	public boolean isAllDisabled() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnValue = true;
		int i = 1;
		for (WebElement androidElement : this.mobileElements) {
			String logMessage = "Verified isDisabledAll for element no. " + i + " of " + alias + separator
					+ this.locator;
			if (!androidElement.isEnabled()) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else {
				returnValue = false;
				reportManager.reportFail(logMessage);
				System.out.println(logMessage);
			}
			i++;
		}
		return returnValue;
	}

	public boolean isAllSelected() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnValue = true;
		int i = 1;
		for (WebElement androidElement : this.mobileElements) {
			String logMessage = "Verified isSelectedAll for element no. " + i + " of " + alias + separator
					+ this.locator;
			if (androidElement.isSelected()) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else {
				returnValue = false;
				reportManager.reportFail(logMessage);
				System.out.println(logMessage);
			}
			i++;
		}
		return returnValue;
	}

	public boolean isDeselected() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnText = mobileElement.isSelected();
		String logMessage = "Verified isDeSelected \"" + !returnText + "\" from " + alias + " " + this.locator;
		if (!returnText) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return returnText;
	}

	public boolean isDeselected(int index) {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		if (index == 0) {
			index++;
		}
		boolean returnText = this.mobileElements.get(index - 1).isSelected();
		String logMessage = "Verified isDeSelected \"" + !returnText + "\" from " + index + " of " + alias + " "
				+ this.locator;
		if (!returnText) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return returnText;
	}

	public boolean isAllDeselected() {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnValue = true;
		int i = 1;
		for (WebElement androidElement : this.mobileElements) {
			String logMessage = "Verified isDeSelectedAll for element no. " + i + " of " + alias + separator
					+ this.locator;
			if (!androidElement.isSelected()) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else {
				returnValue = false;
				reportManager.reportFail(logMessage);
				System.out.println(logMessage);
			}
			i++;
		}
		return returnValue;
	}

	public void setWebElements(MobileElement element, String locator) {
		this.alias = "";
		this.separator = "";
		this.locator = locator;
		this.mobileElements = new ArrayList<WebElement>();
		this.mobileElements.add(element);
		this.mobileElement = element;
	}

	public boolean verifyAttributesEqualsTo(String att, String value) {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnValue = true;
		String logMessage = "Verified attribute " + att + " for element " + alias + separator + this.locator;
		if (value == "0" || value == "null") {
			if (value == "null") {
				value = null;
			}
			logMessage = "Verified isDeselected \"true\" for element " + alias + separator + this.locator;
		} else if (value == "1") {
			logMessage = "Verified isDeselected \"false\" for element " + alias + separator + this.locator;
		}
		if (this.mobileElement.getAttribute(att) == value) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else if (this.mobileElement.getAttribute(att) != null && this.mobileElement.getAttribute(att).equals(value)) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			returnValue = false;
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return returnValue;
	}

	public boolean verifyAttributesAllEqualsTo(String att, String value) {
		setStepNameDefault();
//		reportManager.addScreenCapture(appiumMobileDriver.getWebDriver());
		boolean returnValue = true;
		for (WebElement androidElement : this.mobileElements) {
			String logMessage = "Verified attribute " + att + " for element " + alias + separator + this.locator;
			if (value == "0" || value == "null") {
				if (value == "null") {
					value = null;
				}
				logMessage = "Verified isDeselected \"true\" for element " + alias + separator + this.locator;
			} else if (value == "1") {
				logMessage = "Verified isDeselected \"false\" for element " + alias + separator + this.locator;
			}
			if (this.mobileElement.getAttribute(att) == value) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else if (androidElement.getAttribute(att) != null && androidElement.getAttribute(att).equals(value)) {
				reportManager.reportPass(logMessage);
				System.out.println(logMessage);
			} else {
				returnValue = false;
				reportManager.reportFail(logMessage);
				System.out.println(logMessage);
			}
		}
		return returnValue;
	}

	public boolean verifyAttributesEqualsTo(String att, String value, int index) {
		setStepNameDefault();
		reportManager.addScreenCapture(appiumMobileDriver.getMobileDriver());
		if (index == 0) {
			index++;
		}
		boolean returnValue = true;
		String logMessage = "Verified attribute " + att + " for element " + index + " of " + alias + separator
				+ this.locator;
		if (value == "0" || value == "null") {
			if (value == "null") {
				value = null;
			}
			logMessage = "Verified isDeselected \"true\" for element " + index + " of " + alias + separator
					+ this.locator;
		} else if (value == "1") {
			logMessage = "Verified isDeselected \"false\" for element " + index + " of " + alias + separator
					+ this.locator;
		}
		if (this.mobileElements.get(index - 1).getAttribute(att) == value) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else if (this.mobileElements.get(index - 1).getAttribute(att) != null
				&& this.mobileElements.get(index - 1).getAttribute(att).equals(value)) {
			reportManager.reportPass(logMessage);
			System.out.println(logMessage);
		} else {
			returnValue = false;
			reportManager.reportFail(logMessage);
			System.out.println(logMessage);
		}
		return returnValue;
	}

}
