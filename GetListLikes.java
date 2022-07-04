package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import io.restassured.response.Response;
import netscape.javascript.JSObject;

public class GetListLikes extends APINeedTesting {
	
	public String creRequest1(String ... request) {
		LogInTest login = new LogInTest();
		String currentAccount = login.creRequest(request[0], request[1]);
		login.callAPI(currentAccount);
		JSONObject data = new JSONObject(login.dataResponse);
		String access_token = data.getString("access_token").toString();
		return access_token;
	}
	
	public String creRequest2(String ... request) {
		JSONObject req = new JSONObject();
		req.put("index", request[0]);
		req.put("count", request[1]);
		return req.toString();
	}
	
	public void callAPI(String access_token, String request) {
		baseURI = BaseURL.BASEURI;
		
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
			String access_token = this.creRequest1(
					"auto@gmail.com"							
					,"123456");
			
			String request = this.creRequest2("1", "1");
			
			this.callAPI(access_token, request);
			
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

	void test2() {
		System.out.println("Test 2 of GetListLikes API: return code should be 1004 since user haven't logged in");
		
		try {
			String access_token = "";
			
			String request = this.creRequest2("1", "1");
			
			this.callAPI(access_token, request);
			Assert.assertEquals(this.codeResponse, 1004);
			System.out.println("Code: 1004");
	        System.out.println("Unit 1: Passed");
	        System.out.println(this.codeResponse);
	        System.out.println(this.dataResponse);
		} catch (AssertionError e) {
			System.out.println("Unit 2: Failed");
		} catch (JSONException j) {
			System.out.println("Unit 2: Failed");
		}
	}
}
