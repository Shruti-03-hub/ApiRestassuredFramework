package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ProductLombok;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest {

	@Test
	public void getProductTest()
	{
		Response response=restClient.get(BASE_URL_PRODUCT, PRODUCT_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		ProductLombok[] product=JsonUtils.deserialize(response, ProductLombok[].class);
		for(ProductLombok p:product)
		{
			System.out.println("Id: "+p.getId());
			System.out.println("title: "+p.getTitle());
			System.out.println("Price: "+p.getPrice());
			System.out.println("Description: "+p.getDescription());
			System.out.println("Category: "+p.getCategory());
			System.out.println("Image: "+p.getImage());
			System.out.println("Rate: "+p.getRating().getRate());
			System.out.println("Count: "+p.getRating().getCount());
		}
	}
}
