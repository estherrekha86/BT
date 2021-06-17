package com.stepdefinition;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.CommonUtils.BT_CommonUtils;
import com.CommonUtils.ReadAndWritePropertyFile;
import com.testRunners.BT_UkbOnlineRunnerTest;
import com.webElements.DashboardPageElements;
import com.webElements.LoginPageElements;
import com.webdriverUtils.WebDriverManager;

import cucumber.api.java.en.When;
import junit.framework.Assert;

public class DashboardPageSteps {

	WebDriver driver;
	ReadAndWritePropertyFile propFileVar;
	LoginPageElements loginPage;
	DashboardPageElements dashboardPage;
	BT_CommonUtils btComUtil;
	
	
	protected void LoadAllInstances() throws IOException {
		propFileVar = new ReadAndWritePropertyFile();
		driver = WebDriverManager.getDriver();
		btComUtil = new BT_CommonUtils(driver);
		loginPage = PageFactory.initElements(driver, LoginPageElements.class);
		dashboardPage = PageFactory.initElements(driver, DashboardPageElements.class);
	}
	
	@When("^verify dashboard is displayed$")
	public void verify_dashboard_is_displayed() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		LoadAllInstances();
		if(driver.findElements(By.xpath("//*[@class='modal-close-btn']")).size()>0) {
		driver.findElement(By.xpath("//*[@class='modal-close-btn']")).click();}
		
		boolean a = driver.findElement(By.className("pg-title")).isDisplayed();
		Assert.assertEquals(true, a);
		
	   
	}
}
