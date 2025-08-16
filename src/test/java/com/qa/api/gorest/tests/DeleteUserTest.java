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

public class DeleteUserTest extends BaseTest {
	
	
	private String tokenId;
	@BeforeClass
	public void setupToken() {
		tokenId="a8290af37229cda22cbe012d9214d84398b21cb3a022824dc9304e0ee10403ae";
		ConfigManager.set("bearertoken", tokenId);
	}

	// For delete flow is create-get-delete-get

	@Test
	public void deleteUserTest() {
		// 1. create a user - Post here we are using builder paatern
		User user = User.builder()
				.name("Riya")
				.email(StringUtils.getRamdomEmail())
				.gender("female").status("active")
				.build();

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, user,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "Riya");
		Assert.assertNotNull(response.jsonPath().getString("id"));

		String userId = response.jsonPath().getString("id");
		System.out.println("User is --->" + userId);

		// 2. get call= fetch user

		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.statusLine(), "HTTP/1.1 200 OK");
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);

		// 3. delete - update the user
		Response responseDelete = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseDelete.statusLine(), "HTTP/1.1 204 No Content");

		// 4. get call= fetch user
		responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.statusCode(), 404);
		Assert.assertEquals(responseGet.statusLine(), "HTTP/1.1 404 Not Found");
		Assert.assertEquals(responseGet.jsonPath().getString("message"), "Resource not found");

	}

}
