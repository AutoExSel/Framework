package com.autoexsel.mobile.driver;

import com.autoexsel.mobile.wrapper.AppiumMobileElement;

import io.appium.java_client.MobileElement;

public interface FindAppiumMobileElement {
	public AppiumMobileElement byXpath(String xpath);

	public AppiumMobileElement byName(String name);

	public AppiumMobileElement byAccessibilityId(String id);

	public AppiumMobileElement byId(String id);

	public AppiumMobileElement byAny(MobileElement element);

	public AppiumMobileElement byAny(String locator);

}
