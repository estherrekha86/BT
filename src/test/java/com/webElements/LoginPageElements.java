package com.webElements;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.webdriverUtils.WebDriverManager;

public class LoginPageElements {
	
	public LoginPageElements() throws IOException {
		PageFactory.initElements(WebDriverManager.getDriver(), this);
	}
	
	@FindBy(id="USER")
	public WebElement usernameEdtBx;
	
	@FindBy(id="PASSWORD")
	public WebElement passwordEdtBx;
	
	@FindBy(id="btnAuthenticateUser")
	public WebElement loginBtn;

}
