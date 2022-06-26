package restautotest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.response.Response;

public class LogoutTest {

	public static void main(String args[]) {
		baseURI = "https://auctions-app-2.herokuapp.com/";
		
		JSONObject request = new JSONObject();
		request.put("email", "damanh@gmail.com");
		request.put("password", "123456");
		
		//Create RESPONSE object to recieve response from api
		Response responseLogin = 
				given()
					.header("Content-Type", "application/json")
					.body(request.toString())
				.when()
					.post("api/login");
		
		JSONObject rep = new JSONObject(responseLogin.getBody().asString());
		JSONObject data = new JSONObject(rep.getJSONObject("data").toString());
		String access_token = data.get("access_token").toString();

		
		Response responseLogout = 
				given()
					.header("Authorization", 
							"Bearer" + access_token)
				.when()
					.post("api/logout");
		
		System.out.println(responseLogout.getBody().asPrettyString());
	}
}
