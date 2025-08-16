package com.qa.api.gorest.tests;

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

public class UpdateUserTest extends BaseTest {
	
	private String tokenId;
	@BeforeClass
	public void setupToken() {
		tokenId="a8290af37229cda22cbe012d9214d84398b21cb3a022824dc9304e0ee10403ae";
		ConfigManager.set("bearertoken", tokenId);
	}

	//For put flow is create-get-update-get
	
	@Test
	public void updateUserTest()
	{
		//1. create a user - Post here we are using builder paatern
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
		
		//2. get call= fetch user
		
			Response responseGet=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseGet.statusLine(), "HTTP/1.1 200 OK");
			Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
			
		//3. Put - update the user
			
			user.setName("Priya");
			user.setStatus("inactive");
			Response responsePut=restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responsePut.statusLine(), "HTTP/1.1 200 OK");
			Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
			Assert.assertEquals(responsePut.jsonPath().getString("name"),"Priya");
			Assert.assertEquals(responsePut.jsonPath().getString("status"),"inactive");

			
		//4. get call= fetch user
			
			 responseGet=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseGet.statusLine(), "HTTP/1.1 200 OK");
			Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
			Assert.assertEquals(responseGet.jsonPath().getString("name"),"Priya");
			Assert.assertEquals(responseGet.jsonPath().getString("status"),"inactive");


	}
}
