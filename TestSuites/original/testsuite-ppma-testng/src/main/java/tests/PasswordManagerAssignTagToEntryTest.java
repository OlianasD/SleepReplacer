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

public class PasswordManagerAssignTagToEntryTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.assignTagToEntryEntries;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	@Parameters({"port", "host"})
	public void testPasswordManagerAssignTagToEntry(String port, String host) throws Exception {
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
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_name")).clear();
			driver.findElement(By.id("Entry_name")).sendKeys(data[i][0]);
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_username")).clear();
			driver.findElement(By.id("Entry_username")).sendKeys(data[i][1]);
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_password")).clear();
			driver.findElement(By.id("Entry_password")).sendKeys(data[i][2]);
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_tagList")).clear();
			driver.findElement(By.id("Entry_tagList")).sendKeys("");
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_url")).clear();
			driver.findElement(By.id("Entry_url")).sendKeys(data[i][3]);
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_comment")).clear();
			driver.findElement(By.id("Entry_comment")).sendKeys(data[i][4]);
			Thread.sleep(1000);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr/td[3]")).getText().contains(""));
			driver.navigate().refresh();
			builder = new Actions(this.driver);
			webElement = driver.findElement(By.linkText("Tags"));
			builder.moveToElement(webElement).perform();
			driver.findElement(By.linkText("Create")).click();
			driver.findElement(By.id("Tag_name")).clear();
			driver.findElement(By.id("Tag_name")).sendKeys(data[i][5]);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.xpath("//td[contains(text(), '"+data[i][5]+"')]/..//td[2]")).getText().contains("0"));
			driver.navigate().refresh();	
			driver.findElement(By.linkText("Entries")).click();
			driver.findElement(By.xpath("//a[contains(text(), '"+data[i][0]+"')]/../..//td[4]/a[4]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).clear();
			driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).sendKeys(data[i][5]);
			Thread.sleep(1000);
			driver.findElement(By.name("yt1")).click();
			assertTrue(driver.findElement(By.xpath("//a[contains(text(), '"+data[i][0]+"')]/../..//td[3]")).getText().contains(data[i][5]));
			driver.navigate().refresh();
			driver.findElement(By.linkText("Tags")).click();
			assertTrue(driver.findElement(By.xpath("//td[contains(text(), '"+data[i][5]+"')]/..//td[2]")).getText().contains("1"));
			driver.findElement(By.xpath("//td[contains(text(), '"+data[i][5]+"')]/..//td[3]/a[2]")).click();
			driver.findElement(By.name("yt0")).click();
			driver.navigate().refresh();
			driver.findElement(By.linkText("Entries")).click();
			Thread.sleep(1000);
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]")).getText().contains(data[i][5]));
			driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[4]/a[5]")).click();
			driver.switchTo().alert().accept();
			driver.navigate().refresh();
			driver.findElement(By.linkText("Profile")).click();
			driver.findElement(By.linkText("Logout")).click();
	}
	

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
