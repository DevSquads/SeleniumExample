import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;


import static org.junit.Assert.*;
public class TestGoolgeHomePage {
	    private static final String GOOGLE_TEST_URL = "https://www.google.com/?hl=en";
	    public static final String PATH_TO_WEBDRIVER = "webdriver/chromedriver";
	    private WebDriver driver;

	    @Before
	    public void prepare() {
	        System.setProperty(
	                "webdriver.chrome.driver",
	                PATH_TO_WEBDRIVER);
	        // to run tests in headless mode
	        // ChromeOptions chromeOptions = new ChromeOptions();
	        // chromeOptions.addArguments("--headless");
	        // driver = new ChromeDriver(chromeOptions);
	        driver = new ChromeDriver();
	        driver.get(GOOGLE_TEST_URL);
	    }

	    @Test
	    public void useGoogleSearchBox(){
	    	WebElement searchInputField = waitForElementToBeVisible(By.name("q"));
	    	searchInputField.sendKeys("Test Example By selenium");
	    	waitForElementToBeVisible(By.name("btnK")).click();
	    	String currentPageUrl = driver.getCurrentUrl();
	    	assertTrue(currentPageUrl.contains("https://www.google.com/search?"));
	    	assertTrue(currentPageUrl.contains("q=Test+Example+By+selenium"));
	    	assertEquals(driver.getTitle(),"Test Example By selenium - Google Search");
	    	
	    }
	    
	    private WebElement waitForElementToBeVisible(By selector) {
	        WebDriverWait wait = new WebDriverWait(driver, 60);
	        return wait.until(
	                ExpectedConditions.visibilityOfElementLocated(selector));
	    }
	    
	    
	    @After
	    public void teardown() throws IOException {
	        driver.quit();
	    }

	}
