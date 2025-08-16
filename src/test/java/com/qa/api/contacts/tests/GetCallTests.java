package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetCallTests extends BaseTest{
	
	private static String tokenID;
	@BeforeClass
	public void getToken()
	{
		ContactsCredentials creds=ContactsCredentials.builder()
							.email("shruti.mishra.qa@gmail.com")
							.password("shruti123@")
							.build();
		
		Response response=restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, null, null, creds, AuthType.NO_AUTH, ContentType.JSON);
		tokenID= response.jsonPath().getString("token");
		System.out.println("Contacts Api Login Token --->"+tokenID);
		ConfigManager.set("bearertoken", tokenID);
	}
	
	@Test
	public void getAllContactsTest()
	{
		Response response=restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusLine(),"HTTP/1.1 200 OK");
	}

}
