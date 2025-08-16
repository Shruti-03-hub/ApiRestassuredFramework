package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPTTestWithJsonPath extends BaseTest{
	
	@Test
	public void getProductTest()
	{
		Response response=restClient.get(BASE_URL_PRODUCT, PRODUCT_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		List<Number> prices=JsonPathValidatorUtil.readList(response, "$.[*].price");
		System.out.println(prices.size());
		System.out.println(prices);
		
		List<Map<String,Object>> idAndTitle=JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','title']");
		System.out.println(idAndTitle.size());
		System.out.println(idAndTitle);
		
		List<Map<String,Object>> idAndTitlePrice=JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','title','price']");
		System.out.println(idAndTitlePrice.size());
		System.out.println(idAndTitlePrice);
		
		List<Number> rates=JsonPathValidatorUtil.readList(response, "$.[*].rating.rate");
		System.out.println(rates.size());
		System.out.println(rates);
		
		Number minPrice=JsonPathValidatorUtil.read(response, "min($[*].price)");
		
		System.out.println(minPrice);
	}

}
