package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddProjectTest {

	private WebDriver driver;
	private String budgetXpath;
	private String[][] data;
	private WebDriverWait wait;
	
	
	@BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) throws Exception {
		data = CommonData.projects;
		driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
		wait = new WebDriverWait(driver, 10);
		/*driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);*/
		if(DriverProvider.getBrowser().equals("chrome")) {
			budgetXpath = "html/body/div[1]/div[2]/div[2]/div/div[1]/div[2]/ul/li[3]/a";
		}
		else {
			budgetXpath = "html/body/div[1]/div[2]/div[2]/div/div[1]/div[2]/ul/li[4]/a";
		}
		
	}
	@Test
	@Parameters({"port", "host"})
	public void testCollabtiveAddProject(String port, String host) throws Exception {
		int attempts = 0;
		int i = 0;
		//for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":" + port + "/collabtive/");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys("admin");
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys("admin");
			driver.findElement(By.cssSelector("button.loginbutn")).click();
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//*[@id='contentwrapper']/div[1]/ul/li/a")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.id("add_butn_myprojects")).click();
			Thread.sleep(500);
			driver.findElement(By.id("name")).clear();
			driver.findElement(By.id("name")).sendKeys(data[i][0]);
			driver.findElement(By.tagName("body")).click();
			Thread.sleep(500);
			driver.findElement(By.tagName("body")).sendKeys("Description for "+data[i][0]);
			driver.findElement(By.id("budget")).clear();
			driver.findElement(By.id("budget")).sendKeys("10000");
			Thread.sleep(500);
			driver.findElement(By.cssSelector("button[type='submit']")).click();
			Thread.sleep(1000);
			try {
				assertEquals(data[i][0], driver.findElement(By.linkText(data[i][0])).getText());
			} catch(NoSuchElementException e) {
				if(attempts < 3) {
					attempts++;
					i--;
				}
				else throw e;
			}
			driver.findElement(By.linkText(data[i][0])).click();
			Thread.sleep(1000);
			assertEquals("Budget: 10000",driver.findElement(By.xpath(budgetXpath)).getText());
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
