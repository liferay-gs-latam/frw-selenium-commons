package com.liferay.gs.testFramework;

import java.io.File;

import org.openqa.selenium.WebDriver;

public class SeleniumReadPropertyKeys {

	public static final WebDriver DRIVER = Selenium.getDriver();

	private static String TIME_OUT = null;

	private static String PLATFORM_NAME = null;
	private static String URL = null;
	private static String URL_LOGOUT = null;
	private static String SELENIUM_GRID_IP = null;
	private static String SELENIUM_GRID_DOCKER_IP = null;
	private static String GECKODRIVER_PATH = null;
	private static String CHROMEDRIVER_PATH = null;
	private static String ATTACH_FILE_PATH = null;
	private static String DEFAULT_PROPERTIES_FILE_PATH = null;
	private static String DEFAULT_USERNAME = null;
	private static String DEFAULT_PASSWORD = null;

	public static int getTimeOut() {
		TIME_OUT = ReadProperties.getConfigigurationOfPropertiesFile("time-out", "10");
		int timeOut = Integer.parseInt(TIME_OUT);
		return timeOut;
	}

	public static String getAttachFilePath() {
		ATTACH_FILE_PATH = ReadProperties.getConfigigurationOfPropertiesFile("PathWithAttachFiles");
		return ATTACH_FILE_PATH;
	}

	public static String getDefaultPlatformName() {
		PLATFORM_NAME = ReadProperties.getConfigigurationOfPropertiesFile("browser");
		return PLATFORM_NAME;
	}
	
	public static String getUrlToHome() {
		URL = ReadProperties.getConfigigurationOfPropertiesFile("environment", "http://localhost:8080");
		return URL;
	}
	
	public static String getDefaultUsername() {
		DEFAULT_USERNAME = ReadProperties.getConfigigurationOfPropertiesFile("username", "test@liferay.com");
		return DEFAULT_USERNAME;
	}

	public static String getDefaultPassword() {
		DEFAULT_PASSWORD = ReadProperties.getConfigigurationOfPropertiesFile("password", "test");
		return DEFAULT_PASSWORD;
	}


	public static String getLinkToLogOut() {
		URL_LOGOUT = ReadProperties.getConfigigurationOfPropertiesFile("linkToLogOut", "/c/portal/logout");
		return URL_LOGOUT;
	}

	public static String getSeleniumGridMachine() {
		SELENIUM_GRID_IP = ReadProperties.getConfigigurationOfPropertiesFile("SeleniumGridMachine");
		return SELENIUM_GRID_IP;
	}

	public static String getSeleniumGridDocker() {
		SELENIUM_GRID_DOCKER_IP = ReadProperties.getConfigigurationOfPropertiesFile("SeleniumGridDockerPath");
		return SELENIUM_GRID_DOCKER_IP;
	}

	public static String getGeckoDriverPath() {
		String localPropertiesFile;
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			localPropertiesFile = "/SeleniumProperties/geckodriver.exe";
		} else {
			localPropertiesFile = "/SeleniumProperties/geckodriver";
		}

		File localFile = new File(getProjectPath() + localPropertiesFile);

		if (localFile.exists()) {
			GECKODRIVER_PATH = getProjectPath() + localPropertiesFile;
		} else {
			GECKODRIVER_PATH = ConfigurationOS.getString("GeckoDriver_Path");
		}

		return GECKODRIVER_PATH;
	}

	public static String getDownloadSaveFilePath() {
		URL_LOGOUT = ReadProperties.getConfigigurationOfPropertiesFile("DownloadSaveFilePath", "default");
		return URL_LOGOUT;
	}
	
	public static String getChromeDriverPath() {
		String localPropertiesFile;
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			localPropertiesFile = "/SeleniumProperties/chromedriver.exe";
		} else {
			localPropertiesFile = "/SeleniumProperties/chromedriver";
		}

		File localFile = new File(getProjectPath() + localPropertiesFile);

		if (localFile.exists()) {
			CHROMEDRIVER_PATH = getProjectPath() + localPropertiesFile;
		} else {
			CHROMEDRIVER_PATH = ConfigurationOS.getString("ChromeDriver_Path");
		}

		return CHROMEDRIVER_PATH;
	}

	public static String getSeleniumPropertyKeysFilePath() {
		String localPropertiesFile = "/SeleniumProperties/SeleniumPropertyKeys.properties";
		File localFile = new File(getProjectPath() + localPropertiesFile);

		if (localFile.exists()) {
			DEFAULT_PROPERTIES_FILE_PATH = getProjectPath() + localPropertiesFile;
		} else {
			DEFAULT_PROPERTIES_FILE_PATH = ConfigurationOS.getString("SeleniumPropertyKeys");
		}

		return DEFAULT_PROPERTIES_FILE_PATH;
	}

	private static String getProjectPath() {
		String projectPath = System.getProperty("user.dir");
		return projectPath;
	}

}
