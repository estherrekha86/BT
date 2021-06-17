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

import cucumber.api.PendingException;
import cucumber.api.java.en.When;

public class LoginPageSteps {
	
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
	
	@When("^User enters username$")
	public void user_enters_username() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		LoadAllInstances();
		loginPage.usernameEdtBx.sendKeys(BT_UkbOnlineRunnerTest.testData.get("username"));
	}

	@When("^User enters password$")
	public void user_enters_password() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		LoadAllInstances();
		loginPage.usernameEdtBx.sendKeys(BT_UkbOnlineRunnerTest.testData.get("password"));
	}

	@When("^User clicks on login button$")
	public void user_clicks_on_login_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    loginPage.loginBtn.click();
	    //waits.waitForLoaderToMoveAwayFromScreen(dashboardPage.threeDotsLoader);
	    driver.findElement(By.xpath("//*[@class='modal-close-btn']")).click();
	}
	
	@When("^User navigates to Hub page$")
	public void User_navigates_to_Hub_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		LoadAllInstances();
		System.out.println("user is in hub page");
	}
	
}
