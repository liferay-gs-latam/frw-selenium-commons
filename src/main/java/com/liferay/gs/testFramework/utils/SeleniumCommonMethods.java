package com.liferay.gs.testFramework.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * @author Manoel Cyreno
 */
public class SeleniumCommonMethods {
	
	public SeleniumCommonMethods(WebDriver webDriver) {
		_webDriver = webDriver;
	}

	private static WebDriver _webDriver;

	private static Actions actionBuilder = new Actions(_webDriver);

	private static final By addButtonLocator = By.xpath(
			".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_addToggleId']");
	private static final By applicationHeadingLocator = By.xpath(
			".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_addApplicationHeading']");
	private static final By searchApplicationLocator = By.xpath(
			".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_searchApplication']");

	/*
	 * Add the portlet on Screen, but to do this, the user should inform the
	 * portlet name and the column that the portlet will appear
	 */
	public static void addPortletOnScreen(String portletName, String column) {
		clickOnAddButton();
		clickOnApplicationCategory();
		searchForPortletByName(portletName);
		dragAndDropPortletToColumn(portletName, column);
	}

	/*
	 * Remove all portlets from the current screen.
	 */
	public static void removeAllPorlets() {
		List<WebElement> portlets = _webDriver.findElements(By.cssSelector(".portlet-layout .portlet"));
		for (WebElement portlet : portlets) {
			openPortletActionDropDown(portlet);
			clickOnPortletConfigurationMenu("Remove");
			acceptBrowserDialog();
		}
	}

	/*
	 * Remove all specific portlets from the current screen, but to do this the
	 * user should inform any part of the portlet ID, that all portlets with
	 * this part of ID to be removed.
	 */
	public static void removeAllSpecificPorlets(String partOfPortletID) {
		List<WebElement> portlets = _webDriver
				.findElements(By.xpath(".//*[contains(@id,'" + partOfPortletID + "')]"));
		for (WebElement portlet : portlets) {
			openPortletActionDropDown(portlet);
			clickOnPortletConfigurationMenu("Remove");
			acceptBrowserDialog();
		}
	}

	private static void dragAndDropPortletToColumn(String portletName, String column) {
		SeleniumWaitMethods.waitMediumTime();

		By searchApplicationResultLocator = By
				.xpath(".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_portletCategory0']//*[contains (text(), '"
						+ portletName + "')]");

		By columnLocator = By.xpath(".//*[@id='" + column + "']");

		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.visibilityOfElementLocated(searchApplicationResultLocator));
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.elementToBeClickable(searchApplicationResultLocator));
		WebElement element = _webDriver.findElement(searchApplicationResultLocator);
		WebElement target = _webDriver.findElement(columnLocator);
		(new Actions(_webDriver)).dragAndDrop(element, target).perform();
		SeleniumWaitMethods.waitMediumTime();
	}

	private static void searchForPortletByName(String portletName) {
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.visibilityOfElementLocated(searchApplicationLocator));
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.elementToBeClickable(searchApplicationLocator));
		_webDriver.findElement(searchApplicationLocator).sendKeys(portletName);
	}

	private static void clickOnAddButton() {
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.visibilityOfElementLocated(addButtonLocator));
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.elementToBeClickable(addButtonLocator));
		_webDriver.findElement(addButtonLocator).click();
	}

	private static void clickOnApplicationCategory() {
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.visibilityOfElementLocated(applicationHeadingLocator));
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.elementToBeClickable(applicationHeadingLocator));

		By portletContentCategories = By.cssSelector(".add-content-menu .lfr-content-category");
		boolean isApplicationCategoriesDisplayed = _webDriver.findElement(portletContentCategories).isDisplayed();

		if (!isApplicationCategoriesDisplayed) {
			SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.visibilityOfElementLocated(applicationHeadingLocator));
			SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.elementToBeClickable(applicationHeadingLocator));
			_webDriver.findElement(applicationHeadingLocator).click();
		}
	}

	private static void openPortletActionDropDown(WebElement portletElement) {
		WebElement configButton = portletElement.findElement(By.cssSelector(".lexicon-icon-ellipsis-v"));
		actionBuilder.moveToElement(configButton).pause(Duration.ofMillis(200)).perform();
		configButton.click();
	}

	private static void clickOnPortletConfigurationMenu(String title) {
		By dropDownMenu = By.cssSelector(".dropdown-menu");
		SeleniumWaitMethods.getWaitDriver(_webDriver).until(ExpectedConditions.visibilityOfElementLocated(dropDownMenu));

		WebElement dropDownMenuElement = _webDriver.findElement(dropDownMenu);

		List<WebElement> elements = dropDownMenuElement.findElements(By.cssSelector("li a.lfr-icon-item"));
		WebElement element = elements.stream().filter(el -> el.getText().trim().equals(title)).findFirst().get();
		element.click();
	}

	private static void acceptBrowserDialog() {
		Alert alert = _webDriver.switchTo().alert();
		alert.accept();
	}
}