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

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordManagerRemoveTagTest {

	private WebDriver driver;
	private int numTags = 9;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}
	
	@Test
	public void testPasswordManagerRemoveTag() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Tags")).click();
wait.until(ExpectedConditions.elementToBeClickable(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[3]/a[3]")));

		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr[1]/td[3]/a[3]")).click();

		driver.switchTo().alert().accept();

		driver.navigate().refresh();
wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("empty")));

		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}
	
	/*@DataProvider(name="numTagsProvider")
	public static Object[][] getNumTags(ITestContext context) {
		return new Object[][] {
			{9}
		};
	}*/

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
