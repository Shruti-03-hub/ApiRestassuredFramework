package com.qa.api.circuit.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.XmlPathUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CircuitApiWithXmlTest extends BaseTest{
	
	@Test
	public void getCircuitInfoTest()
	{
		Map<String,String> pathParam=new HashMap<>();
		pathParam.put("year", "2017");
		Response response=restClient.get(BASE_URL_ERGAST_CIRCUIT, ERGAST_CIRCUIT_ENDPOINT, null, pathParam, AuthType.NO_AUTH, ContentType.ANY);
		String urls=XmlPathUtil.read(response, "**.find {it.@circuitId=='americas'}.@url");
		System.out.println("name is : "+urls);
		
		//country
		List<String>country=XmlPathUtil.readList(response, "**.findAll{it.@circuitId=='americas' ||it.@circuitId=='bahrain' }.Location.Locality");
		System.out.println("locality is  : "+country);
		System.out.println("----------------------");
	
	
	
	}

}
