package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;

public class ReadNews extends APINeedTesting{
	
	public String creRequest(String... request) {
		LogInTest login = new LogInTest();
		String currentAccount = login.creRequest(request[0], request[1]);
		login.callAPI(currentAccount);
		JSONObject data = new JSONObject(login.dataResponse);
		String access_token = data.getString("access_token").toString();
		return access_token;
	
	}

	public void callAPI(String access_token, String new_id) {
		baseURI = BaseURL.BASEURI;
		Response response = 
				given()
					.header("Authorization", "Bearer" + access_token)
				.when()
					.get("api/news/read/" + new_id);
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in ReadNews API: input new_id get value as 1, The code should be 1000 and message is OK:");
		
		//Unit 1
		try {			
			String access_token = this.creRequest(
					"auto@gmail.com"							
					,"123456");

			String new_id = "1"; 
			this.callAPI(access_token, new_id);
			System.out.println(this.messageResponse);
			System.out.println(this.dataResponse);
			
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
			
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}
	 }
	
	void test2() {
		System.out.println("Test 2 in ReadNews API: input new_id get all values except for 1");
		
		//Unit 2
		try {			
			String access_token = this.creRequest(
					"auto@gmail.com"							
					,"123456");
			String new_id = "2"; 
			this.callAPI(access_token, new_id);			
			System.out.println("Unit 2: Failed");
		} catch (JSONException e) {
			System.out.println("Unit 2: Passed, can not get input value except for 1 ");
		}
	 }
}
