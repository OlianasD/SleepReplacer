package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordManagerAddMultipleEntriesTest {

	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	public void testPasswordManagerAddMultipleEntries() throws Exception {
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
		driver.findElement(By.id("Entry_name")).sendKeys("Google");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_username")).clear();
		driver.findElement(By.id("Entry_username")).sendKeys("myaccount1@google.it");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_password")).clear();
		driver.findElement(By.id("Entry_password")).sendKeys("mypassword1");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_tagList")).clear();
		driver.findElement(By.id("Entry_tagList")).sendKeys("Email, Google");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_url")).clear();
		driver.findElement(By.id("Entry_url")).sendKeys("www.google.it/mail");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_comment")).clear();
		driver.findElement(By.id("Entry_comment")).sendKeys("My personal email");
		driver.findElement(By.name("yt0")).click();
		driver.navigate().refresh();
		builder = new Actions(this.driver);
		webElement = driver.findElement(By.linkText("Entries"));
		builder.moveToElement(webElement).perform();
		driver.findElement(By.linkText("Create")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_name")).clear();
		driver.findElement(By.id("Entry_name")).sendKeys("Google");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_username")).clear();
		driver.findElement(By.id("Entry_username")).sendKeys("myaccount2@google.it");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_password")).clear();
		driver.findElement(By.id("Entry_password")).sendKeys("mypassword2");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_tagList")).clear();
		driver.findElement(By.id("Entry_tagList")).sendKeys("Email, Google");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_url")).clear();
		driver.findElement(By.id("Entry_url")).sendKeys("www.google.it/mail");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_comment")).clear();
		driver.findElement(By.id("Entry_comment")).sendKeys("My second personal email");
		Thread.sleep(1000);
		driver.findElement(By.name("yt0")).click();
		driver.navigate().refresh();
		builder = new Actions(this.driver);
		webElement = driver.findElement(By.linkText("Entries"));
		builder.moveToElement(webElement).perform();
		driver.findElement(By.linkText("Create")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_name")).clear();
		driver.findElement(By.id("Entry_name")).sendKeys("Google");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_username")).clear();
		driver.findElement(By.id("Entry_username")).sendKeys("myaccount3@google.it");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_password")).clear();
		driver.findElement(By.id("Entry_password")).sendKeys("mypassword3");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_tagList")).clear();
		driver.findElement(By.id("Entry_tagList")).sendKeys("Email, Google");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_url")).clear();
		driver.findElement(By.id("Entry_url")).sendKeys("www.google.it/mail");
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_comment")).clear();
		driver.findElement(By.id("Entry_comment")).sendKeys("My third personal email");
		Thread.sleep(1000);
		driver.findElement(By.name("yt0")).click();
		driver.navigate().refresh();
		Thread.sleep(2000);
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[2]")).getText().contains("myaccount1@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[3]")).getText().contains("Email, Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[2]")).getText().contains("myaccount2@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[3]")).getText().contains("Email, Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[2]")).getText().contains("myaccount3@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[3]")).getText().contains("Email, Google"));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();

	}

}
