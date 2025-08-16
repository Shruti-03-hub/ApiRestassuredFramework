package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest{
	
	private String tokenId;
	@BeforeClass
	public void setupToken() {
		tokenId="a8290af37229cda22cbe012d9214d84398b21cb3a022824dc9304e0ee10403ae";
		ConfigManager.set("bearertoken", tokenId);
	}
	@Test
	public void getAllUserTest()
	{
	
		Response response=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
		//2nd way
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test
	public void getAllUserWithQueryParamTest()
	{
		Map<String, String> queryParams=new HashMap<String, String>();
		queryParams.put("name", "Dr. Meena Achari");
		queryParams.put("status", "inactive");
		
		Response response=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
		
	}
	
	@Test
	public void getSingleUserTest()
	
	{
		User user=User.builder()
				.name("Riya")
				.email(StringUtils.getRamdomEmail())
				.gender("female")
				.status("active")
				.build();
			
			Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(response.jsonPath().getString("name"),"Riya");
			Assert.assertNotNull(response.jsonPath().getString("id"));
			
			String userId=response.jsonPath().getString("id");
			System.out.println("User is --->"+userId);
		
		Response responseGet=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.statusLine(), "HTTP/1.1 200 OK");
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
	}

}
