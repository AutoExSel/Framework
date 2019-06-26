package com.autoexsel.mobile.driver;

import org.openqa.selenium.By;

import com.autoexsel.data.manager.JSONLoader;
import com.autoexsel.mobile.wrapper.AppiumMobileElement;
import com.autoexsel.mobile.wrapper.AppiumWebElement;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class FindAppiumWebElement extends AppiumDriverBase implements FindAppiumMobileElement {
	private AppiumMobileDriver appiumMobileDriver = null;
	private By locator;
	private String orSeparator = "==";

	public FindAppiumWebElement(AppiumMobileDriver appiumMobileDriver) {
		this.appiumMobileDriver = appiumMobileDriver;
		orSeparator = JSONLoader.getConfigValue("or-separator");
		if(orSeparator.equals("")) {
			orSeparator = "==";
		}
	}

	public AppiumMobileElement byXpath(String xpath) {
		String[] locatorKV = xpath.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = MobileBy.xpath(locatorKV[1]);
		} else {
			locator = MobileBy.xpath(xpath);
		}
		return getWebElement(locator);
	}

	public AppiumMobileElement byName(String name) {
		String[] locatorKV = name.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = MobileBy.name(locatorKV[1]);
		} else {
			locator = MobileBy.name(name);
		}
		return getWebElement(locator);
	}

	public AppiumMobileElement byAccessibilityId(String id) {
		String[] locatorKV = id.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = MobileBy.AccessibilityId(locatorKV[1]);
		} else {
			locator = MobileBy.AccessibilityId(id);
		}
		return getWebElement(locator);
	}

	public AppiumMobileElement byId(String id) {
		String[] locatorKV = id.split(orSeparator);
		if(alias.trim().contains("")) {
			alias = locatorKV[0];
		}
		if (locatorKV.length == 2) {
			locator = MobileBy.id(locatorKV[1]);
		} else {
			locator = MobileBy.id(id);
		}
		return getWebElement(locator);
	}

	public AppiumMobileElement byAny(MobileElement element) {
		String locator = element.toString();
		locator = locator.substring(locator.indexOf("({") + 2, locator.indexOf("})"));
		appiumMobileElement = new AppiumWebElement(appiumMobileDriver);
		appiumMobileElement.setWebElements(element, locator);
		return appiumMobileElement;
	}

	public AppiumMobileElement byAny(String locator) {
		AppiumMobileElement webElement = null;
		String[] locatorKV = locator.split(orSeparator);
		alias = locatorKV[0];
		if (locatorKV.length == 3) {
			String locatorType = locatorKV[1];
			if (locatorType.equalsIgnoreCase("id")) {
				webElement = byId(locatorKV[2]);
			} else if (locatorType.equalsIgnoreCase("name")) {
				webElement = byName(locatorKV[2]);
			} else if (locatorType.equalsIgnoreCase("xpath")) {
				webElement = byXpath(locatorKV[2]);
			} else if (locatorType.equalsIgnoreCase("accessibility id")) {
				webElement = byAccessibilityId(locatorKV[2]);
			}
		}else if (locatorKV.length == 2) {
			if (locatorKV[1].startsWith("//") || locatorKV[1].startsWith(".//")) {
				webElement = byXpath(locatorKV[1]);
			} else {
				webElement = byAccessibilityId(locatorKV[1]);
			}
		} 
		else if (locatorKV.length == 1) {
			if (locatorKV[0].startsWith("//") || locatorKV[0].startsWith(".//")) {
				webElement = byXpath(locatorKV[0]);
			} else {
				webElement = byAccessibilityId(locatorKV[0]);
			}
		}
		return webElement;
	}

	private AppiumMobileElement getWebElement(By locator) {
		appiumMobileElement = new AppiumWebElement(appiumMobileDriver);
		appiumMobileElement.setWebElements(locator);
		appiumMobileElement.as(alias);
		alias = "";
		return appiumMobileElement;
	}
}
