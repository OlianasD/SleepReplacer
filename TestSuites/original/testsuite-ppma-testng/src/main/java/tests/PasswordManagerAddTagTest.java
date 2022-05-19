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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordManagerAddTagTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.addTagTags;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	@Parameters({"port", "host"})
	public void testPasswordManagerAddTag(String port, String host) throws Exception {
		int i=0;
			driver.findElement(By.id("LoginForm_username")).clear();
			driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
			driver.findElement(By.id("LoginForm_password")).clear();
			driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
			driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
			Actions builder = new Actions(this.driver);
			WebElement webElement = driver.findElement(By.linkText("Tags"));
			builder.moveToElement(webElement).perform();
			driver.findElement(By.linkText("Create")).click();
			driver.findElement(By.id("Tag_name")).clear();
			driver.findElement(By.id("Tag_name")).sendKeys(data[i][0]);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr["+data[i][1]+"]/td[1]")).getText().contains(data[i][0]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr["+data[i][1]+"]/td[2]")).getText().contains("0"));
			driver.findElement(By.linkText("Profile")).click();
			driver.findElement(By.linkText("Logout")).click();

	}
	

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
