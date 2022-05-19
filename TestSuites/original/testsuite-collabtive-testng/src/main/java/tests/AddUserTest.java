package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddUserTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.users;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		wait = new WebDriverWait(driver, 10);
		/*driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);*/
	}

	@Test
	@Parameters({"port", "host"})
	public void testCollabtiveAddUser(String port, String host) throws Exception {
		int i =0;
		//for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":" + port + "/collabtive/");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys("admin");
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys("admin");
			driver.findElement(By.cssSelector("button.loginbutn")).click();
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
			driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[2]/a")).click();
			driver.findElement(By.id("add_butn_member")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("name")).clear();
			driver.findElement(By.id("name")).sendKeys(data[i][0]);
			driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys(data[i][2]);
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys(data[i][1]);
			Thread.sleep(1000);
			new Select(driver.findElement(By.id("roleselect"))).selectByIndex(1);
			driver.findElement(By.xpath("//*[@id='form_member']/div/form/fieldset/div[11]/div/button")).click();
			new Actions(driver).moveToElement(driver.findElement(By.xpath("(//table/tbody/tr[1]/td[2]/a/img)[1]"))).build().perform();
			driver.findElement(By.cssSelector("a.edit")).click();
			Thread.sleep(1000);
			assertEquals(data[i][0], driver.findElement(By.id("name")).getAttribute("value"));
			assertEquals(data[i][2], driver.findElement(By.id("email")).getAttribute("value"));
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
