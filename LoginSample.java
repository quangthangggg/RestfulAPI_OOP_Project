package restautotest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.json.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

 
public class LoginTest {
 
	public static void main(String args[]) {   
		//Set base URI
		baseURI = "https://auctions-app-2.herokuapp.com/";
		
		//Create JSON object to send input
		JSONObject request = new JSONObject();
		request.put("email", "damanh@gmail.com");
		request.put("password", "123456");
		
		//Create RESPONSE object to recieve response from api
		Response response = 
				given()
					.header("Content-Type", "application/json")
					.body(request.toString())
				.when()
					.post("api/login");
		
		//To print body of response
		System.out.println(response.getBody().asPrettyString());
		
		//To extract value of key in response body
		JSONObject rep = new JSONObject(response.getBody().asString());
		String code = rep.get("code").toString();
		String message = rep.get("message").toString();
		JSONObject data = rep.getJSONObject("data");
		String access_token = data.getString("access_token").toString();
		
		//Sample unit test 1
        System.out.println("Unit test 1: The code and message strings shall be not NULL as well as non-empty:");
        Assert.assertNotNull(code);
        Assert.assertNotNull(message);
        System.out.println("Finished! Satisfied!");
        
        System.out.println("Unit test 2: The code should be 1000");
        Assert.assertEquals(code, "1000");
        System.out.println("Finished! Satisfied!");
        
        

	 }

}
