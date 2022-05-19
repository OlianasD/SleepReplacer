package tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EditRoleTest {

	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		wait = new WebDriverWait(driver, 10);
		/*driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);*/
		driver.get("http://"+host+":" + port + "/collabtive/");
	}

	@Test
	@Parameters({"port", "host"})
	public void testCollabtiveEditRole(String port, String host) throws Exception {
		//for(int i=0; i<40; i++) {
			driver.get("http://"+host+":" + port + "/collabtive/");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys("admin");
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys("admin");
			driver.findElement(By.cssSelector("button.loginbutn")).click();
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
			driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[2]/a")).click();
			driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[1]/td[2]/div/a")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("rolename")).clear();
			driver.findElement(By.id("rolename")).sendKeys("NewRole");
			driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[3]/div[3]/input")).click();
			driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[3]/div[5]/input")).click();
			driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[5]/button[1]")).click();
			assertEquals("NewRole",driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[1]/td[2]/div/a")).getText());
			driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[1]/td[2]/div/a")).click();
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
