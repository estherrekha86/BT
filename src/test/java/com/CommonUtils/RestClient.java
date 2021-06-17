package com.CommonUtils;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class RestClient {
	
	static Map<String, Object> headerMap = new HashMap<String,Object>();
	static Map<String, Object> formparamMap = new HashMap<String,Object>();
	static Response response;
	
	
	public static Response hitRequestWithUrlAndCatchResponse(String typeOfReq, String url,String contentType, Object obj,boolean bodyThroughFile,boolean log) {
		RequestSpecification request = createRequest(contentType, log);
		
		switch(typeOfReq.toUpperCase()) {
		case "GET":
			response = request.get(url);
			headerMap.clear();
			formparamMap.clear();
			break;
		case "POST":
			if(obj!="") {
				if(bodyThroughFile) {
					request.body(obj.toString());
					
				} else {
					String jsonPayLoad = SerializationUtil.getSerializedJson(obj);
					System.out.println(jsonPayLoad);
					request.body(jsonPayLoad);
				}
			}
			response = request.post(url);
			headerMap.clear();
			formparamMap.clear();
			break;
		case "PUT":
			if(bodyThroughFile) {
				request.body(obj.toString());
			} else {
				String jsonPayLoad = SerializationUtil.getSerializedJson(obj);
				request.body(jsonPayLoad);
				
			}
			response = request.put(url);
			headerMap.clear();
			break;
		case "DELETE":
			headerMap.clear();
			formparamMap.clear();
			response = request.delete(url);
			break;
		default:
			System.out.println("Please PASS correct request");
			break;
		}
	    return response;
	    
			
			
		}
	


	private static RequestSpecification createRequest(String contentType, boolean log) {
		// TODO Auto-generated method stub
		RequestSpecification request_1 = RestAssured.given();
		if(!headerMap.isEmpty()) {
			request_1.headers(headerMap);
			
		}
		
		if(!formparamMap.isEmpty()) {
			request_1.formParams(formparamMap);
		}
		
		if(log) {
			System.out.println("#####Logs#####");
			request_1=request_1.log().all();
			
		}
		
		if(contentType.equalsIgnoreCase("Json")) {
			request_1.contentType(ContentType.JSON);
		}else if(contentType.equalsIgnoreCase("XML")) {
			request_1.contentType(ContentType.XML);
			
		}else if(contentType.equalsIgnoreCase("TEXT")) {
			request_1.contentType(ContentType.TEXT);
			
		}else if(contentType.equalsIgnoreCase("URLENC")) {
			request_1.contentType(ContentType.URLENC.withCharset("UTF-8"));
		}else {
			request_1.contentType(ContentType.URLENC);
		}  
	    return request_1;
		
	}
	
	public static Map<String, Object> addHeaders(String headerKey, String headerVal){
		headerMap.put(headerKey, headerVal);
		return headerMap;
	}
	
	public static Map<String, Object> addFormParameters(String headerKey, String headerVal){
		formparamMap.put(headerKey, headerVal);
		return formparamMap;
	}
	
	public static JSONObject getResponseAsJsonObject(Response response) {
		JSONObject jparentobj  = new JSONObject(response.asString());
		return jparentobj;	
	}
	
	public static String getValueFromJsonReponse(Response response,String value) {
		return response.jsonPath().get(value).toString();
		
	}
	
	public static JsonPath getJsonPath(Response response) {
		return response.jsonPath();
		
	}
	
	public static ArrayList getJsonbodysFromArray(Response response,String variableNameOfArray) {
		return response.jsonPath().get(variableNameOfArray);
		
	}
	
	public static String getValueFromJsonArray(Response response,String propName) {
		
		JSONArray responseJsonArrays = new JSONArray(response.asString());
		
		String value = null;
		for(int i=0;i<responseJsonArrays.length();i++) {
			value =responseJsonArrays.getJSONObject(i).getString(propName);
		}
		return value;
		
	}
	
	public static InputStream getResponseAsInputStream_XML(Response response) {
		return response.asInputStream();
	}

	public static Element getAllElementsFromXml(Response response) throws SAXException, IOException, ParserConfigurationException {
	   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

   
       DocumentBuilder builder = factory.newDocumentBuilder();

      
       Document doc = builder.parse(response.asInputStream());

       // get the first element
       Element element = doc.getDocumentElement();

       // get all child nodes
       NodeList nodes = element.getChildNodes();
       
       Node nxml = nodes.item(0);

      /* // print the text content of each child
       for (int i = 0; i < nodes.getLength(); i++) {
          System.out.println("" + nodes.item(i).getTextContent());
       }*/
       return element;
	}
	
	public static int getStatusCode(Response response) {
		int resCode = response.getStatusCode();
		if(resCode ==200 ) {
			System.out.println("200 response success");
		}else if(resCode ==400 ) {
			System.out.println("400 response bad request");
		}else if(resCode == 404 ) {
			System.out.println("404 response Not found");
		}else if(resCode ==406 ) {
			System.out.println("406 response not acceptible");
		}else if(resCode ==500 ) {
			System.out.println("500 response server error");
		}
		Assert.assertEquals(response.getStatusCode(), 200);
		return resCode;
	}
	
	public static String getHeaderValue(Response response,String headerName) {
		return response.getHeader(headerName);
	}
	
	public static java.util.List<Header> getHeaderCount(Response response) {
		
		Headers headers = response.getHeaders();
		java.util.List<Header> headerList = headers.asList();
		return headerList;	
	}
	public static Object uploadJsonBody(String filePath) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser jsonParser = new JsonParser();
		Object obj = jsonParser.parse(new FileReader(filePath));
		return obj;
	}
	
	public static Object uploadXmlBody(String filePath) throws JsonIOException, JsonSyntaxException, IOException {
		FileInputStream fileInput =new FileInputStream(filePath);
		Object obj= fileInput.read();
		return obj;
	}
	
	
	public static Object uploadFileeAsBody(String filePath) throws JsonIOException, JsonSyntaxException, IOException {
		
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}
	
	/*
	 * public static Object deSerializeTheResponse(Response response,String Obj) {
	 * return response.as(response); }
	 */
	
   
}
