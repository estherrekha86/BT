package com.CommonUtils;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiDemos {
	@Test
	public void test() {
		
		
		RestAssured.get("https://reqres.in/api/users?page=2").then().assertThat().statusCode(200);
		
		
		RestAssured.useRelaxedHTTPSValidation();
		RestClient.addFormParameters("authenticate", "btone");
		RestClient.addFormParameters("username", "btone");
		RestClient.addFormParameters("password", "btone");
		RestClient.addFormParameters("udid", "btone");
		RestClient.addFormParameters("client_id", "btone");
		RestClient.addFormParameters("client_secret", "btone");
		RestClient.addFormParameters("connection", "btone");
		RestClient.addFormParameters("make", "btone");
		RestClient.addFormParameters("model", "btone");
		RestClient.addFormParameters("os", "btone");
		RestClient.addFormParameters("osv", "btone");
		RestClient.addFormParameters("smsession", "btone");
	    Response response1 = RestClient.hitRequestWithUrlAndCatchResponse("POST", "https://sipg-test.secure.btintra.com/authentication/oauth", "URLENC", "", false, true);
	    System.out.println("Status Code is "+ RestClient.getStatusCode(response1));
	    
	    String serviceid ="789987";
	    System.out.println(RestClient.getValueFromJsonReponse(response1, "productInventoryManagement[0].billingAcccounts[0].products.findAll{it.id=='"+serviceid+"'}.serviceLineType"));
	    
	    RestAssured.useRelaxedHTTPSValidation();
	    RestAssured.proxy("10.55.219.73",8080);
		RestClient.addHeaders("authenticate", "btone");
	    Response response2 = RestClient.hitRequestWithUrlAndCatchResponse("GET", "https://sipg-test.secure.btintra.com/authentication/oauth", "JSON", "", false, true);
	    System.out.println("Status Code is "+ RestClient.getStatusCode(response2));
	    
		
	    
	}

}
