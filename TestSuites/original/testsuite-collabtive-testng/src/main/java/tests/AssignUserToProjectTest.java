package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;


public class AssignUserToProjectTest {

	private WebDriver driver;
	private String[][] data;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.userProject;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		wait = new WebDriverWait(driver, 10);
		/*driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);*/
		driver.get("http://"+host+":" + port + "/collabtive/");
	}

	@Test
	@Parameters({"port", "host"})
	public void testCollabtiveAssignUserToProject(String port, String host) throws Exception {
		int i =0;
		//for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":" + port + "/collabtive/");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys("admin");
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys("admin");
			driver.findElement(By.cssSelector("button.loginbutn")).click();
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
			Thread.sleep(2000);
			driver.findElement(By.linkText(data[i][1])).click();
			driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[6]/a")).click();
			driver.findElement(By.id("add_butn_member")).click();
			Thread.sleep(3000);
			new Select(driver.findElement(By.id("addtheuser"))).selectByVisibleText(data[i][0]);
			Thread.sleep(1000);
			driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
			assertEquals(data[i][0], driver.findElement(By.partialLinkText(data[i][0])).getText());
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
