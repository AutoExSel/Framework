package com.autoexsel.webdriver.wrapper;

import org.openqa.selenium.By;

import io.appium.java_client.MobileElement;

public class ActionManager extends AssertionManager {
	
	public void clickAll() {

	}

	public void click(int index) {

	}

	public void setValue(String keys) {

	}

	public void setWebElements(By locator) {

	}

	public void clickOnSlider(int noOftimes) {

	}

	public void setWebElements(MobileElement element, String locator) {

	}
	
	public void click() {
		setStepNameDefault();
		this.webElement.click();
		reportManager.reportPass("Clicked on " + alias + separator + this.locator);
		System.out.println("Clicked on " + alias + separator + this.locator);
	}

	public void submit() {
		setStepNameDefault();
		this.webElement.submit();
		reportManager.reportPass("Submitted " + alias + separator + this.locator);
		System.out.println("Submitted " + alias + separator + this.locator);
	}

	public void sendKeys(String keys) {
		setStepNameDefault();
		this.webElement.sendKeys(keys);
		reportManager.reportPass("Entered \"" + keys + "\" in " + alias + " " + this.locator);
		System.out.println("Entered \"" + keys + "\" in " + alias + " " + this.locator);
	}

	public String getText() {
		setStepNameDefault();
		String returnText;
		if (this.webElements == null) {
			returnText = this.webElement.getText();
		} else {
			returnText = this.webElement.getText();
		}
		reportManager.reportPass("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String getAttribute(String attr) {
		setStepNameDefault();
		String returnText = this.webElement.getAttribute(attr);
		reportManager.reportPass("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String getCssValue(String attr) {
		setStepNameDefault();
		String returnText = webElement.getCssValue(attr);
		reportManager.reportPass("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String getTagName() {
		setStepNameDefault();
		String returnText = webElement.getTagName();
		reportManager.reportPass("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Retrieved \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	public String clear() {
		setStepNameDefault();
		String returnText = webElement.getText();
		webElement.clear();
		reportManager.reportPass("Cleared \"" + returnText + "\" from " + alias + " " + this.locator);
		System.out.println("Cleared \"" + returnText + "\" from " + alias + " " + this.locator);
		return returnText;
	}

	private void setStepNameDefault() {
		int noOfElements = this.webElements.size();
		if (noOfElements == 0) {
			System.out.println("Locator " + alias + separator + locator + " is not found.");
			reportManager.reportFail("Locator " + alias + separator + locator + " is not found.");
		} else if (noOfElements > 1) {
			System.out.println(noOfElements + " elements found on page, finding displayed element.");
			for (int i = 0; i < noOfElements; i++) {
				if (this.webElements.get(i).isDisplayed()) {
					this.webElement = this.webElements.get(i);
					break;
				}
			}
		}
	}
}
