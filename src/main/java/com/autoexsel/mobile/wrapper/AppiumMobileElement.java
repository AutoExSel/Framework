package com.autoexsel.mobile.wrapper;

import java.util.List;

import org.openqa.selenium.By;

import com.autoexsel.mobile.wrapper.AppiumWrapperBase.Assert;

import cucumber.api.DataTable;
import io.appium.java_client.MobileElement;

public interface AppiumMobileElement {

	public AppiumMobileElement as(String as);

	public List<AppiumMobileElement> getAllMobileElements();

	public AppiumMobileElement scrollDownUntilVisible();

	public AppiumMobileElement scrollDownUntilVisible(String name, String value);

	public AppiumMobileElement waitUntillVisible();

	public void click();

	public void clickAll();

	public void click(int index);

	public void submit();

	public void sendKeys(String keys);

	public void setValue(String keys);

	public void setValue(float value);

	public void setWebElements(By locator);

	public void setWebElements(MobileElement element, String locator);

	public String clear();

	public String getText();

	public String getTagName();

	public String getAttribute(String attr);

	public String getCssValue(String attr);

	public boolean isEnabled();

	public boolean isAllEnabled();

	public boolean isDisabled();

	public boolean isAllDisabled();

	public boolean isSelected();

	public boolean isAllSelected();

	public boolean isDeselected();

	public boolean isAllDeselected();

	public boolean isDeselected(int index);

	public boolean isDisplayed();

	public boolean waitTillVisible();

	public boolean isAllDisplayed();

	public boolean verifyEqualsTo(String expected);

	public boolean verifyEqualsTo(String expected, Assert type);

	public boolean verifyAllEqualsTo(DataTable expected);

	public boolean verifyAllContains(DataTable expected);

	public boolean verifyAllEqualsTo(List<String> expected);

	public boolean verifyAllNotEqualsTo(List<String> expected);

	public boolean verifyContains(String expected);

	public boolean verifyAllContains(List<String> expected);

	public boolean verifyNotContains(String expected);

	public boolean verifyNotEqualsTo(String expected);

	public boolean verifyAllNotContains(List<String> expected);

	public boolean verifyNotEqualsTo(String expected, Assert type);

	public boolean verifyAttributesEqualsTo(String att, String value);

	public boolean verifyAttributesEqualsTo(String att, String value, int index);

	public boolean verifyAttributesAllEqualsTo(String att, String value);

}
