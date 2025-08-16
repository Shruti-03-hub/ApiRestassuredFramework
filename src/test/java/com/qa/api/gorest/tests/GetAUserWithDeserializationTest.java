package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserWithDeserializationTest extends BaseTest {
	
	private String tokenId;
	@BeforeClass
	public void setupToken() {
		tokenId="a8290af37229cda22cbe012d9214d84398b21cb3a022824dc9304e0ee10403ae";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void createUserTest()
	{
		
		User user=new User(null,"Rama",StringUtils.getRamdomEmail(),"male","active"); 
		
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"Rama");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userid=response.jsonPath().getString("id");
		System.out.println("User id is : "+userid);
		
		Response resGet=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userid, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
	
		User jsonUser=JsonUtils.deserialize(resGet, User.class);
		
		System.out.println("User ID : "+jsonUser.getId());
		System.out.println("User Email : "+jsonUser.getEmail());
		System.out.println("User Name : "+jsonUser.getName());
		System.out.println("User Status : "+jsonUser.getStatus());
		System.out.println("User Gender : "+jsonUser.getGender());

	}
	

}
