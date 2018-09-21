package draup;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class sel_my_account {
	
	@Test(priority = 1)
	public void run_driver() throws MalformedURLException
	{
		String driverPath = System.getProperty("user.dir") +File.separator + "geckodriver15"+File.separator +"geckodriver.exe";
		System.setProperty("webdriver.firefox.marionette",driverPath);
		WebDriver driver = new FirefoxDriver();
			
			System.setProperty("webdriver.gecko.driver", driverPath+"geckodriver");
	         URL serverurl = new URL("http://localhost:4444");
	         DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	         capabilities.setCapability("marionetee", true);
	         driver = new RemoteWebDriver(serverurl,capabilities);
	         driver.get("https://www.google.com");
	}
	
}
