package com.CommonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.mail.MultiPartEmail;

public class TriggerMail {
	
	
	public static String envNameSel;
	public static ArrayList<String> listOfFailedTC = new ArrayList<String>();
	public static ArrayList<String> listOfPassedTC = new ArrayList<String>();
	
	public static void sendreport(String reportpath) throws Exception {
		envNameSel = ReadAndWritePropertyFile.getPropertyValue("ConstantValues", "Environment");
		String browser= ReadAndWritePropertyFile.getPropertyValue("ConstantValues", "Browser");
		String userMail = "rekhaesther10@gmail.com";
		String [] emailList = {userMail};
		String jenRun = System.getProperty("runThroughJenkins");
		String mailSubject = System.getenv("JOB_NAME");
		if((jenRun != null) && jenRun.equalsIgnoreCase("Yes")){
			emailList = new String [] {userMail};
			compressedToZip();
		} else {
			emailList = new String[] {userMail};
			compressedToZip();
		}
	    String list = listOfFailedTCNames();
	    String failedList = "";
	    if(TriggerMail.listOfFailedTC.size()>0) {
	    	failedList = "List of failed cases: \n"+ list;
	    }
	    int numberOfSceExecuted = TriggerMail.listOfFailedTC.size() + TriggerMail.listOfPassedTC.size();
	    String str = "Hi Team, \n"
	    		+ "Please find below for the summary on automation report, \n\n"
	    		+"Environment:"  + envNameSel + "\n"
	    		+ " Browser:" + browser + "\n"
	    		+ "Total No of Test Cases Executed"  + numberOfSceExecuted+"\n"
	    		+ "Test Cases Passed: " + TriggerMail.listOfPassedTC.size()+"\n"
	    		+ "Test Cases Failed: " + TriggerMail.listOfFailedTC.size()+"\n\n"
	    		+ failedList + " \n"
	    		+ "Note: PFA for in deatils report. \n"
	    		+ "Regards, \n"
	    		+ "Saas Team. \n \n"
	    		+ " **** THIS IS AN AUTO GENERATED MAIL. KINDLY DO NOT REPLY TO THIS MAIL ***  ";
	    System.out.println(str);
	    Thread.sleep(4000);
	    
//	 // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
	    
	    
	    File report = new File(reportpath);
	    MultiPartEmail email = new MultiPartEmail();
	    //email.setHostName("smtp.intra.bt.com");
	    email.setHostName("smtp.gmail.com");
	    //email.setSmtpPort(25);
	    email.setSmtpPort(465);
	    email.setAuthentication("rekhaesther10@gmail.com", "Jesussavesu86$");
	    email.setSSLOnConnect(true);
	    //email.setFrom("no-reply@bt.com");
	    email.setFrom("rekhaesther10@gmail.com");
	    email.addTo(emailList);
	    email.setSubject(envNameSel + " - New Dashboard Autoamtion Report");
	    email.setMsg(str);
	    //email.attach(report);
	    //email.attach(new File(System.getProperty("user.dir")+ "//Reports//"+"Reports.zip"));
	    email.send();
	}
	
	public static String listOfFailedTCNames() {
		
	    String output= "";
	    for(int i=0; i<TriggerMail.listOfFailedTC.size();i++) {
	    	output += TriggerMail.listOfFailedTC.get(i)+"\n";
	    }
	    return output;
		
	}
	
	
	public static void compressedToZip() throws IOException {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		
		fileWriter = new FileOutputStream(System.getProperty("user.dir")+ "//Report.zip");
		//fileWriter = new FileOutputStream("C://Users//rekha//Downloads//Report.zip");
		zip = new ZipOutputStream(fileWriter);
		String userDir = System.getProperty("user.dir");
		addFolderToZip("", userDir+ "//Reports//", zip);
		zip.flush();
		zip.close();
		
	}
	
	
	@SuppressWarnings("resource")
	static private void addFileToZip(String path,String srcFile,ZipOutputStream zip) throws IOException
	{
		File folder = new File(srcFile);
		if(folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
			
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while((len = in.read(buf))>0) {
				zip.write(buf, 0, len);
			}
		}
		}
	
	
	
	static private void  addFolderToZip(String path,String srcFolder,ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);
		for(String fileName: folder.list()) {
			if(path.equals("")) {
				addFileToZip(folder.getName(),srcFolder + "/"+ fileName, zip);
			} else {
				addFileToZip(path+ "/"+ folder.getName(),srcFolder + "/"+ fileName, zip);
			}
		}
	}
}
