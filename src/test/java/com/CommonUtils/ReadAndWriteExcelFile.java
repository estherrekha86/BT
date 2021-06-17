package com.CommonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.testRunners.BT_UkbOnlineRunnerTest;

public class ReadAndWriteExcelFile {
	public static Map<String,String> dataMap;
	
	public ReadAndWriteExcelFile() {
		//propFileVar = new ReadAndWritePropertyFile();
	}
	
	public Object[] readDataFromExcel(String fileName,String sheetName) throws InvalidFormatException, IOException
	{
	
	  FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"//src/test//resources//TestData//"+ fileName+ ".xls");
	  Workbook wb = WorkbookFactory.create(file);
	  
	  Sheet sh = wb.getSheet(sheetName);
	  
	 Object[][] data = new Object[sh.getLastRowNum()][sh.getRow(0).getLastCellNum()];
	  for (int i = 0; i < sh.getLastRowNum(); i++) {
	   for (int k= 0; k < sh.getRow(0).getLastCellNum(); k++) {
	    data[i][k] = sh.getRow(i+1).getCell(k).toString();
	    System.out.print(data[i][k]);
	   }
	  }
	  return data;
	 }
	
	
	
	public String getDataFromSelectedRowColumn(String fileName,String sheetName, int rowNum, int colNum) throws IOException, InvalidFormatException
	{
		String data = null;
		FileInputStream fls = new FileInputStream(System.getProperty("user.dir")+"//src/test//resources//TestData//"+ fileName+ ".xls");
		 Workbook wb = WorkbookFactory.create(fls);
		 Sheet sh = wb.getSheet(sheetName);
		 System.out.println(sh.getLastRowNum());
		 for(int i=0; i<sh.getLastRowNum(); i++) {
			 String a= sh.getRow(i+1).getCell(0).getStringCellValue();
			 int b = Integer.parseInt(a);
			 if(rowNum==b) {
				 Row row=sh.getRow(i+1);
				 String data1 = row.getCell(colNum).getStringCellValue();
				 data = data1;			 
			 }		 
		 }	 
		return data;
		
	}
	
	public String setDataToSelectedRowColumn(String fileName, String sheetName, int rowNum, int colNum, String status) throws InvalidFormatException, IOException
	{
		
		FileInputStream fls = new FileInputStream(System.getProperty("user.dir")+"//src/test//resources//TestData//"+ fileName+".xls");
		  Workbook wb = WorkbookFactory.create(fls);
		  Sheet sh = wb.getSheet(sheetName);
		  Row row = sh.getRow(rowNum);
		  Cell cel = row.createCell(colNum);
		  FileOutputStream fos = new FileOutputStream("");
		  cel.setCellValue(status);
		  wb.write(fos);
		  return status;
		
	}
	
	
	public Map<String,String> splitCellData(String cellData){
		dataMap = new HashMap<String, String>();
		String[] cellDataSplitArr = cellData.split("\n");
		for(String cellAtData : cellDataSplitArr) {
			String[] colonSplitArr = cellAtData.split(":"); 
			if(colonSplitArr.length>1) {
				dataMap.put(colonSplitArr[0].toLowerCase(), colonSplitArr[1]);
			}
		}
		return dataMap;
	}
	
	public List<Integer> splitTestNumbers() throws IOException {
		
		String testCaseNos = BT_UkbOnlineRunnerTest.propertyData.get().get("tcNumbers");
		List<Integer> tcNoList = new ArrayList<Integer>();
		if(testCaseNos.contains(",")) {
			String[] testNumbersArray = testCaseNos.split(",");
			if(testNumbersArray.length >1) {
				for( int i = 0; i < testNumbersArray.length;i++) {
					if(testNumbersArray[i].contains("-")) {
						String[] iArray = testNumbersArray[i].split("-");
						for(int j= Integer.parseInt(iArray[0]);j<=Integer.parseInt(iArray[iArray.length-1]); j++) {
							tcNoList.add(j);
						
						}
							
					} else {
						tcNoList.add(Integer.parseInt(testNumbersArray[i]));
						
					}
				}
			}
			} else if(testCaseNos.contains("-")){
				String[] iArray = testCaseNos.split("-");
				for(int j= Integer.parseInt(iArray[0]);j<=Integer.parseInt(iArray[iArray.length-1]); j++) {
					tcNoList.add(j);
				
				}
				
			}  if(!testCaseNos.contains("-") && !testCaseNos.contains(",")) {
				tcNoList.add(Integer.parseInt(testCaseNos));
			}
			
		return tcNoList ;
		}
		
	
	
}

