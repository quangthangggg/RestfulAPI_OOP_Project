package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import io.restassured.response.Response;
import netscape.javascript.JSObject;

public class GetListLikes extends APINeedTesting {

	public String creRequest(String ... request) {
		JSONObject req = new JSONObject();
		req.put("index", request[0]);
		req.put("count", request[1]);
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
					.get("api/likes");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 of GetListLikes API: return code should be 1000 and message should be OK");
		
		//Unit 1
		try {
			String request = this.creRequest("1", "1");
			
			String email = "auto@gmail.com";
			String password = "123456";
			
			this.callAPI(email, password, request);
			
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
			System.out.println("Code: 1000\nMessage: OK");
	        System.out.println("Unit 1: Passed");
	        System.out.println(this.dataResponse);
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		} catch (JSONException j) {
			System.out.println("Unit 1: Failed");
		}	
	}
}
