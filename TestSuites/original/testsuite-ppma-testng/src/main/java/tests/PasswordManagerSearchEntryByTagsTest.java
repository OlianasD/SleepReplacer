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
public class PasswordManagerSearchEntryByTagsTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.entryTags;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		driver.get("http://"+host+":"
                + port + "/ppma/index.php");
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	@Parameters({"port", "host"})
	public void testPasswordManagerSearchEntryByTags(String port, String host) throws Exception {
		int i = 0;
			driver.get("http://"+host+":"
	                + port + "/ppma/index.php");
			driver.findElement(By.id("LoginForm_username")).clear();
			driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
			driver.findElement(By.id("LoginForm_password")).clear();
			driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
			driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
			driver.findElement(By.linkText("Advanced Search")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_tagList")).clear();
			driver.findElement(By.id("Entry_tagList")).sendKeys(data[i][0]);
			Thread.sleep(1000);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[1]")).getText().contains(data[i][1]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[2]")).getText().contains(data[i][2]));
			assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]")).getText().contains(data[i][3]));
			driver.findElement(By.linkText("Advanced Search")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("Entry_tagList")).clear();
			driver.findElement(By.id("Entry_tagList")).sendKeys("Email, Google");
			Thread.sleep(1000);
			driver.findElement(By.name("yt0")).click();
			assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
			driver.findElement(By.linkText("Profile")).click();
			driver.findElement(By.linkText("Logout")).click();
	}
	
	/*@DataProvider(name="entryTagProvider")
	public static Object[][] getEntries(ITestContext context) {
		return new Object[][] {
			{"Email", "Google", "myaccount@google.it","Email, Google"},
			{"Google", "Google", "myaccount@google.it","Email, Google"},
			{"Yahoo", "Yahoo", "myaccount@yahoo.com", "Yahoo, Zztag"},
			{"Yahoo1", "Yahoo1", "myaccount1@yahoo.com", "Yahoo1, Zztag1"},
			{"Yahoo2", "Yahoo2", "myaccount2@yahoo.com", "Yahoo2, Zztag2"},
			{"Yahoo3", "Yahoo3", "myaccount3@yahoo.com", "Yahoo3, Zztag3"},
			{"Yahoo4", "Yahoo4", "myaccount4@yahoo.com", "Yahoo4, Zztag4"},
			{"Yahoo5", "Yahoo5", "myaccount5@yahoo.com", "Yahoo5, Zztag5"},
			{"Yahoo6", "Yahoo6", "myaccount6@yahoo.com", "Yahoo6, Zztag6"},
			{"Yahoo7", "Yahoo7", "myaccount7@yahoo.com", "Yahoo7, Zztag7"},
			{"Zztag", "Yahoo", "myaccount@yahoo.com", "Yahoo, Zztag"},
			{"Zztag1", "Yahoo1", "myaccount1@yahoo.com", "Yahoo1, Zztag1"},
			{"Zztag2", "Yahoo2", "myaccount2@yahoo.com", "Yahoo2, Zztag2"},
			{"Zztag3", "Yahoo3", "myaccount3@yahoo.com", "Yahoo3, Zztag3"},
			{"Zztag4", "Yahoo4", "myaccount4@yahoo.com", "Yahoo4, Zztag4"},
			{"Zztag5", "Yahoo5", "myaccount5@yahoo.com", "Yahoo5, Zztag5"},
			{"Zztag6", "Yahoo6", "myaccount6@yahoo.com", "Yahoo6, Zztag6"},
			{"Zztag7", "Yahoo7", "myaccount7@yahoo.com", "Yahoo7, Zztag7"}
		};
	}*/

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
