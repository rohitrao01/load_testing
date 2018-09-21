/*//TO GET THE OUTPUT FOR ARRAY BODY AND DATA VALIDATION
	public class test {
		static String output;
		static String final_output="";
		static String body;
		public static String login1()
		{
			
		String data = ("{" + //("{"user":{"email":"","password":""}") format to pass as authentication
				"\"user\": {" + 
				"\"email\": \"admin\"," + 
				"\"password\": \"sahaj123\"" + 
				"}" +
				"}");
			Scanner sc=new Scanner(System.in);
			System.out.println("ENTER BASE URL FOR FILTERS");
			String base_url = sc.next();
			RestAssured.baseURI  = "http://dev-platform.draup.com";
			
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
		}
		static String Token_got = draup.token_gen.login();//this is getting token from test class
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
			String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "universe_filter_env_prop" ;
			FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
			prop.load(fis);//loading the file
		}
		@Test(priority = 1)
		public static void array_body()
		{
			
			RestAssured.baseURI = "http://dev-platform.draup.com";//baseurl from rolodex_env file
			
			res = given()
			.queryParam("filters", "{\"rnd_locations\":[\"Austria\",\"Arcadia\",\"Ahmedabad\"],\"outsourcing_size\":{\"min\":0,\"max\":100}}")
			.queryParam("pagination", "{\"offset\":0,\"limit\":25}")
			.header("Content-Type","application/json")//mandatory header of content type
			.header("Authorization", "bearer "+ Token_got )//mandatory header for token
			.when()
			.get("service/universe/opportunities/_all/")//getting resource from files
			.then()
			.contentType(ContentType.JSON).and().statusCode(200)
			.extract().response();
			 body = res.asString();
			 JsonPath js = new JsonPath(body);
	 		String compare = js.getString("rowData[0]");
	 		System.out.println(compare);
	 		
			//System.out.println(body);
			
			JSONParser parser = new JSONParser();
	        try {
	            JSONObject jsonObject = (JSONObject) parser.parse(body);
	            JSONArray data1 = (JSONArray) jsonObject.get("rowData");
	           
	            //System.out.println(data1);
	            for(int i = 0; i< data1.size(); i++) 
	            	{
	            		JSONArray sub = (JSONArray) data1.get(i);
	            		output = (String)(sub.get(2));
			           //System.out.println(output);
	            		if(i==0) {
			           final_output = output;
	            		}
	            		else {
	            			final_output = final_output + "," +output;	
	            		}
			        }
	            System.out.println(final_output);
	            int temp = 0;
	    		String[] arr = prop.getProperty("data11").split(",");
	    		for (int i=0;i<arr.length;i++) 
	    		{
	    			//System.out.println(arr[i]);
	    			if(!final_output.contains(arr[i]))
	    			{
	    				temp = 1;
	    				//ATUReports.add("steps", prop.getProperty(data),final_output,false);
	    				Reporter.log("Coudn't find " + arr[i] + " in "+ output + "method is failed");
	    				Assert.fail();
	    			}
	    		}
	    		if (temp==0) 
	    		{
	    			ATUReports.add("steps", prop.getProperty("data1"),final_output,false);
	    		}
	    		else
	    		{
	    			ATUReports.add("steps", prop.getProperty("data1"),final_output,false);
	    		}
	    		
	    	}
	         
	        catch (Exception e) {
	        	e.printStackTrace();
	            System.out.println("ERROR");
	            Assert.fail();
	        }  
	           

	        
		

	        }}
	*/


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

	public class rolodex_filter {
		static String output;
		static String final_output="";
		static String body;
		static String compare;
		/*public static String login1()
		{
			
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
			String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "rolodex_filter_env_prop" ;
			FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
			prop.load(fis);//loading the file
		}
		
	
		public static String result_gen(String status, String url, String compare, String data, String methodName)
		{
			String test = 
			("Status : " + status +"\n" +"Method Name : " + methodName + "\n" + "URL : " + url + "\n" + "Actual Output : "+compare +"\n"+ "Expected Output : "+prop.getProperty(data)+"\n"+"----------------------------------");
			return test;
		}
		
		public static void dataValidation(String filter, String pagination, String data, String path, String methodName,String steps) 
		{
			
			
			RestAssured.baseURI = "https://qa-platform.draup.com";//baseurl from rolodex_env file
			
			res = given()
			.queryParam("filters", prop.getProperty(filter))
			.queryParam("pagination", prop.getProperty(pagination))
			.header("Content-Type","application/json")//mandatory header of content type
			.header("Authorization", "bearer "+ Token_got )//mandatory header for token
			.when()
			.get("service/rolodex/executives/")//getting resource from files
			.then()
			.assertThat().contentType(ContentType.JSON).and().statusCode(200)
			.extract().response();
			 body = res.asString();
			/*//System.out.println(body);
			JsonPath js = new JsonPath(body);
			String compare = js.getString(prop.getProperty(path));
			//System.out.println(compare);
			//data= prop.getProperty("data1");
			String[] arr = prop.getProperty(data).split(",");
			int temp = 0;
			
			
			for (int i=0;i<arr.length;i++) 
			{
				//System.out.println(arr[i]);
				if(!compare.contains(arr[i]))
				{
					temp = 1;
					Reporter.log(rolodex_filter.result_gen("Failed","http://dev-platform.draup.com/service/rolodex/executives/", compare, data, methodName));
					Assert.fail(rolodex_filter.result_gen("Failed","http://dev-platform.draup.com/service/rolodex/executives/", compare, data, methodName));
					
				}
			}
				
			
			if (temp==0) 
			{
				System.out.println(rolodex_filter.result_gen("Passed","http://dev-platform.draup.com/service/rolodex/executives/",compare, data, methodName));
				ATUReports.add(steps, compare, prop.getProperty(data), false);
			}
			else
			{
				
				System.out.println(rolodex_filter.result_gen("Failed","http://dev-platform.draup.com/service/rolodex/executives/",compare, data, methodName));
				ATUReports.add(steps, compare, prop.getProperty(data), false);
			}*/
		}


	@Test(priority = 1)
	public void rol_fil_verticals()
	{
		rolodex_filter.dataValidation("fil1", "pag1", "data1", "path1", "rol_fil_verticals","steps");
	}
	@Test(priority = 2)
	public void rol_fil_subverticals()
	{
		rolodex_filter.dataValidation("fil2", "pag2", "data2", "path2", "rol_fil_subverticals", "steps");
	}
	@Test(priority = 3)
	public void rol_fil_company()
	{
		rolodex_filter.dataValidation("fil3", "pag3", "data3", "path3", "rol_fil_company", "steps");
	}
	@Test(priority = 4)
	public void rol_fil_business_unit()
	{
		rolodex_filter.dataValidation("fil4", "pag4", "data4", "path4", "rol_fil_business_unit", "steps");
	}
	@Test(priority = 5)
	public void rol_fil_locations()
	{
		rolodex_filter.dataValidation("fil5", "pag5", "data5", "path5", "rol_fil_locations", "steps");
	}
	@Test(priority = 6)
	public void rol_fil_level_in_org()
	{
		rolodex_filter.dataValidation("fil6", "pag6", "data6", "path6", "rol_fil_level_in_org", "steps");
	}
	@Test(priority = 7)
	public void rol_fil_opp_index()
	{
		rolodex_filter.dataValidation("fil7", "pag7", "data7", "path7", "rol_fil_opp_index", "steps");
	}
	@Test(priority = 8)
	public void rol_fil_deal_size_influence()
	{
		rolodex_filter.dataValidation("fil8", "pag8", "data8", "path8", "rol_fil_deal_size_influence", "steps");
	}
	@Test(priority = 9)
	public void rol_fil_verticals_with_other1()
	{
		rolodex_filter.dataValidation("fil9", "pag9", "data9", "path9", "rol_fil_verticals_with_other1", "steps");
	}
	@Test(priority = 10)
	public void rol_fil_verticals_with_other2()
	{
		rolodex_filter.dataValidation("fil10", "pag10", "data10", "path10", "rol_fil_verticals_with_other2", "steps");
	}
	@Test(priority = 11)
	public void rol_fil_deal_size_influence_with_others()
	{
		rolodex_filter.dataValidation("fil11", "pag11", "data11", "path11", "rol_fil_deal_size_influence_with_others", "steps");
	}
	@Test(priority = 12)
	public void rol_fil_combination()
	{
		rolodex_filter.dataValidation("fil12", "pag12", "data12", "path12", "rol_fil_combination", "steps");
	}
}