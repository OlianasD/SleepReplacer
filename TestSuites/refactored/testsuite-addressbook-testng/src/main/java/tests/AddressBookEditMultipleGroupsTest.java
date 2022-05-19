package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddressBookEditMultipleGroupsTest {

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
	public void testAddressBookEditMultipleGroups() throws Exception {
		driver.findElement(By.name("user")).sendKeys("admin");
		driver.findElement(By.name("pass")).sendKeys("secret");
		driver.findElement(By.xpath(".//*[@id='content']/form/input[3]")).click();
		driver.findElement(By.linkText("groups")).click();
		driver.findElement(By.xpath(".//*[@id='content']/form/input[4]")).click();
		driver.findElement(By.xpath(".//*[@id='content']/form/input[9]")).click();
		driver.findElement(By.name("group_name")).clear();
		driver.findElement(By.name("group_name")).sendKeys("NewGroup1");
		driver.findElement(By.name("group_header")).clear();
		driver.findElement(By.name("group_header")).sendKeys("New Header1");
		driver.findElement(By.name("group_footer")).clear();
		driver.findElement(By.name("group_footer")).sendKeys("New Footer1");
		driver.findElement(By.name("update")).click();
		driver.findElement(By.linkText("group page")).click();
		driver.findElement(By.xpath(".//*[@id='content']/form/input[4]")).click();
		driver.findElement(By.xpath(".//*[@id='content']/form/input[9]")).click();
		driver.findElement(By.name("group_name")).clear();
		driver.findElement(By.name("group_name")).sendKeys("NewGroup2");
		driver.findElement(By.name("group_header")).clear();
		driver.findElement(By.name("group_header")).sendKeys("New Header2");
		driver.findElement(By.name("group_footer")).clear();
		driver.findElement(By.name("group_footer")).sendKeys("New Footer2");
		driver.findElement(By.name("update")).click();
		driver.findElement(By.linkText("group page")).click();	
		driver.findElement(By.xpath(".//*[@id='content']/form/input[4]")).click();
		driver.findElement(By.xpath(".//*[@id='content']/form/input[9]")).click();
		driver.findElement(By.name("group_name")).clear();
		driver.findElement(By.name("group_name")).sendKeys("NewGroup3");
		driver.findElement(By.name("group_header")).clear();
		driver.findElement(By.name("group_header")).sendKeys("New Header3");
		driver.findElement(By.name("group_footer")).clear();
		driver.findElement(By.name("group_footer")).sendKeys("New Footer3");
		driver.findElement(By.name("update")).click();
		driver.findElement(By.linkText("group page")).click();
		assertTrue(driver.findElement(By.xpath(".//*[@id='content']/form")).getText().contains("NewGroup1"));
		assertTrue(driver.findElement(By.xpath(".//*[@id='content']/form")).getText().contains("NewGroup2"));
		assertTrue(driver.findElement(By.xpath(".//*[@id='content']/form")).getText().contains("NewGroup3"));
		driver.findElement(By.linkText("home")).click();
wait.until(ExpectedConditions.elementToBeClickable(By.name("group")));

		new Select(driver.findElement(By.name("group"))).selectByVisibleText("NewGroup1");
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]")).getText().contains("New Header1"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]")).getText().contains("New Footer1"));
		new Select(driver.findElement(By.name("group"))).selectByVisibleText("NewGroup2");
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]")).getText().contains("New Header2"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]")).getText().contains("New Footer2"));
		new Select(driver.findElement(By.name("group"))).selectByVisibleText("NewGroup3");
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]")).getText().contains("New Header3"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]")).getText().contains("New Footer3"));
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
