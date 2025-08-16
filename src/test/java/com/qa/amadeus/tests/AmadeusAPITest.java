package com.qa.amadeus.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{
	private String accessToken;
	@BeforeMethod
	public void getOAuth2Token()
	{
		Response response=restClient.post(BASE_URL_OAUTH2_AMADEUS, AMADEUS_OAUTH2_ENDPOINT, 
				ConfigManager.get("clientidamadeus"),ConfigManager.get("clientsecretamadeus") , ConfigManager.get("granttypeamadeus"), ContentType.URLENC);
		
		accessToken=response.jsonPath().getString("access_token");
		System.out.println("Access Token is : "+accessToken);
		ConfigManager.set("bearertoken", accessToken);
	
	}
	
	@Test
	public void getFlightDetailsTest()
	{
		Map<String,String> queryParam=new HashMap<>();
		queryParam.put("origin", "PAR");
		queryParam.put("maxPrice", "200");
		Response response=restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_DESTINATION_ENDPOINT, queryParam, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		response.prettyPrint();
	}
}
