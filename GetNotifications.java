package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;

public class GetNotifications extends APINeedTesting{
	
	public String creRequest1(String... request) {
		LogInTest login = new LogInTest();
		String currentAccount = login.creRequest(request[0], request[1]);
		login.callAPI(currentAccount);
		JSONObject data = new JSONObject(login.dataResponse);
		String access_token = data.getString("access_token").toString();
		return access_token;
	
	}
	
	public String creRequest2(String... request) {
		JSONObject req = new JSONObject();
		req.put("index", request[0]);
		req.put("count", request[1]);
		req.put("is_not_read", request[2]);
		return req.toString();
	
	}
	
	
	public void callAPI(String access_token, String input_notifications) {
		baseURI = BaseURL.BASEURI;
		Response response = 
				given()
					.header("Authorization", "Bearer" + access_token)
					.body(input_notifications)
					.contentType("application/json")
				.when()
					.get("api/notifications");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in GetNotifications API: Input get a numeric value, code should be 1000 and message is OK:");
		
		//Unit 1
		try {			
			String access_token = this.creRequest1(
					"auto@gmail.com"							
					,"123456");
			
			String input_notifications = this.creRequest2("1", "1", "");
			this.callAPI(access_token, input_notifications);
			
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
			
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}
	 }
	
	void test2() {
		System.out.println("Test 2 in GetNotifications API: Input get a non-numeric value encountered (500 Internal Server Error)");
		
		//Unit 2
		try {			
			String access_token = this.creRequest1(
					"auto@gmail.com"							
					,"123456");
			
			String input_notifications = this.creRequest2("x", "y", "");
			this.callAPI(access_token, input_notifications);			
	        System.out.println("Unit 2: Failed");
		} catch (JSONException e) {
			System.out.println("Unit 2: Passed, can not get input non-numeric value");
		}
	 }
	
	void test3() {
		System.out.println("Test 3 in GetNotifications API: Input get null value, code should be 1000 and message is OK:");
		
		//Unit 3
		try {			
			String access_token = this.creRequest1(
					"auto@gmail.com"							
					,"123456");
			
			String input_notifications = this.creRequest2("", "", "");			
			this.callAPI(access_token, input_notifications);
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
	        System.out.println("Unit 3: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 3: Failed");
		}
	 }
}
