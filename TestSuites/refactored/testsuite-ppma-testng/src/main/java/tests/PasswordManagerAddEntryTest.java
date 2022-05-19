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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordManagerAddEntryTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.entries;
		System.out.println("Host: "+host+", port: "+port+", browserPort: "+browserPort);
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	@Parameters({"port", "host"})
	public void testPasswordManagerAddEntry(String port, String host) throws Exception {
		int i=0;

			driver.findElement(By.id("LoginForm_username")).clear();
			driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
			driver.findElement(By.id("LoginForm_password")).clear();
			driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
			driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
			Actions builder = new Actions(this.driver);
			WebElement webElement = driver.findElement(By.linkText("Entries"));
			builder.moveToElement(webElement).perform();
			driver.findElement(By.linkText("Create")).click();
			driver.findElement(By.id("Entry_name")).clear();
			driver.findElement(By.id("Entry_name")).sendKeys(data[i][0]);
			driver.findElement(By.id("Entry_username")).clear();
			driver.findElement(By.id("Entry_username")).sendKeys(data[i][1]);
			driver.findElement(By.id("Entry_password")).clear();
			driver.findElement(By.id("Entry_password")).sendKeys(data[i][2]);
			driver.findElement(By.id("Entry_tagList")).clear();
			driver.findElement(By.id("Entry_tagList")).sendKeys(data[i][3]);
			driver.findElement(By.id("Entry_url")).clear();
			driver.findElement(By.id("Entry_url")).sendKeys(data[i][4]);
			driver.findElement(By.id("Entry_comment")).clear();
			driver.findElement(By.id("Entry_comment")).sendKeys(data[i][5]);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr["+data[i][6]+"]/td[1]")).getText().contains(data[i][0]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr["+data[i][6]+"]/td[2]")).getText().contains(data[i][1]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr["+data[i][6]+"]/td[3]")).getText().contains(data[i][0]));
			driver.findElement(By.linkText("Profile")).click();
			driver.findElement(By.linkText("Logout")).click();

	}
	

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
