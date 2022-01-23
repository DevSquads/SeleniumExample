package tests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.config.parser.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class TestVodafoneLogin {

    public static final String ACCOUNT_ICON_TITLE = "My Profile";
    private static final String VODAFONE_TEST_URL = "https://web.vodafone.com.eg/en/home";
    public static final String PATH_TO_WEBDRIVER = "webdriver/chromedriver"; // chrome v97.0.4692
    private WebDriver driver;
	ConfigParser props;

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
        driver.get(VODAFONE_TEST_URL);
        props = new ConfigParser();
    }

    @Test
    public void testAccountLogin() throws IOException, InterruptedException {
        openLoginLayout();
        loginToAccount();
        assertUserDetailsExistsInPage();
        openUserProfileMenu();
        assertMyProfileIconTitleExsits();
    }

	private void assertMyProfileIconTitleExsits() {
		WebElement myProfileIconInlineText = waitForElementsToBeVisible(By.cssSelector(".nav-item-link.nav-user-list-link")).get(0);
        String myProfileIconString = myProfileIconInlineText.getText();
        assertEquals(myProfileIconString,ACCOUNT_ICON_TITLE);
	}

	private void assertUserDetailsExistsInPage() throws IOException {
		WebElement accountNumberTitle = waitForElementToBeVisible(By.id("account-switch-number"));
        String AccountHeadingTitle = accountNumberTitle.getText();
        assertTrue(AccountHeadingTitle.contains(props.getByKey("user")));
	}

	private void openUserProfileMenu() {
		WebElement myProfileIcon = waitForElementsToBeVisible(By.id("avatar")).get(0);
        myProfileIcon.click();
	}

    private void loginToAccount() throws InterruptedException, IOException {
    	  WebElement submitButton = waitForElementToBeVisible(By.id("submitBtn"));
    	  boolean isSubmitButtonDisplayed = submitButton.isDisplayed();
    	  if (isSubmitButtonDisplayed) {
	          WebElement loginNumberField = waitForElementToBeVisible(By.id("username"));
	          loginNumberField.sendKeys(props.getByKey("user"));
	          WebElement loginPasswordField = waitForElementToBeVisible(By.id("password"));
	          loginPasswordField.sendKeys(props.getByKey("password"));
	          loginPasswordField.sendKeys(Keys.RETURN);
    	  }
    }

    private void openLoginLayout() {
        WebElement loginLayoutBtn = waitForElementToBeVisible(By.id("loginIcon"));
        loginLayoutBtn.click();
    }

    private WebElement waitForElementToBeVisible(By selector) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(selector));
    }
    
    private List<WebElement> waitForElementsToBeVisible(By selector) {
    	WebDriverWait wait = new WebDriverWait(driver, 20);
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
    }
    @After
    public void teardown() throws IOException {
        driver.quit();
    }

}
