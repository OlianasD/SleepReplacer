package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginUserTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.users;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		wait = new WebDriverWait(driver, 10);
		/*driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);*/
	}

	@Test
	@Parameters({"port", "host"})
	public void testCollabtiveLoginUser(String port, String host) throws Exception {
		int i = 0;
		//for(int i=0; i<40; i++) {
			driver.get("http://"+host+":" + port + "/collabtive/");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(data[i][0]);
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys(data[i][1]);
			driver.findElement(By.cssSelector("button.loginbutn")).click();
			driver.findElement(By.xpath("//*[@id=\"mainmenue\"]/li[2]/a")).click();
			assertTrue(driver.findElement(By.cssSelector("body")).getText().matches("^[\\s\\S]*"+data[i][0]+"[\\s\\S]*$"));
			driver.findElement(By.xpath("//*[@id='mainmenue']/li[3]/a")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
