package draup;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;



public class token_gen {
	
	@Test
	//@BeforeSuite
	public static String login()
	{
	System.out.println("started test for token generation");
	String data = ("{" + //("{"user":{"email":"","password":""}") format to pass as authentication
			"\"user\": {" + 
			"\"email\": \"rohit@zinnov.com\"," + 
			"\"password\": \"zinnov123\"" + 
			"}" +
			"}");
		/*Scanner sc=new Scanner(System.in);
		System.out.println("ENTER BASE URL");
		String base_url = sc.next();*/
		System.out.println("after credentials");
		RestAssured.baseURI  = "https://qa-platform.draup.com";
		
		Response res = given()
		.body(data)
		.contentType("application/json")
		.when()
		.post("service/django-users/users/login")//resource address
		.then()
		.and()
		.extract().response();
		System.out.println("in between rest assured commands");
		//String body = r.getBody().asString();
		String body = res.asString();
		System.out.println(body);
		
		JsonPath js = new JsonPath(body);
		String token_number = js.get("user.token");
		System.out.println("token : "+token_number);
		return token_number;
	}
}