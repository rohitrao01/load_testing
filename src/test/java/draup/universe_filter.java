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

public class universe_filter {
	static String output;
	static String final_output="";
	static String body;
	static String steps = "steps";
/*	public static String login1()
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
		String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "universe_filter_env_prop" ;
		FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
		prop.load(fis);//loading the file
	}
	
	public static String array_body(String filter, String pagination, String path, int index, String data)
	{
		
		RestAssured.baseURI = "http://dev-platform.draup.com";//baseurl from rolodex_env file
		
		res = given()
		.queryParam("filters", prop.getProperty(filter))
		.queryParam("pagination", prop.getProperty(pagination))
		.header("Content-Type","application/json")//mandatory header of content type
		.header("Authorization", "bearer "+ Token_got )//mandatory header for token
		.when()
		.get("service/universe/opportunities/_all/")//getting resource from files
		.then()
		.contentType(ContentType.JSON).and().statusCode(200)
		.extract().response();
		 body = res.asString();
		//System.out.println(body);
		
		JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(body);
            JSONArray data1 = (JSONArray) jsonObject.get(prop.getProperty(path));
           
            System.out.println(data1);
            for(int i = 0; i< data1.size(); i++) 
            	{
            		JSONArray sub = (JSONArray) data1.get(i);
            		output = (String)(sub.get(index));
		           //System.out.println(output);
            		if(i==0) {
		           final_output = output;
            		}
            		else {
            			final_output = final_output + "," +output;	
            		}
		        }
            	   //System.out.println(final_output);
            int temp = 0;
    		String[] arr = prop.getProperty(data).split(",");
    		for (int i=0;i<arr.length;i++) 
    		{
    			//System.out.println(arr[i]);
    			if(!final_output.contains(arr[i]))
    			{
    				temp = 1;
    				ATUReports.add("steps", prop.getProperty(data),final_output,false);
    				Reporter.log("Coudn't find " + arr[i] + " in "+ output + "method is failed");
    				Assert.fail();
    			}
    		}
    		if (temp==0) 
    		{
    			ATUReports.add("steps", prop.getProperty(data),final_output,false);
    		}
    		else
    		{
    			ATUReports.add("steps", prop.getProperty(data),final_output,false);
    		}
    		
    	}
         
        catch (Exception e) {
        	e.printStackTrace();
            System.out.println("ERROR");
        }
        return body;
	}
	public static String result_gen(String status, String url, String compare, String data, String methodName)
	{
		String test=
		("Status : " + status +"\n" +"Method Name : " + methodName + "\n" + "URL : " + url + "\n" + "Expected Output : "+compare +"\n"+ "Actual Output : "+data+"\n"+"----------------------------------");
		return test;
	}
	
	public static void dataValidation(String data,String compare,String methodName) {
		//data= prop.getProperty("data1");
		/*String[] arr = data.split(",");
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
			System.out.println(account.result_gen("Passed","http://dev-platform.draup.com/service/core/context/",compare, data, methodName));
			System.out.println("Data Validation is passed for " + methodName);
			
			ATUReports.add(steps, compare, data, false);
		}
		else
		{
			System.out.println("Data Validation is failed for " + methodName);
		}*/
	}
		

		
		
		
		@Test(priority = 1)
		public void uni_fil_vertical()
		{
			body = universe_filter.array_body("ver_fil", "ver_pag", "ver_path", 2, "data1");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			System.out.println(compare);
			//universe_filter.dataValidation("data2", "vertical", compare);
			System.out.println("#########################################");
		}
			
		@Test(priority = 2)
		public void subverticals()
		{
			body = universe_filter.array_body("sub_filter", "sub_pagination", "sub_path", 2, "data3");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data4", "subverticals", compare);
			
			System.out.println("#########################################");
		}
		@Test(priority = 3)
		public void service_provider_partner()
		{
			body = universe_filter.array_body("ser_filter", "ser_pagination", "ser_path", 2, "data6");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data5", "service_provider_partner", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 4)
		public void rnd_location()
		{
			body = universe_filter.array_body("rnd_filter", "rnd_pagination", "rnd_path", 2, "data7");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data8", "rnd_location", compare);
			
			System.out.println("#########################################");
			
		}
		
		@Test(priority = 5)
		public void HQ_location()
		{
			body = universe_filter.array_body("hq_filter", "hq_pagination", "hq_path", 2, "data10");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data9", "HQ_location", compare);
			System.out.println("#########################################");
		}
		
		@Test(priority = 6)
		public void Outsourcing_size()
		{
			body = universe_filter.array_body("out_filter", "out_pagination", "out_path", 2, "data11");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data12", "Outsourcing_size", compare);
			System.out.println("#########################################");
		}

		@Test(priority = 7)
		public void opportunity_index()
		{
			body = universe_filter.array_body("opp_filter", "opp_pagination", "opp_path", 2, "data13");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data14", "opportunity_index", compare);
			
			System.out.println("#########################################");
			
		}

		@Test(priority = 8)
		public void revenue()
		{
			body = universe_filter.array_body("rev_filter", "rev_pagination", "rev_path", 2, "data15");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data16", "revenue", compare);
			System.out.println("#########################################");
			
		}
		@Test(priority = 9)
		public void employee()
		{
			body = universe_filter.array_body("emp_filter", "emp_pagination", "emp_path", 2, "data17");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data18", "employee", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 10)
		public void signals()
		{
			body = universe_filter.array_body("sig_filter", "sig_pagination", "sig_path", 2, "data19");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data20", "signals", compare);
			System.out.println("#########################################");
		}
// COMBINATION OF FILTERS
		@Test(priority = 11)
		public void vertcials_with_others1()
		{
			body = universe_filter.array_body("fil1", "pag1", "fil_path1", 2, "data_fil1");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil2", "vertcials_with_others1", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 12)
		public void verticals_with_others2()
		{
			body = universe_filter.array_body("fil2", "pag2", "fil_path2", 2, "data_fil3");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil4", "verticals_with_others2", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 13)
		public void verticals_with_others3()
		{
			body = universe_filter.array_body("fil3", "pag3", "fil_path3", 2, "data_fil5");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil6", "verticals_with_others3", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 14)
		public void subverticals_with_others1()
		{
			body = universe_filter.array_body("fil4", "pag4", "fil_path4", 2, "data_fil96");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			
			universe_filter.dataValidation("data_fil7", "subverticals_with_others1", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 15)
		public void subverticals_with_others2()
		{
			body = universe_filter.array_body("fil5", "pag5", "fil_path5", 2, "data_fil8");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil9", "subverticals_with_others2", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 16)
		public void subverticals_with_others3()
		{
			body = universe_filter.array_body("fil6", "pag6", "fil_path6", 2, "data_fil10");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil11", "subverticals_with_others3", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 17)
		public void service_provider_partner_with_others1()
		{
			body = universe_filter.array_body("fil7", "pag7", "fil_path7", 2, "data_fil12");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil133", "service_provider_partner_with_others1", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 18)
		public void service_provider_partner_with_others2()
		{
			body = universe_filter.array_body("fil8", "pag8", "fil_path8", 2, "data_fil13");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil14", "service_provider_partner_with_others2", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 19)
		public void service_provider_partner_with_others3()
		{
			body = universe_filter.array_body("fil9", "pag9", "fil_path9", 2, "data_fil15");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil16", "service_provider_partner_with_others3", compare);
			System.out.println("#########################################");
		}
		@Test(priority = 20)
		public void rnd_location_with_others1()
		{
			body = universe_filter.array_body("fil10", "pag10", "fil_path10", 2, "data_fil17");
			JsonPath js = new JsonPath(body);
			String compare = js.getString("rowData[0]");
			universe_filter.dataValidation("data_fil18", "rnd_location_with_others1", compare);
			System.out.println("#########################################");
		}
	/*	@Test(priority = 21)
		public void rnd_location_with_others2()
		{
			universe_filter.status_check_200(prop.getProperty("fil11"), prop.getProperty("pag11"), prop.getProperty("res_common"), "rnd_location_with_others2");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil11"), prop.getProperty("pag11"), prop.getProperty("fil_path11"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}*/
		//@Test(priority = 22)
	/*	public void rnd_location_with_others3()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 23)
		public void hq_location_with_others1()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 24)
		public void hq_location_with_others2()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 25)
		public void hq_location_with_others3()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 26)
		public void outsourcing_size_with_others1()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 27)
		public void outsourcing_size_with_others2()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 28)
		public void outsourcing_size_with_others3()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 29)
		public void opp_index_with_others1()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 30)
		public void opp_index_with_others2()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}
		@Test(priority = 31)
		public void opp_index_with_others3()
		{
			universe_filter.status_check_200(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("res_common"), "service_provider_partner_with_others3");
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			universe_filter.array_body(prop.getProperty("fil7"), prop.getProperty("pag7"), prop.getProperty("fil_path7"), 2);
			
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
			universe_filter.dataValidation(prop.getProperty(""), compare, "");
		}*/
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
