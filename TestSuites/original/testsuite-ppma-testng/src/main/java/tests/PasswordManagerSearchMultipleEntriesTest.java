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
public class PasswordManagerSearchMultipleEntriesTest {

	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"
                + port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	public void testPasswordManagerSearchMultipleEntries() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Advanced Search")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_name")).clear();
		driver.findElement(By.id("Entry_name")).sendKeys("Google");
		Thread.sleep(1000);
		driver.findElement(By.name("yt0")).click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.className("summary")).getText().contains("Displaying 1-3 of 3 results."));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[2]")).getText().contains("myaccount1@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[3]")).getText().contains("Email, Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[2]")).getText().contains("myaccount2@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[2]/td[3]")).getText().contains("Email, Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[2]")).getText().contains("myaccount3@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[3]/td[3]")).getText().contains("Email, Google"));
		driver.findElement(By.linkText("Advanced Search")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_username")).clear();
		driver.findElement(By.id("Entry_username")).sendKeys("myaccount1@google.it");
		driver.findElement(By.name("yt0")).click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.className("summary")).getText().contains("Displaying 1-1 of 1 result."));
		driver.findElement(By.linkText("Advanced Search")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_username")).clear();
		driver.findElement(By.id("Entry_username")).sendKeys("myaccount2@google.it");
		Thread.sleep(1000);
		driver.findElement(By.name("yt0")).click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.className("summary")).getText().contains("Displaying 1-1 of 1 result."));
		driver.findElement(By.linkText("Advanced Search")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Entry_username")).clear();
		driver.findElement(By.id("Entry_username")).sendKeys("myaccount3@google.it");
		Thread.sleep(1000);
		driver.findElement(By.name("yt0")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("summary")).click();
		assertTrue(driver.findElement(By.className("summary")).getText().contains("Displaying 1-1 of 1 result."));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
