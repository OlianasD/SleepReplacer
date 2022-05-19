package tests;

import static org.testng.AssertJUnit.assertTrue;
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

public class AddressBookRemoveAddressBookTest {

	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        wait = new WebDriverWait(driver, 10);
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("http://localhost:"+port+"/addressbook/index.php");
	}

	@Test
	public void testAddressBookRemoveAddressBook() throws Exception {
		driver.findElement(By.name("user")).sendKeys("admin");
		driver.findElement(By.name("pass")).sendKeys("secret");
		driver.findElement(By.xpath(".//*[@id='content']/form/input[3]")).click();
		driver.findElement(By.xpath("html/body/div[1]/div[4]/form[2]/table/tbody/tr[2]/td[1]/input")).click();
		driver.findElement(By.xpath("html/body/div[1]/div[4]/form[2]/div[2]/input")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.linkText("home")).click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]/label/strong")).getText().contains("Number of results: 0"));
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
