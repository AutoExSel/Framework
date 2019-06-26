package com.autoexsel.webdriver.wrapper;

public class WebBrowser extends WebDriverWrapperBase {

	public void openURL(String url) {
		setStepNameDefault();
		if (seleniumWebDriver.driver == null) {
			seleniumWebDriver.launchBrowser("chrome");
//			this.maximize();
		}
		seleniumWebDriver.driver.get(url);
		String logger = "Open url '" + url + "'";
		System.out.println(logger);
		reportManager.reportPass(logger);
	}

	public void navigateTo(String url) {
		setStepNameDefault();
		seleniumWebDriver.driver.navigate().to(url);

		String logger = "Navigate to url '" + url + "'";
		System.out.println(logger);
		reportManager.reportPass("", "");
		reportManager.reportPass("navigate", logger);
	}

	public void fullscreen() {
		setStepNameDefault();
		seleniumWebDriver.driver.manage().window().fullscreen();
	}

	public void refresh() {
		setStepNameDefault();
		seleniumWebDriver.driver.navigate().refresh();
	}

	public void forward() {
		setStepNameDefault();
		seleniumWebDriver.driver.navigate().forward();
	}

	public void back() {
		setStepNameDefault();
		seleniumWebDriver.driver.navigate().back();
	}

	public void maximize() {
		setStepNameDefault();
		seleniumWebDriver.driver.manage().window().maximize();
	}
	private void setStepNameDefault() {
//		String stepName = Thread.currentThread().getStackTrace()[3].getMethodName();
//		WebDriverManager.getReportManager().setStepName(stepName);
	}
	
}
