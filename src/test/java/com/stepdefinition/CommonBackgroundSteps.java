package com.stepdefinition;

import org.openqa.selenium.WebDriver;

import com.CommonUtils.BT_CommonUtils;
import com.CommonUtils.ReadAndWritePropertyFile;
import com.testRunners.BT_UkbOnlineRunnerTest;
import com.webdriverUtils.WebDriverManager;

import cucumber.api.java.en.When;

public class CommonBackgroundSteps {
	WebDriver driver;
	ReadAndWritePropertyFile propFileVar= new ReadAndWritePropertyFile();
	BT_CommonUtils btComUtil;
	
	
	@When("^user launches the browser$")
	public void user_launches_the_browser() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	
	 if(System.getProperty("runOnDockerGrid").equalsIgnoreCase("yes")){
		 driver = WebDriverManager.launchRemoteBrowser(BT_UkbOnlineRunnerTest.propertyData.get().get("browsersType"));
	 } else {
		 driver = WebDriverManager.launchBrowser(BT_UkbOnlineRunnerTest.propertyData.get().get("browsersType"));
		 
	 }
	 btComUtil = new BT_CommonUtils(driver);
	}
	
	@When("^user enter the BT My Account URL$")
	public void user_enter_the_BT_My_Account_URL() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		String env = BT_UkbOnlineRunnerTest.propertyData.get().get("environment");
		btComUtil.browserBTSaasUrl(env, "No");
	   
	}
	
	
}
