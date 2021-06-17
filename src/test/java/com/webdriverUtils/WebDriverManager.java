package com.webdriverUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.testRunners.BT_UkbOnlineRunnerTest;

public class WebDriverManager {
	
	public static ThreadLocal<WebDriver> driver=new ThreadLocal<WebDriver>();
	
	static String browserName;
	
	public WebDriverManager() {
		browserName = BT_UkbOnlineRunnerTest.propertyData.get().get("browsersType");
	}
	
	public static WebDriver getDriver() throws IOException {
		if(driver.get()==null) {
			if(System.getProperty("runOnDockerGrid").equalsIgnoreCase("Yes"))
				launchRemoteBrowser(browserName);
			else
				launchBrowser(browserName);
					
		}
		return driver.get();
	}

	public static WebDriver launchBrowser(String browserName) {
		// TODO Auto-generated method stub
		switch (browserName) {
		 
		 case "Chrome":
		 String chromePath= System.getProperty("user.dir")+"/src/test/resources/browsers/chromedriver.exe";
		 System.setProperty("webdriver.chrome.driver", chromePath);
			
			  ChromeOptions options = new ChromeOptions();
			  options.addArguments("--headless"); 
			  options.addArguments("--no-sandbox");
			  options.addArguments("--disable-popup-blocking");
			  options.addArguments("--disable-dev-shm-usage"); 
			  Map<String, Object> prefs =
			  new HashMap<String,Object>();
			  prefs.put("profile.default_content_settings.popups", 0);
			  options.setExperimentalOption("prefs", true);
			 
		 
		 DesiredCapabilities cap = DesiredCapabilities.chrome();
		 cap.setCapability(ChromeOptions.CAPABILITY, options);	
		 driver.set(new ChromeDriver(cap));	
		 break;
		 
		 case "Firefox":
			 String pathBinary = "C:/Users/esther/AppData/Local/Mozilla Firefox/firefox.exe";
			 String firefoxPath= System.getProperty("user.dir")+"/src/test/resources/com/btUkbOnline/browsers/firefoxdriver.exe";
			 System.setProperty("webdriver.gecko.driver", firefoxPath);
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"C:\\temp\\log.txt");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setBinary(pathBinary);
		firefoxOptions.addPreference("marionette", true);
		 driver.set(new FirefoxDriver(firefoxOptions));
		 break;
		 
		 default:
			 break;
		 }
		return driver.get();
		
		
	}

	public static WebDriver launchRemoteBrowser(String browserName) throws MalformedURLException {
		// TODO Auto-generated method stub
		RemoteWebDriver rdriver = null;
		URL url = new URL("http://172.23.240.1:4444/wd/hub");
		/*
		 * Proxy proxy=new Proxy(); 
		 * proxy.setProxyType(ProxyType.PAC);
		 * proxy.setProxyAutoconfigUrl("http://acl.intra.bt.com/default.pac");
		 */
		 switch (browserName) {
		 
		 case "Chrome":
			 String chromePath= System.getProperty("user.dir")+"src/test/resources/com/btUkbOnline/browsers/chromedriver.exe";
			 System.setProperty("webdriver.chrome.driver", chromePath);
		 ChromeOptions options = new ChromeOptions();
		 options.addArguments("--disable-popup-blocking");
		 Map<String, Object> prefs = new HashMap<String,Object>();
		 prefs.put("profile.default_content_settings.popups", 0);
		 DesiredCapabilities cap = DesiredCapabilities.chrome();
		 cap.setBrowserName("chrome");
		 cap.setCapability("name", "ChromeTest");
		// cap.setCapability(CapabilityType.PROXY, proxy);
		 cap.setCapability(ChromeOptions.CAPABILITY, options);
		
		 cap.setCapability(ChromeOptions.CAPABILITY, options);
		 rdriver = new RemoteWebDriver(url,cap);
		 driver.set(rdriver);
		
		 break;
		 
		 case "Firefox":
			 
			 String firefoxPath= System.getProperty("user.dir")+"/src/test/resources/com/btUkbOnline/browsers/chromedriver.exe";
			 System.setProperty("webdriver.gecko.driver", firefoxPath);
		
		 DesiredCapabilities capff = DesiredCapabilities.firefox();
		 capff.setBrowserName("firefox");
		 capff.setCapability("name", "firefoxTest");
		 //capff.setCapability(CapabilityType.PROXY, proxy);
		 rdriver = new RemoteWebDriver(url,capff);
		 driver.set(rdriver);
		 break;
		 }
		return driver.get();
	}
	
	public static String captureScreenshot(String screenshotName) throws IOException {
		
		
		TakesScreenshot shot = (TakesScreenshot)driver.get();
		File source = shot.getScreenshotAs(OutputType.FILE);
		String screenshotPath = System.getProperty("user.dir")+"//Reports//FailedScreenshots//" + screenshotName + ".jpg";
		FileUtils.copyFile(source, new File(screenshotPath));
		return screenshotPath;
	}
	

}
