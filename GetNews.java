package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;

public class GetNews extends APINeedTesting{


	public void callAPI(String news) {
		baseURI = BaseURL.BASEURI;
		Response response = 
				given()
					.body(news)
					.contentType("application/json")
				.when()
					.get("api/news");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in GetNews API: Input is numeric value, the code should be 1000 and message is OK:");
		
		//Unit 1
		try {
			IndexCount news = new IndexCount();
			String input_news = news.creRequest("1", "1");
			this.callAPI(input_news);
			
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}
	 }
	
	void test2() {
		System.out.println("Test 2 in GetNews API: Input is None, the code should be 1000 and message is OK");
		
		// Unit 2
		try {
			IndexCount news = new IndexCount();
			String input_news = news.creRequest("", "");
			this.callAPI(input_news);
			
			Assert.assertEquals(this.messageResponse, "OK");
			 System.out.println("Unit 2: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 2: Failed");
		}		
	}
	
	void test3() {
		System.out.println("Test 3 in GetNews API: A non-numeric value encountered (500 Internal Server Error)");
		
		// Unit 3
		try {
			IndexCount news = new IndexCount();
			String input_news = news.creRequest("duong", "duong");
			this.callAPI(input_news);
			System.out.println("Unit 3: Failed");
		} catch (JSONException e) {
			System.out.print("Unit 3: Passed");
			
		}
	}
}
