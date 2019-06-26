package com.autoexsel.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.webdriver.wrapper.SeleniumWebElement;

public class FindWebElements {
	private SeleniumWebDriver seleniumWebDriver = null;
	private By locator;
	private String orSeparator;
	
	public static String alias = "";

	public FindWebElements(SeleniumWebDriver seleniumWebDriver) {
		this.seleniumWebDriver = seleniumWebDriver;
		orSeparator = JSONLoader.getConfigValue("or-separator");
		if(orSeparator.equals("")) {
			orSeparator = "==";
		}
	}

	public SeleniumWebElement byXpath(String xpath) {
		String[] locatorKV = xpath.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = By.xpath(locatorKV[1]);
		} else {
			locator = By.xpath(xpath);
		}
		return getWebElement(locator);
	}

	public SeleniumWebElement byName(String name) {
		String[] locatorKV = name.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = By.name(locatorKV[1]);
		} else {
			locator = By.name(name);
		}
		return getWebElement(locator);
	}

	public SeleniumWebElement byId(String id) {
		String[] locatorKV = id.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = By.id(locatorKV[1]);
		} else {
			locator = By.id(id);
		}
		return getWebElement(locator);
		
	}
	public SeleniumWebElement byAny(WebElement element) {
		String locator = element.toString().split("\\)] -> ")[1];
		locator = locator.substring(0, locator.length() - 1).replace(": ", orSeparator);
		SeleniumWebElement webElement = byAny(locator);
		return webElement;
	}
	public SeleniumWebElement byAny(String locator) {
		SeleniumWebElement webElement = null;
		String[] locatorKV = locator.split(orSeparator);
		alias = locatorKV[0];
		if (locatorKV.length == 3) {
			if (locatorKV[1].equalsIgnoreCase("id")) {
				webElement = byId(locatorKV[2]);
			} else if (locatorKV[1].equalsIgnoreCase("name")) {
				webElement = byName(locatorKV[2]);
			} else if (locatorKV[1].equalsIgnoreCase("xpath")) {
				webElement = byXpath(locatorKV[2]);
			}
		} else if (locatorKV.length == 2) {
			if (locatorKV[1].startsWith("//") || locatorKV[1].startsWith(".//")) {
				webElement = byXpath(locatorKV[1]);
			} else {
				webElement = byName(locatorKV[1]);
			}
		}else if (locatorKV.length == 1) {
			if (locatorKV[0].startsWith("//") || locatorKV[0].startsWith(".//")) {
				webElement = byXpath(locatorKV[0]);
			} else {
				webElement = byName(locatorKV[0]);
			}
		}
		return webElement;
	}

	private SeleniumWebElement getWebElement(By locator) {
		SeleniumWebElement genericWebElement = new SeleniumWebElement(seleniumWebDriver);
		genericWebElement.setWebElements(locator);
		genericWebElement.as(alias);
		alias = "";
		return genericWebElement;
	}

}
