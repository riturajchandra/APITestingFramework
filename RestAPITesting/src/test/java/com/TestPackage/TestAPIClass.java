package com.TestPackage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestAPIClass {
	int statusCode = 0;
	Response apiResponse ;

	/**
	 * Validation Of ResponseStatusCode
	 */	
	@Test(priority = 0)
	public void validateResponseCode() {
		String apiUrl = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";
		apiResponse = RestAssured.get(apiUrl);
		statusCode = apiResponse.getStatusCode();
		System.out.println("The Response Status Code is: "+statusCode);
		Assert.assertEquals(statusCode,200);
	}

	/**
	 * Validation Of Name
	 */
	@Test(priority = 1)
	public void validateName() {
		String responseT = apiResponse.asString();
		JSONObject obj = new JSONObject(responseT);
		Assert.assertEquals(obj.getString("Name"), "Carbon credits");
		System.out.println("Name is: "+obj.getString("Name"));
	}

	/**
	 * Validation Of CanRelist
	 */
	@Test(priority = 2)
	public void validateCanRelist() {
		String responseT = apiResponse.asString();
		JSONObject obj = new JSONObject(responseT);
		boolean nodeCanRelist = obj.getBoolean("CanRelist");
		Assert.assertEquals(true, nodeCanRelist);
	}

	/**
	 * Validation Of Description
	 */
	@Test(priority = 3)
	public void validateDescription() {
		String responseT = apiResponse.asString();
		String name ="";
		String description ="";
		JSONObject obj = new JSONObject(responseT);
		JSONArray promotionsArray = obj.getJSONArray("Promotions");
		System.out.println("Promotions Array Size "+promotionsArray.length());
		for(int i=0;i<promotionsArray.length();i++) {
			JSONObject jb = promotionsArray.getJSONObject(i);
			name = jb.getString("Name");
			description = jb.getString("Description");
			if(name.equals("Gallery")) {
				System.out.println(name+"    "+description);
				Assert.assertEquals(true, description.contains("2x larger image"));
			}
		}
	}
}
