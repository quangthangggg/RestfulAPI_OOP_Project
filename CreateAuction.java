package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;

public class CreateAuction extends APINeedTesting{

	public String creRequest(String... request) {		
		JSONObject req = new JSONObject();
		req.put("category_id", request[0]);
		req.put("start_date", request[1]);
		req.put("end_date", request[2]);
		req.put("title_ni", request[3]);
		return req.toString();
	}
	
	public void callAPI(String currentEmail, String currentPassword, String request) {
		baseURI = BaseURL.BASEURI;
		
		LogInTest login = new LogInTest();
		String currentAccount = login.creRequest(currentEmail, currentPassword);
		login.callAPI(currentAccount);
		JSONObject data = new JSONObject(login.dataResponse);
		String access_token = data.getString("access_token").toString();
		
		Response response = 
				given()
					.header("Authorization", "Bearer" + access_token)
					.contentType("application/json")
					.body(request)
				.when()
					.post("api/auctions/create");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in CreateAuction API: The code should be 1000 and message is OK:");
		
		//Unit 1
		try {		
			String requestEdit = this.creRequest(
					"1"
					, "2022/07/05"
					, "2022/07/09"
					, "auctsss"
			);
			String email = "auto@gmail.com";	
			String password = "123456";
			this.callAPI(email, password, requestEdit);
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}
	}
}
