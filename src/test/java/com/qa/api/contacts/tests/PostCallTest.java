package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;
import com.qa.api.pojo.ContactsPostRequest;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostCallTest extends BaseTest {
	
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
	public void createContactUserTest()
	{
		String ReqEmail=StringUtils.getRamdomEmail();
		ContactsPostRequest userData=ContactsPostRequest.builder()
				.firstName("Andy")
				.lastName("America")
				.email(ReqEmail)
				.phone("9876543456")
				.birthdate("1970-01-01")
				.street1("1 building")
				.street2("landmark")
				.city("Kanpur")
				.stateProvince("Uttar Pradesh")
				.postalCode("209861")
				.country("India")
				.build();
		
		
		Response response=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, userData, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("firstName"), "Andy");
		Assert.assertEquals(response.jsonPath().getString("lastName"), "America");
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertEquals(response.jsonPath().getString("email"), ReqEmail);
		
		System.out.println("id is:"+response.jsonPath().getString("_id"));
	
	
	}
}
