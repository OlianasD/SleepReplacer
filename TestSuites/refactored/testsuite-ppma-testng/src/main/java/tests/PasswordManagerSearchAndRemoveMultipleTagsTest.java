package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordManagerSearchAndRemoveMultipleTagsTest {

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
	public void testPasswordManagerSearchAndRemoveMultipleTags() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Tags")).click();
		driver.findElement(By.linkText("Advanced Search")).click();
wait.until(ExpectedConditions.elementToBeClickable(By.id("Tag_name")));

		driver.findElement(By.id("Tag_name")).clear();
		driver.findElement(By.id("Tag_name")).sendKeys("Google");
wait.until(ExpectedConditions.elementToBeClickable(By.name("yt0")));

		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]/a[3]")).click();
		driver.switchTo().alert().accept();
		driver.navigate().refresh();
		driver.findElement(By.linkText("Advanced Search")).click();
wait.until(ExpectedConditions.elementToBeClickable(By.id("Tag_name")));

		driver.findElement(By.id("Tag_name")).clear();
		driver.findElement(By.id("Tag_name")).sendKeys("Email");
wait.until(ExpectedConditions.elementToBeClickable(By.name("yt0")));

		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]/a[3]")).click();
		driver.switchTo().alert().accept();
		driver.navigate().refresh();
		driver.findElement(By.linkText("Advanced Search")).click();
wait.until(ExpectedConditions.elementToBeClickable(By.id("Tag_name")));

		driver.findElement(By.id("Tag_name")).clear();
		driver.findElement(By.id("Tag_name")).sendKeys("Facebook");
wait.until(ExpectedConditions.elementToBeClickable(By.name("yt0")));

		driver.findElement(By.name("yt0")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]/a[3]")).click();
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
