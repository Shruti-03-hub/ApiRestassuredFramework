package com.qa.api.schema.test;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GorestUserSchemaTest extends BaseTest {
	
	@BeforeMethod
	public void accessToken()
	{
		ConfigManager.set("bearertoken", "a8290af37229cda22cbe012d9214d84398b21cb3a022824dc9304e0ee10403ae");

	}
	@Test
	public void getUserApiSchemaTest()
	{
		Response response=restClient.get(BASE_URL_GOREST, GOREST_POST_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getRestSchema.json"));
		
	}
	
	@Test
	public void createUserApiSchemaTest()
	{
		User user=User.builder()
			.name("priyanshi")
			.email(StringUtils.getRamdomEmail())
			.gender("female")
			.status("active")
			.build();
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,null, null,user, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/CreateUser.json"));
		
	}
	

}
