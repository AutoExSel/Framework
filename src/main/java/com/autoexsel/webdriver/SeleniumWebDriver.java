package com.autoexsel.webdriver;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumWebDriver extends WebDriverBase {

	public WebDriver driver = null;

	public WebDriver getDriverInstance() {
		return driver;
	}

	public void launchBrowser(String strBrowser) {
		if (driver == null && strBrowser.toLowerCase().contains("chrome")) {
			System.out.println("Launching chrome browser.");
			driver = getChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	public WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", getChromePath());
		driver = new ChromeDriver();
		return driver;
	}

	public void close() {
		driver.close();
	}

	public String getChromePath(){
		String binaryPath = getRoot()+"/lib/bin/chromedriver.exe";
		if (System.getProperty("os.name").contains("Mac"))
			binaryPath = binaryPath.replace(".exe", "");

		File jsonFile = new File(binaryPath);
		boolean jsonExist = jsonFile.exists();
		if(!jsonExist) {
			System.out.println("Binary path for chrome "+binaryPath+" does not exist.");
		}
		return binaryPath;
	}

	public DesiredCapabilities getChromeCapabilities() {
		HashMap<String, Object> chromePreference = new HashMap<String, Object>();
		chromePreference.put("profile.default_content_settings.popups", 0);
		chromePreference.put("download.default_directory", "\\Downloads");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePreference);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}
	
	private String getRoot() {
		File resultDir = new File(System.getProperty("user.dir"));
		return resultDir.getAbsolutePath();
	}

}
