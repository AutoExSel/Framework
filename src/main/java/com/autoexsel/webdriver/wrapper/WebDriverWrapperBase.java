package com.autoexsel.webdriver.wrapper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autoexsel.report.manager.ReportManager;
import com.autoexsel.webdriver.SeleniumWebDriver;

public abstract class WebDriverWrapperBase {
	protected WebDriver driver;
	protected By locator;
	protected WebElement webElement;
	protected List<WebElement> webElements;
	protected List<SeleniumWebElement> genericWebElements;
	
	protected String alias;
	protected String separator;

	protected String strActual;
	protected int intActual;
	protected double dblActual;
	protected float floatActual;

	public static enum Assert {
		TEXT, COLOR, FONT_SIZE
	};

	protected static ReportManager reportManager;
	protected static SeleniumWebDriver seleniumWebDriver;

}
