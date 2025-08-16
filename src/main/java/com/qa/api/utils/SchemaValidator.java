package com.qa.api.utils;

import io.restassured.response.Response;
import  io.restassured.module.jsv.JsonSchemaValidator;
public class SchemaValidator {
	
	public static boolean validateSchema(Response response, String schemaFileName)
	{
		try {
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFileName));
			System.out.println("Schema valiadtion is passed for : "+schemaFileName);
		return true;
		}catch(Exception e)
		{
			System.out.println("Schema valiadtion is failed: "+e.getMessage());
			return false;
		}

}}
