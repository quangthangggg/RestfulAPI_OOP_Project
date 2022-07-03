package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import org.json.JSONObject;
import org.testng.Assert;
import io.restassured.response.Response;

public class LikeAuction extends APINeedTesting {
	
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
					.post("api/updateLike/" + auctionID);
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		
		//Unit 1
		try {
			System.out.println("Test 1: return code should be 1000 and message should be OK");
			
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
}
