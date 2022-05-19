package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.CommonData;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EditProjectTest {

	private WebDriver driver;
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
	}

	@Test
	@Parameters({"port", "host"})
	public void testCollabtiveEditProject(String port, String host) throws Exception {
		//for(int i=0; i<data.length; i++) {
		int i = 0;
			driver.get("http://"+host+":" + port + "/collabtive/");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys("admin");
			driver.findElement(By.id("pass")).clear();
			driver.findElement(By.id("pass")).sendKeys("admin");
			driver.findElement(By.cssSelector("button.loginbutn")).click();
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
			driver.findElement(By.xpath(".//a[@title='"+data[i][0]+"']/../../../td[5]/a[1]")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("end")).clear();
			driver.findElement(By.id("end")).sendKeys("24.12.2019");
			driver.findElement(By.linkText("Close")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id=\"form_addmyproject\"]/div/form/fieldset/div[6]/button[1]")).click();
			driver.findElement(By.id("edit_butn")).click();
			assertEquals("24.12.2019", driver.findElement(By.id("end")).getAttribute("value"));
			driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
