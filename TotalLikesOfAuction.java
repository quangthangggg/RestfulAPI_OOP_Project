package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import io.restassured.response.Response;

public class TotalLikesOfAuction extends APINeedTesting {
	
	public String creRequest(String ... request) {
		LogInTest login = new LogInTest();
		String currentAccount = login.creRequest(request[0], request[1]);
		login.callAPI(currentAccount);
		JSONObject data = new JSONObject(login.dataResponse);
		String access_token = data.getString("access_token").toString();
		return access_token;
	}
	
	public void callAPI(String access_token, String auctionID) {
		baseURI = BaseURL.BASEURI;
		
		Response response = 
				given()
					.header("Authorization", "Bearer" + access_token)
				.when()
					.get("api/totalLikes/" + auctionID);
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 of TotalLikesOfAuction API: return code should be 1000 and message should be OK");
		
		//Unit 1
		try {
			
			String access_token = this.creRequest(
					"auto@gmail.com"
					,"123456"
					);
			
			String auctionID = "123";
			this.callAPI(access_token, auctionID);
			System.out.println(this.codeResponse);
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
		System.out.println("Test 2 of TotalLikesOfAuction API: input auctionID get a non-numeric value");
		
		//Unit 2
		try {
			String access_token = this.creRequest(
					"auto@gmail.com"
					,"123456"
					);
			String auctionID = "abc";
			this.callAPI(access_token, auctionID);
			System.out.println("Unit 2: Failed");
		} catch (JSONException e) {
			System.out.println("Unit 2: Passed, input auction must be a numeric value");
		}
	}
	
	void test3() {
		System.out.println("Test 3 of TotalLikesOfAuction API: input auctionID get null value");
	
		//Unit 3
		try {
			String access_token = this.creRequest(
					"auto@gmail.com"
					,"123456"
					);
			String auctionID = "";
			this.callAPI(access_token, auctionID);
			System.out.println("Unit 3: Failed");
		} catch (JSONException e) {
			System.out.println("Unit 3: Passed, input auctionID can't get null value");
		}
	}
}
