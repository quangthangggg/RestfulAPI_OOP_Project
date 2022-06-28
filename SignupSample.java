package restautotest;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

public class SignupTest {;

	public String callSignUp() {
		Scanner sc = new Scanner(System.in);
		baseURI = "https://auctions-app-2.herokuapp.com/api";
		
		System.out.println("Input email: ");	String email = sc.nextLine();
		System.out.println("Input password: ");	String password = sc.nextLine();
		System.out.println("Re - password?: "); String rePass = sc.nextLine();
		System.out.println("Input address: ('press 0 for default address')");		String address = sc.nextLine();
		System.out.println("Input name: ('press 0 for default name')");		String name = sc.nextLine();
		System.out.println("Input phone: ('press 0 for default phone')");		String phone = sc.nextLine();
		System.out.println("Input avatar ('press 0 for default avatar')");		String avatar = sc.nextLine();	
		//Set default values
		if(address.equals("0")) address = "Ha Noi";
		if(name.equals("0")) name = "default user";
		if(phone.equals("0")) phone = "0123456789";
		if(avatar.equals("0")) avatar = "https://www.google.com.vn";
		//Create a request object
		JSONObject request = new JSONObject();
		request.put("email", email);
		request.put("password", password);
		request.put("re_pass", rePass);
		request.put("address", address);
		request.put("name", name);
		request.put("phone", phone);
		request.put("avatar", avatar);
		
		Response response = 
				given()
					.header("Content-Type", "application/json")
					.body(request.toString())
				.when()
					.post("/signup");
		String resp = response.getBody().asString();
		return resp;
	}
		
	@Test
	void test1() {
		System.out.println("Test 1: Code and message must not be NULL: ");
		JSONObject resp = new JSONObject(this.callSignUp());
		String code = resp.get("code").toString();
		String message = resp.get("message").toString();
		Assert.assertNotNull(code);
		Assert.assertNotNull(message);
	}
	
	@Test
	void test2() {
		System.out.println("Test 2: Code must be 1000 and Message is 'OK': when signing up correctly an account that does not exist");
		JSONObject resp = new JSONObject(this.callSignUp());
		String code = resp.get("code").toString();
		String message = resp.get("message").toString();
		Assert.assertEquals(code, "1000");
		Assert.assertEquals(message, "OK");
	}
	
	@Test
	void test3() {
		System.out.println("Test3: If email is '' and password is '', code must be 1001");
		JSONObject resp = new JSONObject(this.callSignUp());
		String code = resp.get("code").toString();
		Assert.assertEquals(code, "1001");
	}

}
