package draup;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
	//import static org.testng.Assert.fail;
	//import static org.hamcrest.Matchers.equalTo;

	public class contenders {
		static String steps = "steps";
		static String Token_got = draup.token_gen.login();//this is getting token from test class
		static Properties prop  = new Properties();
		{
			System.setProperty("atu.reporter.config",System.getProperty("user.dir")+ File.separator + "src" + File.separator +"atu.properties");
		}
		public WebDriver driver;
		
		protected void setAuthorInfoF0orReports() 
		{
	        ATUReports.setAuthorInfo("Rohit", Utils.getCurrentTime(),"1.0");
	        ATUReports.setWebDriver(driver);
	        ATUReports.indexPageDescription = "Draup Api Testing Report";
	        ATUReports.currentRunDescription = "Draup Api Testing Report";
		}
		static Response res;
		public static String result_gen(String status, String url, String compare, String data, String methodName)
		{
			String test=
			("Status : " + status +"\n" +"Method Name : " + methodName + "\n" + "URL : " + url + "\n" + "Expected Output : "+compare +"\n"+ "Actual Output : "+data+"\n"+"----------------------------------");
			return test;
		}
		public static Response status_check_200(String Token_got,String resource)
		{
			
			RestAssured.baseURI = prop.getProperty("base_url");//baseurl from rolodex_env file
			
			res = given()
			.header("Content-Type","application/json")//mandatory header of content type
			.header("Authorization", "bearer "+ Token_got )//mandatory header for token
			.when()
			.get(resource)//getting resource from files
			.then().assertThat().and().statusCode(200)
			.and().contentType(ContentType.JSON).and()
			.extract().response();
			return res;
		}
		
		public static void dataValidation(String data,String compare,String methodName) {
			/*//data= prop.getProperty("data1");
			String[] arr = data.split(",");
			int temp = 0;
			
			for (int i=0;i<arr.length;i++) {
				
				//System.out.println(arr[i]);
				
				if(!compare.contains(arr[i]))
				{
					temp = 1;
					ATUReports.add(steps, compare, data, false);
					//Reporter.log(account.result_gen("Failed","http://dev-platform.draup.com//service/accounts/",compare, data, methodName));
					Assert.fail("Data Validation is failed for : "+methodName);
					
				}
				
				
			}
			
			if (temp==0)
			{	
				//System.out.println(data);
				System.out.println(account.result_gen("Passed","http://dev-platform.draup.com/service/contenders",compare, data, methodName));
				System.out.println("Data Validation is passed for " + methodName);
				
				ATUReports.add(steps, compare, data, false);
			}
			else
			{
				System.out.println("Data Validation is failed for " + methodName);
			}*/
		}
		
		public static void negative_test_401(String resource)
		{
			//negative method for token value is wrong
		  	  given()
			 .header("Content-Type", "application/json")	
			 .header("Authorization", "bearer "+Token_got+"1")
			 .when()
			 .get(resource)//getting resource from files
			 .then().assertThat().statusCode(401);
			  System.out.println("401 - negative test passed");
		}
		
		@BeforeTest
		public void env_run() throws IOException
		{	
			String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "contenders_env_prop" ;
			FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
			prop.load(fis);//loading the file
		}
		
		@Test(priority = 1)
		public void get_contenders_overview()
		{
			contenders.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_contenders_overview"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("rowData[0][1]");
			//System.out.println(compare);
			
			contenders.dataValidation(prop.getProperty("body_text1"), compare, "get_contenders_overview");
			contenders.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_contenders_overview"));
			
		}
		@Test(priority = 2)
		public void post_contenders_name_suggestion()
		{
			RestAssured.baseURI = prop.getProperty("base_url");
			
			Response res = given()
			.header("Content-Type","application/json")//mandatory header of content type
			.header("Authorization", "bearer "+ Token_got )//mandatory header for token
			.when()
			.post("/service/contenders/names/load_autocomp/")
			.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
			
			String body = res.asString();
			System.out.println("Status Code for post request - post_contenders_name_suggestion : "+res.statusCode());
		}
		
		@Test(priority = 3)
		public void get_contenders_name_suggestion()
			{
				contenders.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_contenders_name_suggestion"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0][1]");
				//System.out.println(compare);
				
				//contenders.dataValidation(prop.getProperty("body_text2"), compare, "get_contenders_name_suggestion");
				contenders.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_contenders_name_suggestion"));
			}
		@Test(priority = 3)
		public void get_contenders_comparison()
		{
			contenders.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_contenders_comparison"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);
			//contenders.dataValidation(prop.getProperty("body_text3"), compare, "get_contenders_comparison");
			contenders.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_contenders_comparison"));
		}
		}
