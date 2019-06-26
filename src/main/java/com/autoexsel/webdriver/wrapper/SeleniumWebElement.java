package com.autoexsel.webdriver.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.autoexsel.webdriver.SeleniumWebDriver;

public class SeleniumWebElement extends ActionManager {

	public SeleniumWebElement(SeleniumWebDriver seleniumWebDriver) {
		WebDriverWrapperBase.seleniumWebDriver = seleniumWebDriver;
		reportManager = seleniumWebDriver.getReportManager();
		driver = seleniumWebDriver.driver;
	}

	public SeleniumWebElement as(String as) {
		this.alias = "'" + as + "', ";
		this.separator = " ";
		return this;
	}

	public void setWebElements(By locator) {
		this.alias = "";
		this.separator = "";
		this.locator = locator;

		this.webElements = driver.findElements(locator);
		if (this.webElements.size() == 0) {
			this.webElement = null;
		} else {
			this.webElement = this.webElements.get(0);
		}
	}

	public List<SeleniumWebElement> getAllWebElements() {
		genericWebElements = new ArrayList<SeleniumWebElement>();
		for (WebElement webElement : this.webElements) {
			SeleniumWebElement genericWebElement = new SeleniumWebElement(seleniumWebDriver);
			genericWebElement.webElements = this.webElements;
			genericWebElement.webElement = webElement;
			genericWebElement.locator = locator;
			genericWebElement.separator = separator;
			genericWebElement.alias = alias;
			genericWebElements.add(genericWebElement);
		}
		return genericWebElements;
	}


}
