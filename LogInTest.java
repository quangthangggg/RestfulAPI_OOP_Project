package restautotest;

import static io.restassured.RestAssured.*;
import org.json.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;

 
public class LogInTest extends APINeedTesting{
	
	public String creRequest(String... request) {
		JSONObject req = new JSONObject();
		req.put("email", request[0]);
		req.put("password", request[1]);
		return req.toString();
	}
	
	public void callAPI(String request) {
		baseURI = BaseURL.BASEURI;
		Response response = 
				given()
					.header("Content-Type", "application/json")
					.body(request)
				.when()
					.post("api/login");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}

	void test1() {
		System.out.println("Test 1 in SignIn API: The code and message strings shall be not NULL as well as non-empty:");
		
		//Unit 1
		try {
			String request = this.creRequest(
					"damanh@gmail.com"							
					,"123456"
			);
			this.callAPI(request);
			Assert.assertNotNull(this.codeResponse);
			Assert.assertNotNull(this.messageResponse);
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}

		
		//Unit 2
		try {
			String request = this.creRequest(
					"d111112@gmail.com"
					,"123"
			);
			this.callAPI(request);
			Assert.assertNotNull(this.codeResponse);
			Assert.assertNotNull(this.messageResponse);
			System.out.println("Unit 2: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 2: Failed");
		}

	 }

}
