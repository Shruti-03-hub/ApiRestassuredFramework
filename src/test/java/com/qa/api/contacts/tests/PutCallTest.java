package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;
import com.qa.api.pojo.ContactsPostRequest;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PutCallTest extends BaseTest{
	private String TokenId;
	@BeforeClass
	public void getToken()
	{
		ContactsCredentials cred=ContactsCredentials.builder()
							.email("shruti.mishra.qa@gmail.com")
							.password("shruti123@")
							.build();
		Response resonse=restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, null, null, cred, AuthType.NO_AUTH, ContentType.JSON);
		TokenId=resonse.jsonPath().getString("token");
		System.out.println("Token Id is ===>"+TokenId);
		ConfigManager.set("bearertoken", TokenId);
	}
	
	@Test
	public void updateContactDetailTest()
	{
		//1 . Create contact
		String generateEmail=StringUtils.getRamdomEmail();
		ContactsPostRequest contactData=ContactsPostRequest.builder()
							.firstName("Mira")
							.lastName("Mishra")
							.birthdate("2001-04-09")
							.email(generateEmail)
							.street1("1 building")
							.street2("gate 2")
							.city("Delhi")
							.stateProvince("Delhi")
							.phone("9876543212")
							.postalCode("987654")
							.country("India")
							.build();
						
		Response responsePost=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, contactData, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePost.jsonPath().getString("firstName"), "Mira");
		Assert.assertEquals(responsePost.jsonPath().getString("lastName"), "Mishra");
		Assert.assertTrue(responsePost.statusLine().contains("Created"));
		Assert.assertEquals(responsePost.jsonPath().getString("email"), generateEmail);
		
		String contactId=responsePost.jsonPath().getString("_id");
		System.out.println("Contact ID is :" +contactId);
	
		//2. get contact details
		
	Response responseGet=restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+contactId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	Assert.assertEquals(responseGet.statusLine(),"HTTP/1.1 200 OK");
		

		//3. Update Contact
	
		//String generateEmail=StringUtils.getRamdomEmail();
		contactData.setFirstName("Jiya");
		
		contactData.setLastName("Mishra");
		contactData.setBirthdate("2001-04-09");
		contactData.setStreet1("1 building");
		contactData.setStreet2("gate 2");
		contactData.setCity("Kanpur");
		contactData.setStateProvince("Delhi");
		contactData.setPhone("9876543212");
		contactData.setPostalCode("987654");
		contactData.setCountry("India");
							
						
		Response responsePut=restClient.put(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+contactId, null, null, contactData, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePut.jsonPath().getString("firstName"), "Jiya");
		Assert.assertEquals(responsePut.jsonPath().getString("lastName"), "Mishra");
		Assert.assertTrue(responsePut.statusLine().contains("OK"));
		
		
		String contactIdput=responsePut.jsonPath().getString("_id");
		System.out.println("Contact ID is :" +contactIdput);
		
		
		responseGet=restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+contactId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.statusLine(),"HTTP/1.1 200 OK");
		}
}
