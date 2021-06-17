package com.stepdefinition;

import java.io.IOException;

import com.CommonUtils.TriggerMail;
import com.cucumber.listener.Reporter;
import com.testRunners.BT_UkbOnlineRunnerTest;
import com.webdriverUtils.WebDriverManager;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class Hooks {
	@After
	public void afterScenario(Scenario scenario) throws IOException
	{
		if(scenario.isFailed()) {
			String a = WebDriverManager.captureScreenshot(BT_UkbOnlineRunnerTest.tagNameOfSce);
			Reporter.addScreenCaptureFromPath(a);
			TriggerMail.listOfFailedTC.add(BT_UkbOnlineRunnerTest.tagNameOfSce);
			
		} else  {
			TriggerMail.listOfPassedTC.add(BT_UkbOnlineRunnerTest.tagNameOfSce);
		}
	}
}
