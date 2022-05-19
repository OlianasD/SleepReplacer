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
public class PasswordManagerSearchEntryByCommentTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.entryComments;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"+ port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	@Parameters({"port", "host"})
	public void testPasswordManagerSearchEntryByComment(String port, String host) throws Exception {
		int i =0;
			driver.findElement(By.id("LoginForm_username")).clear();
			driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
			driver.findElement(By.id("LoginForm_password")).clear();
			driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
			driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
			driver.findElement(By.linkText("Advanced Search")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_comment")).clear();
			driver.findElement(By.id("Entry_comment")).sendKeys(data[i][3]);
			Thread.sleep(1000);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[1]")).getText().contains(data[i][0]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[2]")).getText().contains(data[i][1]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]")).getText().contains(data[i][2]));
			driver.findElement(By.linkText("Profile")).click();
			driver.findElement(By.linkText("Logout")).click();

	}
	
	/*@DataProvider(name="entryProvider")
	public static Object[][] getEntries(ITestContext context) {
		return new Object[][] {
			{"Google", "myaccount@google.it",  "Email, Google", "My personal email"},
			{"Yahoo", "myaccount@yahoo.com",  "Yahoo, Zztag", "Comment1"},
			{"Yahoo1", "myaccount1@yahoo.com", "Yahoo1, Zztag1", "Comment2"},
			{"Yahoo2", "myaccount2@yahoo.com", "Yahoo2, Zztag2", "Comment3"},
			{"Yahoo3", "myaccount3@yahoo.com", "Yahoo3, Zztag3", "Comment4"},
			{"Yahoo4", "myaccount4@yahoo.com", "Yahoo4, Zztag4", "Comment5"},
			{"Yahoo5", "myaccount5@yahoo.com", "Yahoo5, Zztag5", "Comment6"},
			{"Yahoo6", "myaccount6@yahoo.com", "Yahoo6, Zztag6", "Comment7"},
			{"Yahoo7", "myaccount7@yahoo.com",  "Yahoo7, Zztag7", "Comment8"}
		};
	}*/

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
