package tests;

import static org.testng.AssertJUnit.assertTrue;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
public class PasswordManagerSearchTagsTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.tags;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	@Parameters({"port", "host"})
	public void testPasswordManagerSearchTags(String port, String host) throws Exception {
		int i = 0;
			driver.findElement(By.id("LoginForm_username")).clear();
			driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
			driver.findElement(By.id("LoginForm_password")).clear();
			driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
			driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
			driver.findElement(By.linkText("Tags")).click();
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[1]")).getText().contains("Email"));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[1]")).getText().contains("Google"));
			driver.findElement(By.linkText("Advanced Search")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("Tag_name")).clear();
			driver.findElement(By.id("Tag_name")).sendKeys(data[i][0]);
			Thread.sleep(1000);
			driver.findElement(By.linkText("Profile")).click();
			driver.findElement(By.linkText("Logout")).click();
	}
	
	
	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
