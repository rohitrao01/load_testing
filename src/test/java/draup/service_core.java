	package draup;
	import static io.restassured.RestAssured.given;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.util.List;
	import java.util.Properties;
	import java.util.Scanner;

	import org.json.simple.JSONArray;
	import org.json.simple.JSONObject;
	import org.json.simple.parser.JSONParser;
	import org.openqa.selenium.WebDriver;
	import org.testng.Assert;
	import org.testng.Reporter;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Test;

	import atu.testng.reports.ATUReports;
	import atu.testng.reports.utils.Utils;
	import io.restassured.RestAssured;
	import io.restassured.http.ContentType;
	import io.restassured.path.json.JsonPath;
	import io.restassured.response.Response;

	public class service_core {
		static String output;
		static String final_output="";
		static String body;
		static String compare;
		
		
/*			
		String data = ("{" + //("{"user":{"email":"","password":""}") format to pass as authentication
				"\"user\": {" + 
				"\"email\": \"admin\"," + 
				"\"password\": \"sahaj123\"" + 
				"}" +
				"}");
			Scanner sc=new Scanner(System.in);
			System.out.println("ENTER BASE URL FOR FILTERS");
			String base_url = sc.next();
			RestAssured.baseURI  = "https://dev-platform.draup.com";
			
			Response r = given()
			.body(data)
			.contentType("application/json")
			.when()
			.post("/django-users/users/login")//resource address
			.then()
			.and()
			.extract().response();
			
			//String body = r.getBody().asString();
			String j_format = r.asString();
			//System.out.println(body);
			
			JsonPath js = new JsonPath(j_format);
			String token_number = js.get("user.token");
			//System.out.println("token : "+token_number);
			return token_number;
		}*/
		static String Token_got = token_gen.login();//this is getting token from test class
		static Response res;
		static Properties prop  = new Properties();
		{
			System.setProperty("atu.reporter.config",System.getProperty("user.dir")+ File.separator + "src" + File.separator +"atu.properties");
		}
		public WebDriver driver;
		
		protected void setAuthorInfoF0orReports() 
		{
	        ATUReports.setAuthorInfo("Rohit", Utils.getCurrentTime(),
	                "1.0");
	        ATUReports.setWebDriver(driver);
	        ATUReports.indexPageDescription = "Draup Api Testing Report";
	        ATUReports.currentRunDescription = "Draup Api Testing Report";
	       
		}
		@BeforeTest
		public void env_run() throws IOException
		{
			String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "service_core_env_prop" ;
			FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
			prop.load(fis);//loading the file
		}
		
	
		public static String result_gen(String status, String url, String compare, String data, String methodName)
		{
			String test=
			("Status : " + status +"\n" +"Method Name : " + methodName + "\n" + "URL : " + url + "\n" + "Expected Output : "+compare +"\n"+ "Actual Output : "+prop.getProperty(data)+"\n"+"----------------------------------");
			return test;
		}
		
		public static void dataValidation(String data, String path, String methodName,String steps, String query) 
		{
			
			
			RestAssured.baseURI = prop.getProperty("base_url");//baseurl from rolodex_env file
			
			res = given()
			.header("Content-Type","application/json")//mandatory header of content type
			.header("Authorization", "bearer "+ Token_got )//mandatory header for token
			.when()
			.get(prop.getProperty("resource")+prop.get(query))//getting resource from files
			.then()
			.assertThat().contentType(ContentType.JSON).and().statusCode(200)
			.extract().response();
			 body = res.asString();
			//System.out.println(body);
			/*JsonPath js = new JsonPath(body);
			String compare = js.getString(prop.getProperty(path));
			//System.out.println(compare);
			
			String[] arr = prop.getProperty(data).split(",");
			
			int temp = 0;
			
			
			for (int i=0;i<arr.length;i++) 
			{
				//System.out.println(arr[i]);
				if(!compare.contains(arr[i]))
				{
					temp = 1;
					ATUReports.add(steps, compare, prop.getProperty(data), false);
					Assert.fail(service_core.result_gen("Failed","http://dev-platform.draup.com/service/core/context/", compare, data, methodName));
					
				}
			}
			if (temp==0) 
			{	
				System.out.println(service_core.result_gen("Passed","http://dev-platform.draup.com/service/core/context/"+prop.getProperty(query),compare, data, methodName));
				ATUReports.add(steps, compare, prop.getProperty(data), false);
			}
			else
			{
				
				System.out.println(service_core.result_gen("Failed","http://dev-platform.draup.com/service/core/context/"+prop.getProperty(query),compare, data, methodName));
				ATUReports.add(steps, compare, prop.getProperty(data), false);
			}*/
		}
		@Test(priority = 1)
		public void service_core_context()
		{
			service_core.dataValidation("data1", "path1", "service_core_context", "steps", "query1");
		}
		@Test(priority = 2)
		public void service_core_context_id()
		{
			service_core.dataValidation("data2", "path2", "service_core_context_id", "steps", "query2");
		}
		/*@Test(priority = 3)
		public void service_core_footnote()
		{
			service_core.dataValidation("data3", "path3", "service_core_footnote", "steps", "query3");
		}*/
		//FOOTNOTE FEW API FILTER DONT KNOW WHAT TO ADD
		@Test(priority = 4)
		public void service_core_footnote_id()
		{
			service_core.dataValidation("data4", "path4", "service_core_footnote_id", "steps", "query4");
		}
		@Test(priority = 5)
		public void service_core_label()
		{
			service_core.dataValidation("data5", "path5", "service_core_label", "steps", "query5");
		}
		@Test(priority = 6)
		public void service_core_label_groups()
		{
			service_core.dataValidation("data6", "path6", "service_core_label_groups", "steps", "query6");
		}
		//GET /service/core/labels/groups/{group}/locales/{locale}/ filters dont know
		@Test(priority = 7)
		public void service_core_label_id()
		{
			service_core.dataValidation("data7", "path7", "service_core_label_id", "steps", "query7");
		}
		@Test(priority = 8)
		public void service_core_page()
		{
			service_core.dataValidation("data8", "path8", "service_core_page", "steps", "query8");
		}
		@Test(priority = 9)
		public void service_core_pages_all()
		{
			service_core.dataValidation("data9", "path9", "service_core_pages_all", "steps", "query9");
		}
		@Test(priority = 10)
		public void service_core_settings()
		{
			service_core.dataValidation("data10", "path10", "service_core_settings", "steps", "query10");
		}
		//GET /service/core/settings/key/(P{key}.+) dont know the type of filter
		//GET /service/core/settings/{id}/ is showing 404 with id 1
		@Test(priority = 11)
		public void service_core_subverticals()
		{
			service_core.dataValidation("data11", "path11", "service_core_subverticals", "steps", "query11");
		}
		@Test(priority = 12)
		public void service_core_subvertical_id()
		{
			service_core.dataValidation("data12", "path12", "service_core_subvertical_id", "steps", "query12");
		}
		@Test(priority = 13)
		public void service_core_tabledefs()
		{
			service_core.dataValidation("data13", "path13", "service_core_tabledefs", "steps", "query13");
		}
		@Test(priority = 14)
		public void service_core_tabledefs_id()
		{
			service_core.dataValidation("data14", "path14", "service_core_tabledefs_id", "steps", "query14");
		}
		@Test(priority = 15)
		public void service_core_userprofile()
		{
			service_core.dataValidation("data15", "path15", "service_core_userprofile", "steps", "query15");
		}
		@Test(priority = 16)
		public void service_core_userprofile_id()
		{
			service_core.dataValidation("data16", "path16", "service_core_userprofile_id", "steps", "query16");
		}
		@Test(priority = 17)
		public void service_core_verticals()
		{
			service_core.dataValidation("data17", "path17", "service_core_verticals", "steps", "query17");
		}
		@Test(priority = 18)
		public void service_core_vertical_id()
		{
			service_core.dataValidation("data18", "path18", "service_core_vertical_id", "steps", "query18");
		}
		@Test(priority = 19)
		public void service_core_vertical_name()
		{
			service_core.dataValidation("data19", "path19", "service_core_vertical_name", "steps", "query19");
		}
	
	
	
	
	}