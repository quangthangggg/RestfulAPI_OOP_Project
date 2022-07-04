package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;
import java.util.Random;

public class ReadNotifications extends APINeedTesting{
	
	public String creRequest(String... request) {
		LogInTest login = new LogInTest();
		String currentAccount = login.creRequest(request[0], request[1]);
		login.callAPI(currentAccount);
		JSONObject data = new JSONObject(login.dataResponse);
		String access_token = data.getString("access_token").toString();
		return access_token;
	
	}

	public void callAPI(String access_token, String auction_deny_id) {
		baseURI = BaseURL.BASEURI;
		Response response = 
				given()
					.header("Authorization", "Bearer" + access_token)
				.when()
					.get("api/notifications/read/" + auction_deny_id);
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in ReadNotifications API: input auction_deny_id get numeric random value from 1 to 432");
		
		//Unit 1
		Random rand = new Random();
		Integer auction_deny_id = rand.nextInt(432);
		try {			
			String access_token = this.creRequest(
					"auto@gmail.com"							
					,"123456");			
			this.callAPI(access_token, auction_deny_id.toString() );
			System.out.println("auction_deny_id: " + auction_deny_id);
			
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
			
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		} catch (JSONException j) {
			System.out.println("Unit 1: auction_deny_id is not exist");
		}
			
	 }
	
	void test2() {
		System.out.println("Test 2 in ReadNotifications API: input new_id get non-numeric value");
		
		//Unit 2
		try {			
			String access_token = this.creRequest(
					"auto@gmail.com"							
					,"123456");
			String new_id = "duong"; 
			this.callAPI(access_token, new_id);			
			System.out.println("Unit 2: Failed");
		} catch (JSONException e) {
			System.out.println("Unit 2: Passed, can not get input non-numeric value");
		}
	}
	
	void test3() {
		System.out.println("Test 3 in ReadNotifications API: input new_id get null value");
		try {			
			String access_token = this.creRequest(
					"auto@gmail.com"							
					,"123456");
			String new_id = ""; 
			this.callAPI(access_token, new_id);			
			System.out.println("Unit 3: Failed");
		} catch (JSONException e) {
			System.out.println("Unit 3: Passed, can not get input null value");
		}
	}
	
}
