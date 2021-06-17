package com.CommonUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.testRunners.BT_UkbOnlineRunnerTest;
import com.webElements.DashboardPageElements;
import com.webElements.LoginPageElements;

public class BT_CommonUtils {

	
	private WebDriver driver;
	ReadAndWritePropertyFile propFileRead;
	LoginPageElements loginPage;
	
	DashboardPageElements dashboardPage;
	
	public BT_CommonUtils(WebDriver ldriver) {
		this.driver = ldriver;
		propFileRead = new ReadAndWritePropertyFile();
		loginPage = PageFactory.initElements(driver, LoginPageElements.class);
		dashboardPage = PageFactory.initElements(driver, DashboardPageElements.class);
		
	}
	
	public void browserBTSaasUrl(String env,String cdeAdminUrl) throws IOException {
		switch(cdeAdminUrl) {
		
		case "No":
			if(env.equals("Model-A")) {
				driver.get(propFileRead.getPropertyValue("EnvironmentUrls", "modelA_Url"));
			} else if(env.equals("Model-B")) {
				driver.get(propFileRead.getPropertyValue("EnvironmentUrls", "modelB_Url"));
			}
			break;
		case "Yes":
			if(env.equals("Model-A")) {
				driver.get(propFileRead.getPropertyValue("EnvironmentUrls", "cdeModelA_Url"));
			} else if(env.equals("Model-B")) {
				driver.get(propFileRead.getPropertyValue("EnvironmentUrls", "cdeModelB_Url"));
			}
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	}

	public void loginToBT_MyAccount() {
		loginPage.usernameEdtBx.sendKeys(BT_UkbOnlineRunnerTest.testData.get("username"));
		loginPage.passwordEdtBx.sendKeys(BT_UkbOnlineRunnerTest.testData.get("password"));
		System.out.println("username"+BT_UkbOnlineRunnerTest.testData.get("username") );
	}
}
