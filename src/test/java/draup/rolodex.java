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

public class rolodex {
	static String steps = "steps";
	static String Token_got = draup.token_gen.login();//this is getting token from test class
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
	
	/*public static void dataValidation(String data,String compare,String methodName) {
		//data= prop.getProperty("data1");
		String[] arr = data.split(",");
		int temp = 0;
		if(org.apache.commons.lang3.StringUtils.isEmpty(compare) || compare.equals("[]")) 
		{
			
			ATUReports.add("Data Not Available", compare, data, false);
			System.out.println("Data Not Available");
			Assert.fail();
		}
		else
		{
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
		}
	}}
	*/
	public static void negative_test_401(String resource)
	{
		//negative method for token value is wrong
	  	  given()
		 .header("Content-Type", "application/json")
		 .header("Authorization", "bearer "+Token_got+"1")
		 .when()
		 .get(resource)//getting resource from files
		 .then().assertThat().statusCode(401);
		  System.out.println("401- negative test passed");
	}
	
	@BeforeTest
	public void env_run() throws IOException
	{
		String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "rolodex_env_prop" ;
		FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
		prop.load(fis);//loading the file
	}
	
	@Test(priority = 1)
	public void get_rolodex_ui_filter_bufunc()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_bufunc"));
		
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text1"), compare, "get_rolodex_ui_filter_bufunc");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_bufunc"));
	}
	
	@Test(priority = 2)
	public void get_rolodex_ui_filter_company()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_company"));
		
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text2"), compare, "get_rolodex_ui_filter_company");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_company"));
	}
	
	@Test(priority = 3)
	public void get_rolodex_ui_filter_level_in_org()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_level_in_org"));
		
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("");
		//System.out.println(compare);
		
	//	rolodex.dataValidation(prop.getProperty("body_text3"), compare, "get_rolodex_ui_filter_level_in_org");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_level_in_org"));
	}
	
	@Test(priority = 4)
	public void get_rolodex_ui_filter_locations()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_locations"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("");
		System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text4"), compare, "get_rolodex_ui_filter_locations");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filter_locations"));
	}
	
	@Test(priority = 5)
	public void get_rolodex_ui_filters_subverticals()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filters_subverticals"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text5"), compare, "get_rolodex_ui_filters_subverticals");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_ui_filters_subverticals"));
	}
	
	@Test(priority = 6)
	public void get_rolodex_associations()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_associations"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("associations[0]");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text6"), compare, "get_rolodex_associations");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_associations"));
	}
	
	@Test(priority = 7)
	public void get_rolodex_experience()
		{
			rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_experience"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("experience[0]");
			//System.out.println(compare);
			
		//	rolodex.dataValidation(prop.getProperty("body_text7"), compare, "get_rolodex_experience");
			rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_experience"));
		}
	@Test(priority = 8)
	public void get_rolodex_knowledge_wall()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_knowledge_wall"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("skills");
		//System.out.println(compare);
	//	rolodex.dataValidation(prop.getProperty("body_text8"), compare, "get_rolodex_knowledge_wall");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_knowledge_wall"));
	}
	@Test(priority = 9)
	public void get_rolodex_news()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_news"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("news[0]");
		//System.out.println(compare);
	
	//	rolodex.dataValidation(prop.getProperty("body_text9"), compare, "get_rolodex_news");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_news"));
	}
	@Test(priority = 10)
	public void get_rolodex_outsourcing()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_outsourcing"));
		
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("outsourcing_data");
		//System.out.println(compare);
		
	//	rolodex.dataValidation(prop.getProperty("body_text10"), compare, "get_rolodex_outsourcing");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_outsourcing"));
	}
	
	@Test(priority = 11)
	public void get_rolodex_executives()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("executives[0].executive_name");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text11"), compare, "get_rolodex_executives");
		rolodex.negative_test_401(prop.getProperty("res_common"));
	}
	
	@Test(priority = 12)
	public void get_rolodex_names_suggestion()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_names_suggestion"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("executives[0]");
		//System.out.println(compare);
		
	//	rolodex.dataValidation(prop.getProperty("body_text12"), compare, "get_rolodex_names_suggestion");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_names_suggestion"));
	}
	@Test(priority = 13)
	public void get_rolodex_exited_executives()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_exited_executives"));
		String body  = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("executives[0]");
		//System.out.println(compare);
	//	rolodex.dataValidation(prop.getProperty("body_text13"), compare, "get_rolodex_exited_executives");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_exited_executives"));
	}
	@Test(priority = 14)
	public void get_rolodex_hired_executive()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_hired_executive"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("executives[0]");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text14"), compare, "get_rolodex_hired_executive");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_hired_executive"));
	}
	@Test(priority = 15)
	public void get_rolodex_key_executives()
		{
			rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_key_executives"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("executives[0]");
			//System.out.println(compare);
			
			//rolodex.dataValidation(prop.getProperty("body_text15"), compare, "get_rolodex_key_executives");
			rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_key_executives"));
		}
	@Test(priority = 16)
	public void get_rolodex_executive_about()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_executive_about"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("about");
		//System.out.println(compare);
		
	//	rolodex.dataValidation(prop.getProperty("body_text16"), compare, "get_rolodex_executive_about");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_executive_about"));
	}
	@Test(priority = 17)
	public void get_rolodex_bio()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_bio"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		String compare = js.getString("bio");
		//System.out.println(compare);
		
	//	rolodex.dataValidation(prop.getProperty("body_text17"), compare, "get_rolodex_bio");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_bio"));
	}
	@Test(priority = 18)
	public void get_rolodex_blog()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_blog"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		//String compare = js.getString("blogs[0].blog_title");
		//System.out.println(compare);

		//rolodex.dataValidation(prop.getProperty("body_text18"), compare, "get_rolodex_blog");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_blog"));
	}
	@Test(priority = 19)
	public void get_rolodex_education()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_education"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		//String compare = js.getString("executive_education[0]");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text19"), compare, "get_rolodex_education");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_education"));
		}
	@Test(priority = 20)
	public void get_rolodex_influencer()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_influencer"));
		
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		//String compare = js.getString("peer_interactions[0]");
		//System.out.println(compare);
		
	//	rolodex.dataValidation(prop.getProperty("body_text20"), compare, "get_rolodex_influencer");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_influencer"));
	}
	
	@Test(priority = 21)
	public void get_rolodex_interest()
	{
		rolodex.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("res_rolodex_interest"));
		String body = res.asString();
		JsonPath js = new JsonPath(body);
		
		//String compare = js.getString("business_interests[0]");
		//System.out.println(compare);
		
		//rolodex.dataValidation(prop.getProperty("body_text21"), compare, "get_rolodex_interest");
		rolodex.negative_test_401(prop.getProperty("res_common")+prop.getProperty("res_rolodex_interest"));
	}
}
	
	


	
	
	
	
	
