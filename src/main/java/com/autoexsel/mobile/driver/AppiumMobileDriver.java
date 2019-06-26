package com.autoexsel.mobile.driver;

import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.autoexsel.data.manager.JSONLoader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class AppiumMobileDriver extends AppiumDriverBase {
	
	private AppiumDriver<WebElement> appiumWebDriver = null;
	private AppiumDriver<MobileElement> appiumMobileDriver = null;

	public AppiumDriver<MobileElement> launchIOSApplication(String host) throws Exception {
		System.out.println("Current host: " + host);
		appiumMobileDriver = new IOSDriver<MobileElement>(new URL(host), setAppiumDriverCapabilities(AppType.IOSAPP));
		appiumMobileDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(1000);
		return appiumMobileDriver;
	}

	public AppiumDriver<MobileElement> launchAndroidApplication(String host) throws Exception {
		System.out.println("host: " + host);
		appiumMobileDriver = new AndroidDriver<MobileElement>(new URL(host),
				setAppiumDriverCapabilities(AppType.ANDROIDAPP));
		appiumMobileDriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		Thread.sleep(1000);
		return appiumMobileDriver;
	}
	
	public AppiumDriver<WebElement> launchIOSWebApplication(String host) throws Exception {
		System.out.println("Current host: " + host);
		appiumWebDriver = new AndroidDriver<WebElement>(new URL(host), setAppiumDriverCapabilities(AppType.IOSWEB));
		appiumWebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return appiumWebDriver;
	}
	
	public AppiumDriver<WebElement> launchAndroidWebApplication(String host) throws Exception {
		System.out.println("Current host: " + host);
		appiumWebDriver = new AndroidDriver<WebElement>(new URL(host), setAppiumDriverCapabilities(AppType.ANDROIDWEB));
		appiumWebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return appiumWebDriver;
	}

	public AppiumDriver<MobileElement> getMobileDriver() {
		if(appiumMobileDriver != null) {
			return appiumMobileDriver;
		}else {
			System.out.println("!!!!!!!!! Warning: Appium driver is not initialized !!!!!!!!!");
		}
		return null;
	}
	public AppiumDriver<WebElement> getWebDriver() {
		if(appiumWebDriver != null) {
			return appiumWebDriver;
		}else {
			System.out.println("!!!!!!!!! Warning: Appium driver is not initialized !!!!!!!!!");
		}
		return null;
	}

	private Capabilities setAppiumDriverCapabilities(AppType appType) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		JSONObject mobileAppConfig = null;
		switch (appType) {
		case ANDROIDAPP:
			mobileAppConfig = JSONLoader.config.getJSONObject("android-app");
			break;
		case ANDROIDWEB:
			mobileAppConfig = JSONLoader.config.getJSONObject("android-web");
			break;
		case IOSAPP:
			mobileAppConfig = JSONLoader.config.getJSONObject("ios-app");
			break;
		case IOSWEB:
			mobileAppConfig = JSONLoader.config.getJSONObject("ios-web");
			break;
		default:
			break;
		}
		capabilities.setCapability("autoAcceptAlerts", true);
		Iterator<?> keys = mobileAppConfig.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = mobileAppConfig.getString(key);
//			System.out.println(key + ":" + value);
			if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
				capabilities.setCapability(key.toString(), Boolean.parseBoolean(value));
			} else {
				capabilities.setCapability(key.toString(), value.toString());
			}
		}
		return capabilities;
	}
}
