package com.qa.api.reqres;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetCallTests extends BaseTest{

	@Test
	public void getAllUsers() {
		Map<String,String> queryParam=new HashMap<>();
		queryParam.put("page", "2");
		Response response=restClient.get(BASE_URL_REQRES, REQRES_ENDPOINT, queryParam, null, AuthType.NO_AUTH, ContentType.ANY);
		response.prettyPrint();
		String Ids=response.jsonPath().getString("data.id");
		System.out.println("Ids are : "+Ids);
	}
}
