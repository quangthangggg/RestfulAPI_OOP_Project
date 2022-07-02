package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;

public class GetListCategories extends APINeedTesting{

	public String creRequest(String... request) {
		return null;
	}
	
	public void callAPI() {
		baseURI = BaseURL.BASEURI;
		Response response = 
				given()
				.when()
					.get("api/categories");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in GetListCategories API: The code should be 1000 and message is OK:");
		
		//Unit 1
		try {
			this.callAPI();
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}

	 }
		
}
