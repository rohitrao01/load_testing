package draup;
	import org.testng.annotations.Test;
	import atu.testng.reports.ATUReports;
	import atu.testng.reports.utils.Utils;

import org.apache.commons.codec.binary.StringUtils;
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

		public class universe {
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
			
			public static void status_check_200(String Token_got,String resource)
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
				
			}
			
			public static String result_gen(String status, String url, String compare, String data, String methodName)
			{
				String test=
				("Status : " + status +"\n" +"Method Name : " + methodName + "\n" + "URL : " + url + "\n" + "Expected Output : "+compare +"\n"+ "Actual Output : "+data+"\n"+"----------------------------------");
				return test;
			}
			
			public static void dataValidation(String data,String compare,String methodName) {
				//data= prop.getProperty("data1");
			/*	
				
				
				String[] arr = data.split(",");
				int temp = 0;
				
				if(org.apache.commons.lang3.StringUtils.isEmpty(compare)) 
				{
					ATUReports.add("Data Not Available", compare, data, false);
					System.out.println("Data Not Available");
					Assert.fail();
				}
					
				else 
				{	
				for (int i=0;i<arr.length;i++) 
					{
					
					//System.out.println(arr[i]);
					
					if(!compare.contains(arr[i]))
					{	
						temp = 1;
						
						ATUReports.add(steps, compare, data, false);
						
						//Reporter.log(account.result_gen("Failed","http://dev-platform.draup.com//service/accounts/",compare, data, methodName));
						Assert.fail("Data Validation is failed for : "+methodName);
						
							}
						}
					}
				
				if (temp==0)
				{	
					//System.out.println(data);
					System.out.println(account.result_gen("Passed","http://dev-platform.draup.com/service/universe",compare, data, methodName));
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
				String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "universe_env_prop" ;
				FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
				prop.load(fis);//loading the file
			}
			/*@Test(priority = 1)
			public void get_universe_disruption()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_disruption"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				System.out.println(compare);
				universe.dataValidation(prop.getProperty("body_text1"), compare, "get_universe_disruption");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_disruption"));
			}*/
			@Test(priority = 2)
			public void get_universe_filters_accountsforsignal()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_filters_accountsforsignal"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text2"), compare, "get_universe_filters_accountsforsignal");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_filters_accountsforsignal"));
			}
			@Test(priority = 3)
			public void get_universe_hqlocations()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_hqlocations"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("location");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text3"), compare, "get_universe_hqlocations");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_hqlocations"));
			}
			@Test(priority = 4)
			public void get_universe_rndlocation()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_rndlocation"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("locations");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text4"), compare, "get_universe_rndlocation");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_rndlocation"));
			}
			@Test(priority = 5)
			public void get_universe_signaltype()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_signaltype"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text5"), compare, "get_universe_signaltype");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_signaltype"));
			}
			@Test(priority = 6)
			public void get_universe_sp_partners()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_sp_partners"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text6"), compare, "get_universe_sp_partners");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_sp_partners"));
			}
			@Test(priority = 7)
			public void get_universe_verticals()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_verticals"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("vertical");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text7"), compare, "get_universe_verticals");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_verticals"));
			}
			@Test(priority = 8)
			public void get_universe_financials()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_financials"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text8"), compare, "get_universe_financials");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_financials"));
			}
			@Test(priority = 9)
			public void get_universe_globalization()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_globalization"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text9"), compare, "get_universe_globalization");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_globalization"));
			}
			@Test(priority = 10)
			public void get_universe_opportunities()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_opportunities"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text10"), compare, "get_universe_opportunities");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_opportunities"));
			}
			@Test(priority = 11)
			public void get_universe_outsourcing()
			{
				universe.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_universe_outsourcing"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				//System.out.println(compare);
				
				universe.dataValidation(prop.getProperty("body_text11"), compare, "get_universe_outsourcing");
				universe.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_universe_outsourcing"));
			}
		}