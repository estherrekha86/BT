package com.testRunners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.CommonUtils.ReadAndWritePropertyFile;
import com.CommonUtils.TestCaseSelectorPopUp;
import com.beust.jcommander.internal.Maps;

public class PropertyInjectorListener implements IAlterSuiteListener{
	String[] input = new String[4];
	
	StringBuilder browser = new StringBuilder();
	StringBuilder environment = new StringBuilder();
	StringBuilder tcnos = new StringBuilder();
	StringBuilder apisrun = new StringBuilder();
	
	
	ReadAndWritePropertyFile propFileVar = new ReadAndWritePropertyFile();
	
	@Override
	public void alter(List<XmlSuite> suites) {
		// TODO Auto-generated method stub
		
		String jenRun = System.getProperty("runThroughJenkins");
		System.out.println("jenRun"+jenRun);
		if(jenRun.equalsIgnoreCase("Yes")) {
			XmlSuite suite = suites.get(0);
			String browserFlavours = System.getProperty("browsersType");
			
			String[] browsers = browserFlavours.split(":");
			List<XmlTest> xmlTests = new ArrayList<>();
			for(int i=0;i<browsers.length;i++) {
				XmlTest xmlTest = new XmlTest(suite);
				
				xmlTest.setName(browsers[i]+ "_test");
				Map<String,String> parameters = Maps.newHashMap();
				
				
				parameters.put("browsersType", System.getProperty("browsersType").contains(":")==true
						? System.getProperty("browsersType").split(":")[i]
					    :  System.getProperty("browsersType"));
				
				parameters.put("environment", System.getProperty("environment").contains(":")==true
						? System.getProperty("environment").split(":")[i]
					    :  System.getProperty("environment"));
				
				parameters.put("tcNumbers", System.getProperty("tcNumbers").contains(":")==true
						? System.getProperty("tcNumbers").split(":")[i]
					    :  System.getProperty("tcNumbers"));
				
				parameters.put("apisRun", System.getProperty("apisRun").contains(":")==true
						? System.getProperty("apisRun").split(":")[i]
					    :  System.getProperty("apisRun"));
				xmlTest.setParameters(parameters);
				XmlClass xmlClass= new XmlClass();
				xmlClass.setName("com.testRunners.BT_UkbOnlineRunnerTest");
				xmlTest.getClasses().add(xmlClass);
				xmlTests.add(xmlTest);
			}
			suite.setTests(xmlTests);
		} else {
			
			input = TestCaseSelectorPopUp.generateJTable();
			try {
				ReadAndWritePropertyFile.setPropertyValue("ConstantValues","Browser", input[0]);
				ReadAndWritePropertyFile.setPropertyValue("ConstantValues","Environment", input[1]);
				ReadAndWritePropertyFile.setPropertyValue("ConstantValues","TestCaseNumbers", input[2]);
				ReadAndWritePropertyFile.setPropertyValue("ConstantValues","RunAPIs", input[3]);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			XmlSuite suite = suites.get(0);
			String browserFlavours =null;
			
			try {
				browserFlavours=ReadAndWritePropertyFile.getPropertyValue("ConstantValues","Browser");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String[] browsers = browserFlavours.split(":");
			List<XmlTest> xmlTests = new ArrayList<>();
			for(int i=0;i<browsers.length;i++) {
				XmlTest xmlTest = new XmlTest(suite);
				
				xmlTest.setName(browsers[i]+ "_test");
				Map<String,String> parameters = Maps.newHashMap();
				String Browser = null;
				String Environment = null;
				String TestCaseNumbers = null;
				String RunAPIs = null;
				
				
				try {
					Browser=ReadAndWritePropertyFile.getPropertyValue("ConstantValues","Browser");
					Environment=ReadAndWritePropertyFile.getPropertyValue("ConstantValues","Environment");
					TestCaseNumbers=ReadAndWritePropertyFile.getPropertyValue("ConstantValues","TestCaseNumbers");
					RunAPIs=ReadAndWritePropertyFile.getPropertyValue("ConstantValues","RunAPIs");
					System.out.println("ghhh"+Browser+Environment);
				}catch(IOException e) {
					e.printStackTrace();
				}
				
				parameters.put("browsersType", Browser.contains(":")==true
						? Browser.split(":")[i]
					    :  Browser);
				
				parameters.put("environment", Environment.contains(":")==true
						? Environment.split(":")[i]
					    :  Environment);
				
				parameters.put("tcNumbers", TestCaseNumbers.contains(":")==true
						? TestCaseNumbers.split(":")[i]
					    :  TestCaseNumbers);
				
				parameters.put("apisRun", RunAPIs.contains(":")==true
						? RunAPIs.split(":")[i]
					    : RunAPIs);
				System.out.println(parameters);
				xmlTest.setParameters(parameters);
				XmlClass xmlClass= new XmlClass();
				xmlClass.setName("com.testRunners.BT_UkbOnlineRunnerTest");
				xmlTest.getClasses().add(xmlClass);
				xmlTests.add(xmlTest);
			}
			suite.setTests(xmlTests);
			System.out.println(suite);
			
		}
	}	
}
