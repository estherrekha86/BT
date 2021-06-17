
package com.testRunners;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.CommonUtils.CommonMethods;
import com.CommonUtils.ReadAndWriteExcelFile;
import com.CommonUtils.ReadAndWritePropertyFile;
import com.CommonUtils.TriggerMail;

import cucumber.api.CucumberOptions;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;


 
//@RunWith(Cucumber.class)
//@CucumberOptions(
//		features = "src/test/resources/functionalTest",
//				glue= {"stepdefinition"},
//				dryRun = false,
//				monochrome = true, 
//				format = {"pretty", "html:TestReports", "json:TestReports/cucumber.json", "junit:TestReports/cucumber.xml"}
//				
//		)
//public class TestRunner {
//}

@CucumberOptions(plugin= { "html:target/cucumber-html-report","json:target/cucumber.json","com.cucumber.listener.ExtentCucumberFormatter:Reports/report.html"},monochrome =true)

public class BT_UkbOnlineRunnerTest {
	
	WebDriver driver;
	ReadAndWritePropertyFile propFileVar;
	TriggerMail triggerMail = new TriggerMail();
	public static String tagNameOfSce;
	String environment;
	
	public static Map<String,String> testData;
	ReadAndWriteExcelFile excelFileVar;
	
	public static ThreadLocal<HashMap<String,String>> propertyData = new ThreadLocal<HashMap<String,String>>() {
	  @Override
	  protected HashMap<String,String> initialValue() {
		  return new HashMap<>();
	  }
	};
	  
	BT_UkbOnlineRunnerTest(){
		excelFileVar = new ReadAndWriteExcelFile();
		propFileVar = new ReadAndWritePropertyFile();
	}
	
	@BeforeSuite
	public void runBeforeSuite() throws IOException {
		deleteAndMakeDir();
		String dockerRun = System.getProperty("runOnDockerGrid");
		if(dockerRun.equalsIgnoreCase("Yes")) {
			CommonMethods.killTerminalCommand();
			CommonMethods.runTerminalCommand();
		}
		
		String jenRun = System.getProperty("runThroughJenkins");
		
		if(jenRun.equalsIgnoreCase("yes")) {
			System.out.println("run through jenkins");
			//ReadAndWritePropertyFile.setPropertyValue("Constant values","Browser", "System.getProperty(browsersType)");
			ReadAndWritePropertyFile.setPropertyValue("ConstantValues","Browser", System.getProperty("browsersType"));
			ReadAndWritePropertyFile.setPropertyValue("ConstantValues","Environment", System.getProperty("environment"));
			ReadAndWritePropertyFile.setPropertyValue("ConstantValues","TestCaseNumbers", System.getProperty("tcNumbers"));
			ReadAndWritePropertyFile.setPropertyValue("ConstantValues","RunAPIs", System.getProperty("apisRun"));
					
		}	
	}
	
	@Test
	@Parameters({"environment","browsersType","tcNumbers","apisRun"})
	public void runTestCase(String environment,String browsersType,String tcNumbers,String apisRun) throws IOException, InvalidFormatException {
		System.out.println("hai");
		propertyData.get().put("environment",environment);
		propertyData.get().put("browsersType",browsersType);
		propertyData.get().put("tcNumbers",tcNumbers);
		propertyData.get().put("apisRun",apisRun);
		
		
		List<Integer> inte = excelFileVar.splitTestNumbers();
		for(int j: inte) {
			
			System.out.println("Test case :::::::" + j);
			String bddDataInfo = excelFileVar.getDataFromSelectedRowColumn("testdata", environment, j, 5);
			Map<String,String> bddData= excelFileVar.splitCellData(bddDataInfo);
			String testDataInfo = excelFileVar.getDataFromSelectedRowColumn("testdata", environment, j, 4);
			testData = excelFileVar.splitCellData(testDataInfo);
			ClassLoader classLoader = this.getClass().getClassLoader();
			ResourceLoader resourceLoader=new MultiLoader(classLoader);
			RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(getClass());
			RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();
			runtimeOptions.getGlue().clear();
			runtimeOptions.getGlue().add(bddData.get("glue"));
			//runtimeOptions.getGlue().add("stepdefinition");
			
			runtimeOptions.getFeaturePaths().clear();
			runtimeOptions.getFeaturePaths().add(bddData.get("featurefilepath"));
			//runtimeOptions.getFeaturePaths().add("src\\test\\resources\\functionalTest");
			
			tagNameOfSce=bddData.get("tagname");
			runtimeOptions.getFilters().clear();
			runtimeOptions.getFilters().add(bddData.get("tagname"));
			//runtimeOptions.getFilters().add("@test");
			ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
			cucumber.runtime.Runtime runtime = new cucumber.runtime.Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
			try {
				runtime.run();
				ReadAndWriteExcelFile.dataMap.clear();
				bddData.clear();
				//WebDriverManager.driver.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		
		}
	}
	
	@AfterSuite
	public void runAfterSuite() {
		
		String prjPath = System.getProperty("user.dir");
		String pathToReportsFol = prjPath + "\\Reports\\report.html";
		
		
		try {
			TriggerMail.sendreport(pathToReportsFol);
			System.out.println("==================> Successfully send Mail to receipients");
		} catch ( Exception e) {
			System.out.println("==================> Failed to send Mail"+ e.getMessage());
		}
		
		try {
			int totalSce = TriggerMail.listOfFailedTC.size() + TriggerMail.listOfPassedTC.size();
     		TriggerMail.listOfFailedTC.clear();
     		TriggerMail.listOfPassedTC.clear();
		} catch(Exception e) {
			System.out.println("Message not sent through slack");
			System.out.println(e.getMessage());
			TriggerMail.listOfFailedTC.clear();
     		TriggerMail.listOfPassedTC.clear();
		}
		
	}
	
	
	public void deleteAndMakeDir() throws IOException {
		String pathToDel = System.getProperty("user.dir") + "//Reports//FailedScreenshots";
		FileUtils.deleteDirectory(new File(pathToDel));
		String path = System.getProperty("user.dir")+"//Reports";
		File index = new File(path);
		if(!index.exists())
		{
			index.mkdir();
		
		} else {
			index.delete();
			if(!index.exists()) {
				index.mkdir();
			}
		}
		new File(path + "//FailedScreenshots").mkdirs();
	}
	
}
	
	
