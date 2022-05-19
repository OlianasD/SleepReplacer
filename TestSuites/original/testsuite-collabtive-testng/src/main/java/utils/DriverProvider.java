package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverProvider {

	private static DriverProvider ourInstance = new DriverProvider();
	private static String browser;

    private DriverProvider(){
    	browser = "chrome";
    	if(browser.equals("chrome")) {
	    	System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
	    	//WebDriverManager.chromedriver().setup();
	        System.setProperty("webdriver.chrome.silentOutput", "true");
    	}
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
    }

    public WebDriver getDriver(){
    	if(browser.equals("chrome")) {
    		return getChrome();
    	}
    	else if(browser.equals("firefox")){
    		return getFirefox();
    	}
    	else {
    		return null;
    	}
    }

	private WebDriver getChrome() {
		ChromeOptions chromeOptions = new ChromeOptions();
		//chromeOptions.setBinary("/opt/google/chrome-beta/google-chrome-beta");
        //if(Boolean.valueOf(Properties.getInstance().getProperty("headless_browser"))){
            chromeOptions.addArguments("--no-sandbox", "--headless", "--disable-gpu", "--deterministic-mode", "--window-size=1200x600");
       // }
        return new ChromeDriver(chromeOptions);
	}
	
	private WebDriver getFirefox() {
		WebDriverManager.firefoxdriver().setup();

		FirefoxProfile profile = new FirefoxProfile();

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("marionette", true);
		firefoxOptions.setProfile(profile);

		
		firefoxOptions.setHeadless(true);

		return new FirefoxDriver(firefoxOptions);
	}
	
    public WebDriver getRemoteWebDriver(String port) {
    	/*ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox", "--headless", "--deterministic-mode", "--disable-gpu", "--window-size=1200x600");
        
        try {
        	return new RemoteWebDriver(new URL("http://localhost:"+port+"/wd/hub"), chromeOptions);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
        //*/
    	return getChrome();
    }

    public static DriverProvider getInstance(){
        return ourInstance;
    }
    
    public static String getBrowser() {
    	return browser;
    }
}
