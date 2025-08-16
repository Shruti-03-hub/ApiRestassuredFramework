package com.qa.api.client;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {
	
	
	//Define all the Response Specs:
	private ResponseSpecification responseSpec200=expect().statusCode(200);
	private ResponseSpecification responseSpec204=expect().statusCode(204);
	private ResponseSpecification responseSpec201=expect().statusCode(201);
	private ResponseSpecification responseSpec200or201=expect().statusCode(anyOf(equalTo(200),equalTo(201)));
	private ResponseSpecification responseSpec200or404=expect().statusCode(anyOf(equalTo(200),equalTo(404)));
	
	private ResponseSpecification responseSpec200or204=expect().statusCode(anyOf(equalTo(200),equalTo(204)));

	private ResponseSpecification responseSpec404=expect().statusCode(404);
	private ResponseSpecification responseSpec401=expect().statusCode(401);
	private ResponseSpecification responseSpec401or400=expect().statusCode(anyOf(equalTo(401),equalTo(400)));
	private ResponseSpecification responseSpec400=expect().statusCode(400);

	
	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType)
	{
		ChainTestListener.log("API Base Url : "+baseUrl);
		ChainTestListener.log("API Auth Type : "+authType);
		RequestSpecification request=RestAssured.given().log().all()
										.baseUri(baseUrl)
										.contentType(contentType)
										.accept(contentType);
		switch(authType)
		{
		case BEARER_TOKEN:
			request.header("Authorization" ,"Bearer "+ConfigManager.get("bearertoken"));
			break;
		case BASIC_AUTH:
			request.header("Authorization" ,"Basic "+ generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("x-api-key" ,"api key");
			break;
		case NO_AUTH:
			System.out.println("Auth is not Required ....");
			break;
			
		default:
			System.out.println("This Auth is not supported ...Please provide Valid AuthType");
			throw new APIException("===Invalid Authorization===");
		}
	
	return request;
	}
	
	public String generateBasicAuthToken()
	{
		String Creds=ConfigManager.get("basicauthusername")+":"+ConfigManager.get("basicauthpassword");
		return Base64.getEncoder().encodeToString(Creds.getBytes());
	}
	
	private void applyParams(RequestSpecification request,Map<String, String> queryParams,Map<String, String> pathParams)
	{
		if(queryParams!=null)
		{
			request.queryParams(queryParams);
		}
		if(pathParams!=null)
		{
			request.queryParams(pathParams);
		}
	}
	
	//get
	/**
	 * This method is use to call get api
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns get api call response
	 */
	public Response get(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) {
		RequestSpecification request=setupRequest(baseUrl,authType,contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
	}
	
	//Post
	/**
	 * This method is use to call post api
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param body
	 * @param authType
	 * @param contentType
	 * @return it returns post api call response
	 */
	public <T> Response post(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams,
			T body,
			AuthType authType,
			ContentType contentType) 
	{
		RequestSpecification request=setupRequest(baseUrl,authType,contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}
	//overloading post method specifically for OAUTH2
	public Response post(String baseUrl, String endPoint,
			String cleintId,String clientSecret, String grantType,
			ContentType contentType) 
	{
		Response response=RestAssured.given()
					.contentType(contentType)
					.formParam("client_id", cleintId)
					.formParam("client_secret", clientSecret)
					.formParam("grant_type",grantType)
					.when()
						.post(baseUrl+endPoint);
		response.prettyPrint();
		return response;
	}
	
	//overloading post method for jsonFile
	public Response post(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams,
			File file,
			AuthType authType,
			ContentType contentType) 
	{
		RequestSpecification request=setupRequest(baseUrl,authType,contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}
	
	
	
	public <T> Response put(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams,
			T body,
			AuthType authType,
			ContentType contentType) 
	{
		RequestSpecification request=setupRequest(baseUrl,authType,contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}
	
	
	
	public <T> Response patch(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams,
			T body,
			AuthType authType,
			ContentType contentType) 
	{
		RequestSpecification request=setupRequest(baseUrl,authType,contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}
	
	public <T> Response delete(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) 
	{
		RequestSpecification request=setupRequest(baseUrl,authType,contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.delete(endPoint).then().spec(responseSpec200or204).extract().response();
		response.prettyPrint();
		return response;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
