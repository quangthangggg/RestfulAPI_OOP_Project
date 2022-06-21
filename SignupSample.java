package restautotest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import static org.junit.Assert.assertThat;
import org.json.JSONObject;
import org.testng.Assert;

public class SignupTest {
	
	public static void main(String args[]) {
		baseURI = "https://auctions-app-2.herokuapp.com/";
		
		JSONObject request = new JSONObject();
		request.put("email", "damanh@gmail.com");
		request.put("password", "123456");
		request.put("re_pass", "123456");
		request.put("address", "Ha Noi");
		request.put("name", "Viet Anh");
		request.put("phone", "0145146146");
		request.put("avatar", "https://www.facebook.com/photo/?fbid=2845275342433845&set=a.1374537556174305");
		
		Response response = 
				given()
					.header("Content-Type", "application/json")
					.body(request.toString())
				.when()
					.post("api/signup");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		String code = rep.get("code").toString();
		String message = rep.get("message").toString();
		
        System.out.println("Unit test 1: The code and message strings shall be not NULL as well as non-empty:");
        Assert.assertNotNull(code);
        Assert.assertNotNull(message);
        System.out.println("Finished! Satisfied!");
	}

}
