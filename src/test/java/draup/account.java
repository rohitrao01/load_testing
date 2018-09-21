package draup;

import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

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

public class account {
	
	static String Token_got = draup.token_gen.login();//this is getting token from test class
	static Properties prop  = new Properties();
	static String steps = "steps";
	{
		
	        System.setProperty("atu.reporter.config",System.getProperty("user.dir")+ File.separator + "src" + File.separator +"atu.properties");
	    
	}
	public WebDriver driver;
	
	  protected void setAuthorInfoF0orReports() {
	            ATUReports.setAuthorInfo("Rohit", Utils.getCurrentTime(),
	                    "1.0");
	            ATUReports.setWebDriver(driver);
	            ATUReports.indexPageDescription = "Draup Api Testing Report";
	            ATUReports.currentRunDescription = "Draup Api Testing Report";
	            
	        }
	static Response res;
	
	public static Response status_check_200(String Token_got,String resource)
	{
		
		RestAssured.baseURI = prop.getProperty("base_url");//baseurl from env file
		
		res = given()
		.header("Content-Type","application/json")//mandatory header of content type
		.header("Authorization", "bearer "+ Token_got )//mandatory header for token
		.when()
		.get(resource)//getting resource from files
		.then().assertThat().and().statusCode(200)
		.and().contentType(ContentType.JSON).and()
		.extract().response();
		
//		int stCode = res.getStatusCode();
//		if(stCode==200)
//		{
//			Assert.assertTrue(true);
//		}
//		if(stCode==404)
//		{
//			Assert.fail();
//		}
		return res;
	}
	public static String result_gen(String status, String url, String compare, String data, String methodName)
	{
		String test=
		("Status : " + status +"\n" +"Method Name : " + methodName + "\n" + "URL : " + url + "\n" + "Expected Output : "+compare +"\n"+ "Actual Output : "+data+"\n"+"----------------------------------");
		return test;
	}
	
