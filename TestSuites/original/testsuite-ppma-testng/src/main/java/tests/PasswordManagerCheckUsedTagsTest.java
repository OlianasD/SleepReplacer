package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordManagerCheckUsedTagsTest {

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
	public void testPasswordManagerCheckUsedTags() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Entries")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[4]/a[4]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).clear();
		driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).sendKeys("");
		Thread.sleep(1000);
		driver.findElement(By.name("yt1")).click();
		driver.navigate().refresh();
		driver.findElement(By.linkText("Entries")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[4]/a[4]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).clear();
		driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).sendKeys("");
		Thread.sleep(1000);
		driver.findElement(By.name("yt1")).click();
		driver.navigate().refresh();
		driver.findElement(By.linkText("Entries")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[4]/a[4]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).clear();
		driver.findElement(By.xpath("(//input[@id='Entry_tagList'])[2]")).sendKeys("");
		Thread.sleep(1000);
		driver.findElement(By.name("yt1")).click();
		driver.navigate().refresh();
		driver.findElement(By.linkText("Tags")).click();
		Thread.sleep(2000);
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[2]")).getText().contains("0"));
		
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[2]")).getText().contains("0"));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
