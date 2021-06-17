package com.CommonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.portable.OutputStream;

public class ReadAndWritePropertyFile {
	
	static Properties prop = new Properties();
	static String pathToPropFile = System.getProperty("user.dir")+ "//src/test//resources//PropertyFiles//";
	
	public static String getPropertyValue(String fileName, String propName) throws IOException
	{
		FileInputStream input = new FileInputStream(pathToPropFile+fileName+".properties");
		prop.load(input);
		String propVal = prop.getProperty(propName);
		return propVal;
	}
	
	public static void setPropertyValue(String fileName, String propName, String propVal) throws IOException
	{
		FileInputStream input = new FileInputStream(pathToPropFile + fileName + ".properties");
		prop.load(input);
		input.close();
		
		FileOutputStream output = new FileOutputStream(pathToPropFile+ fileName+ ".properties");
		prop.setProperty(propName, propVal);
		prop.store(output, null);
		output.close();
	}

}