	public static void dataValidation(String data,String compare,String methodName) {
		/*//data= prop.getProperty("data1");
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
				System.out.println(account.result_gen("Failed","http://dev-platform.draup.com/service/account/",compare, data, methodName));
				//Reporter.log(account.result_gen("Failed","http://dev-platform.draup.com//service/accounts/",compare, data, methodName));
				Assert.fail("Data Validation is failed for : "+methodName);
				}
			}*/
			
	/*		
		}
		
		if (temp==0)
		{	
			//System.out.println(data);
			System.out.println(account.result_gen("Passed","http://dev-platform.draup.com/service/account",compare, data, methodName));
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
		//String p=System.setProperty("System.getProperty("user.dir")+ File.separator + "src" + File.separator +"draup"+File.separator+"account_env_prop.properties")");
		String driverPath = System.getProperty("user.dir") +File.separator + "src" + File.separator + "test/java/draup"+File.separator+ "account_env_prop" ;
		//System.out.println(driverPath);	
		FileInputStream fis = new FileInputStream(driverPath);//source path of environment file
			prop.load(fis);//loading the file
		}
	
		@Test(priority = 1)
		public void get_accounts_all_signal() //first test case from swagger(GET/service/accounts/allsignals)
		{	
			 
			account.status_check_200(Token_got,prop.getProperty("res_accounts_all_signal"));
			String body = res.asString();//converting json to string
			JsonPath js = new JsonPath(body);
			 String compare = (js.getString("values[0].location")) ;//data at this address(can check on jsoneditoronline)
			 //System.out.println(compare);
			 
			 account.dataValidation(prop.getProperty("data1"), compare, "get_accounts_all_signal");
			 
			 account.negative_test_401(prop.getProperty("res_accounts_all_signal"));
			  	
			  }
			  
		@Test(priority = 2)
		public void get_account_signal_types()
		{
			account.status_check_200(Token_got, prop.getProperty("res_accounts_signal_types"));
			 String body = res.asString();
			 JsonPath js = new JsonPath(body);
			 String compare = js.getString("[0]");
			 //System.out.println(compare);
		 
			 account.dataValidation(prop.getProperty("body_text2"), compare, "get_account_signal_types");
			//negative method for token value is wrong
		  	 account.negative_test_401(prop.getProperty("res_accounts_signal_types"));
			 
		}
		@Test(priority = 3)
		public void get_account_name_suggestion()
		{
			account.status_check_200(Token_got, prop.getProperty("res_accounts_name_suggestions"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			String compare = js.getString("[0]");
			//System.out.println(compare);
			 
			account.dataValidation(prop.getProperty("body_text3"), compare, "res_accounts_name_suggestions");
			//negative method for token value is wrong
		  	 
			account.negative_test_401(prop.getProperty("res_accounts_name_suggestions"));
			 
		}
		@Test(priority = 4)
		public void get_account_location_suggestion()
		{
					account.status_check_200(Token_got, prop.getProperty("res_account_location_suggestions"));
					
					String body = res.asString();
					JsonPath js  = new JsonPath(body);
					String compare = js.getString("[0]");
					//System.out.println(compare);
					
					account.dataValidation(prop.getProperty("body_text4"), compare, "get_account_location_suggestion");

					account.negative_test_401(prop.getProperty("res_account_location_suggestions"));
					
	}
		//NOTE - GET /service/accounts/{id}/execs IS NOT BEING USED(SWAGGER>SERVICE>6TH API)
		@Test(priority = 5)
		public void get_service_accounts_financials_revenue_by_businessunits()
		{
			account.status_check_200(Token_got, prop.getProperty("revenue_by_businessunits_id"));
			
			 String body = res.asString();
			 JsonPath js = new JsonPath(body);
			 String compare = js.getString("data.series[0].name");
			 //System.out.println(compare);
			 
			 account.dataValidation(prop.getProperty("body_text5"), compare, "get_service_accounts_financials_revenue_by_businessunits");
			 
			 account.negative_test_401(prop.getProperty("revenue_by_businessunits_id"));
		}
		@Test(priority = 6)
		public void get_accounts_financials_revenue_by_region()
		{
			account.status_check_200(Token_got, prop.getProperty("revenue_by_region_id"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("data.series[0].name");
				//System.out.println(compare);
				
			account.dataValidation(prop.getProperty("body_text6"), compare, "get_accounts_financials_revenue_by_region");
				
			account.negative_test_401(prop.getProperty("revenue_by_region_id"));
		}		

		@Test(priority = 7)
		public void get_accounts_financials_revenue_rnd_timelines()
		{
			account.status_check_200(Token_got, prop.getProperty("revenue_rnd_timelines_id"));
					String body = res.asString();
					JsonPath js = new JsonPath(body);
					
					String compare = js.getString("data.series[0].data");
					//System.out.println(compare);

					account.dataValidation(prop.getProperty("body_text7"), compare, "get_accounts_financials_revenue_rnd_timelines");
					
					account.negative_test_401(prop.getProperty("revenue_rnd_timelines_id"));
			}
		@Test(priority = 8)
		public void get_account_financials_summary()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("financial_summary_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("revenue[0].values[0]");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text8"), compare, "get_account_financials_summary");
			
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("financial_summary_id"));
		}

		@Test(priority = 9)
		public void get_account_globalization_filter_locations()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("globalization_filter_location_id"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);
			
			account.dataValidation(prop.getProperty("body_text9"), compare, "get_account_globalization_filter_locations");
			
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("globalization_filter_location_id"));
		}
		
//			//NOTE - /service/accounts/{id}/globalization/filters/subverticals IS NOT SHOWING THE DATA
//			//NOTE - /service/accounts/{id}/globalization/manufacturing/location IS NOT SHOWING THE DATA
//			//NOTE - /service/accounts/{id}/globalization/manufacturing/map IS NOT SHOWING THE DATA
//			//NOTE - /service/accounts/{id}/globalization/manufacturing/summary IS NOT SHOWING THE DATA

		@Test(priority =  10)
		public void get_account_globalization_rnd_map()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("globalization_rnd_map_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("data[0].locationClass");
			//System.out.println(compare);
			
			account.dataValidation(prop.getProperty("body_text10"), compare, "get_account_globalization_rnd_map");

			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("globalization_rnd_map_id"));
		}
		@Test(priority = 11)
		public void get_account_globalization_rnd_sites_center()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("globalization_rnd_sites_center_id"));

			String body = res.asString();
			JsonPath js  = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text11"), compare, "get_account_globalization_rnd_sites_center");
			
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("globalization_rnd_sites_center_id"));
		}
			
			
		@Test(priority = 12)
		public void get_account_globalization_rnd_sites_details()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("globalization_rnd_sites_details_id"));			
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("business_units");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text12"), compare, "get_account_globalization_rnd_sites_details");
			
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("globalization_rnd_sites_details_id"));
		}	
			//NOTE - /service/accounts/{id}/globalization/rnd/sites/{site_id}/summary/ IS NOT ERROR
			
		@Test(priority = 13)
		public void get_accounts_globalization_rnd_sites_summary()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("globalization_rnd_sites_summary_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("[0].values[0]");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text13"), compare, "globalization_rnd_sites_summary_id");
		
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("globalization_rnd_sites_details_id"));
		}
		@Test(priority = 14)
		public void get_account_globalization_rnd_topography()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("globalization_rnd_topography_id"));
			
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("data[0].location_level_1");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text14"), compare, "get_account_globalization_rnd_topography");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("globalization_rnd_topography_id"));
		}	

		//NOTE - /service/accounts/{id}/hiring/activity/analysis/demand_by_subverticals IS NOT SHOWING NO CONTENT

		@Test(priority = 15)
			public void get_hiring_activity_analysis_most_in_demand_skills()
			{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("hiring_activity_analysis_most_in_demand_skills_id"));
			
			
			String body  = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("data[0].skill");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text15"), compare, "get_hiring_activity_analysis_most_in_demand_skills");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("hiring_activity_analysis_most_in_demand_skills_id"));
			}
		@Test(priority = 16)
		public void get_account_hiring_activity_filter_location()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("hiring_activity_filter_location_id"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("[6]");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text16"), compare, "get_account_hiring_activity_filter_location");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("hiring_activity_filter_location_id"));
		}

		@Test(priority = 17)
		public void get_account_hiring_activity_filters_subverticals()
		{

			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("hiring_activity_filters_subverticals_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text17"), compare, "get_account_hiring_activity_filters_subverticals");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("hiring_activity_filters_subverticals_id"));
		}

		@Test(priority = 18)
		public void get_account_hiring_job_listings()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_hiring_job_listings_id"));
			
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("values[0].job-description");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text18"), compare, "get_account_hiring_job_listings");
			
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_hiring_job_listings_id"));
		}
		@Test(priority = 19)
		public void get_account_hiring_job_listing_job_id()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("hiring_job_listing_job_id_id"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text19"), compare, "get_account_hiring_job_listing_job_id");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("hiring_job_listing_job_id_id"));
		}

		@Test(priority = 20)
		public void get_account_info()
		{

			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_info_id"));
			
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("account_id");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text20"), compare, "get_account_info");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_info_id"));
		}

//		@Test(priority = 21)
//		public void get_account_landingpage_business_units()
//		{
//			api_test.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("landingpage_business_units"));
//				String body = res.asString();
//			JsonPath js = new JsonPath(body);
//			
//			String compare = js.getString("data[0]");
//			//System.out.println(compare);
//
//			api_test.dataValidation(prop.getProperty("body_text21"), compare, "get_account_landingpage_business_units");
//			api_test.negative_test_401(prop.getProperty("res_common")+prop.getProperty("landingpage_business_units"), compare);
//		}

		@Test(priority = 22)
		public void get_accounts_landingpage_details()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("landingpage_details_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("key_executives");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text22"), compare, "get_accounts_landingpage_details");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("landingpage_details_id"));
		}
		@Test(priority = 23)
		public void get_account_landingpage_summary()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("landingpage_summary_id"));
			
			
			String body  = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("[0].values");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text23"), compare, "get_account_landingpage_summary");
			
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("landingpage_summary_id"));
		}

		@Test(priority = 24)
		public void get_account_outsourcing_accounts()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_outsourcing_accounts_id"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			//String compare = js.getString("rowData[0][1]");
			//System.out.println(compare);

			//account.dataValidation(prop.getProperty("body_text24"), compare, "get_account_outsourcing_accounts");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_outsourcing_accounts_id"));
		}

		@Test(priority = 25)
		public void get_account_outsourcing_analysis_filters_locations()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_analysis_filters_locations_id"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text25"), compare, "get_account_outsourcing_analysis_filters_locations");
		
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_analysis_filters_locations_id"));
		}
		@Test(priority = 26)
		public void get_account_outsourcing_analysis_filters_subverticals()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_analysis_filters_subverticals_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text26"), compare, "get_account_outsourcing_analysis_filters_subverticals");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_analysis_filters_subverticals_id"));
		}
		
		@Test(priority = 27)
		public void get_account_outsourcing_opindex_by_loc()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_opindex_by_loc"));
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("data.xAxis.categories");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text27"), compare, "get_account_outsourcing_opindex_by_loc");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_opindex_by_loc"));
		}

		@Test(priority = 28)
		public void get_account_outsourcing_opindex_by_subvertical()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_opindex_by_subvertical_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("data.xAxis.categories");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text28"), compare, "get_account_outsourcing_opindex_by_subvertical");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_opindex_by_subvertical_id"));
		}

		@Test(priority = 29)
		public void get_account_outsourcing_size_by_location()
		{
			account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_size_by_location_id"));
			
			String body = res.asString();
			JsonPath js = new JsonPath(body);
			
			String compare = js.getString("data.xAxis.categories");
			//System.out.println(compare);

			account.dataValidation(prop.getProperty("body_text29"), compare, "get_account_outsourcing_size_by_location");
			account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_size_by_location_id"));
		}

			@Test(priority = 30)
			public void get_account_outsourcing_size_by_service_provider()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_size_by_service_provider_id"));
				
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("data.xAxis.categories");
				//System.out.println(compare);

				account.dataValidation(prop.getProperty("body_text30"), compare, "get_account_outsourcing_size_by_service_provider");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_size_by_service_provider_id"));
			}

			@Test (priority = 31)
			public void get_account_outsourcing_size_by_subverticals()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("outsourcing_size_by_subverticals_id"));
				
				String body = res.asString();
				JsonPath  js = new JsonPath(body);
				
				String compare = js.getString("data.series[0].data");
				//System.out.println(compare);

				account.dataValidation(prop.getProperty("body_text31"), compare, "get_account_outsourcing_size_by_subverticals");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("outsourcing_size_by_subverticals_id"));
			}
			@Test(priority = 32)
			public void get_acccount_outsourcing_summary()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("acccount_outsourcing_summary_id"));
				
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("[1].values[0]");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text32"), compare, "get_acccount_outsourcing_summary");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("acccount_outsourcing_summary_id"));
			}
			@Test(priority = 33)
			public void get_account_partnership_companies()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_partnership_companies_id"));
				
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text33"), compare, "get_account_partnership_companies");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_partnership_companies_id"));
			}
			@Test(priority = 34)
			public void get_accounts_partnerships_investments()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("accounts_partnerships_investments_id"));
				
				String body = res.asString();
				JsonPath js =new JsonPath(body);
				
				String compare = js.getString("columnDefs[0]");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text34"), compare, "get_accounts_partnerships_investments");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("accounts_partnerships_investments_id"));
			}
			@Test(priority = 35)
			public void get_account_investment_summary()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_investment_summary_id"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("[0]");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text35"), compare, "get_account_investment_summary");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_investment_summary_id"));
			}
			@Test(priority = 36)
			public void get_account_partnership_investment_summary()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_partnership_investment_summary_id"));
				String body  = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("[0]");
				//System.out.println(compare);
			
				account.dataValidation(prop.getProperty("body_text36"), compare, "get_account_partnership_investment_summary");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_partnership_investment_summary_id"));
			}
			@Test(priority = 37)
			public void get_account_partnership_jointventure()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_partnership_jointventure_id"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text37"), compare, "get_account_partnership_jointventure");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_partnership_jointventure_id"));
			}
			@Test(priority = 38)
			public void get_account_partnership_merger()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_partnership_merger_id"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0][2]");
				//System.out.println(compare);
				
				//account.dataValidation(prop.getProperty("body_text38"), compare, "get_account_partnership_merger");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_partnership_merger_id"));
			}
			@Test(priority = 39)
			public void get_account_partnership_merger_summary()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_partnership_merger_summary_id"));
				
				String body  = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("[0].values[0]");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text39"), compare, "get_account_partnership_merger_summary");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_partnership_merger_summary_id"));
			}
			@Test(priority = 40)
			public void get_account_partnership_universities()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("account_partnership_universities_id"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("rowData[0]");
				//System.out.println(compare);
			
				account.dataValidation(prop.getProperty("body_text40"), compare, "get_account_partnership_universities");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("account_partnership_universities_id"));
			}
			@Test(priority = 41)
			public void get_accounts_signal()
			{
				account.status_check_200(Token_got, prop.getProperty("res_common")+prop.getProperty("accounts_signal_id"));
				String body = res.asString();
				JsonPath js = new JsonPath(body);
				
				String compare = js.getString("values[0].location");
				//System.out.println(compare);
				
				account.dataValidation(prop.getProperty("body_text41"), compare, "get_accounts_signal");
				account.negative_test_401(prop.getProperty("res_common")+prop.getProperty("accounts_signal_id"));
				
			}
			
			
}