package com.qa.api.gorest.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppContacts;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Epic("Epic 100: GoRest Create Api Feature")
@Story("USPB 30012: Create User Feature")
@Owner("Shruti Mishra")
@Severity(SeverityLevel.CRITICAL)

public class CreateUserTest extends BaseTest{
	private String tokenId;
	@BeforeClass
	public void setupToken() {
		tokenId="a8290af37229cda22cbe012d9214d84398b21cb3a022824dc9304e0ee10403ae";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@DataProvider
	public Object[][] getUserData()
	{
		return new Object[][]{
			{"Shri","male","active"},
			{"Riya", "female", "inactive"},
			{"John","male","active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserExcelData()
	{
		return ExcelUtil.readData(AppContacts.CREATE_USER_SHEET_NAME);
	}
	@Description("Create User API Test")
	@Test(dataProvider="getUserExcelData")
	public void createUserTest(String name, String gender,String status)
	{
		
		User user=new User(null,name,StringUtils.getRamdomEmail(),gender,status); 
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),name);
		Assert.assertEquals(response.jsonPath().getString("gender"),gender);
		Assert.assertEquals(response.jsonPath().getString("status"),status);
		
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createAUserWithJsonStringTest()
	{
		String emailId=StringUtils.getRamdomEmail();
		String user="{\n"
				+ "\"name\": \"Cora\",\r\n"
				+ "\"email\": \""+emailId+"\",\r\n"
				+ "\"gender\": \"female\",\r\n"
				+ "\"status\": \"active\"\r\n"
				+ "}";
		
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"Cora");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createAUserWithJsonFileTest() throws IOException
	{
		
		String emailId=StringUtils.getRamdomEmail();
		
		//create a string object to store json file as string
		String rawjson=new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/User.json")));
		
		String updateBody=rawjson.replace("{{email}}", emailId);
		
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, updateBody, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"Cora");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}

}
