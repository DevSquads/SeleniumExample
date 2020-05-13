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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TestVodafoneLogin {

    public static final String ACCOUNT_ICON_TITLE = "My Profile";
    public static final String ACCOUNT_PHONENUMBER = "01090614633";
    public static final String ACCOUNT_PASSWORD = "oNe_status_1";
    private static final String VODAFONE_TEST_URL = "https://web.vodafone.com.eg/en/home";
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
        driver.get(VODAFONE_TEST_URL);
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
		WebElement myProfileIconInlineText = waitForElementsToBeVisible(By.cssSelector(".navigation__link.navigation__link--secondary")).get(0);
        String myProfileIconString = myProfileIconInlineText.getText();
        assertEquals(myProfileIconString,ACCOUNT_ICON_TITLE);
	}

	private void assertUserDetailsExistsInPage() {
		WebElement accountNumberTitle = waitForElementToBeVisible(By.className("service-selector__active-number"));
        String AccountHeadingTitle = accountNumberTitle.getText();
        assertTrue(AccountHeadingTitle.contains(ACCOUNT_PHONENUMBER));
	}

	private void openUserProfileMenu() {
		WebElement myProfileIcon = waitForElementsToBeVisible(By.cssSelector(".js-navigation-item.navigation__item.navigation__item--right.js-navigation-item-clickable.navigation__item--clickable.user-menu")).get(0);
        myProfileIcon.click();
	}

    private void loginToAccount() throws InterruptedException {
          WebElement loginNumberField = waitForElementToBeVisible(By.id("username"));
          loginNumberField.sendKeys(ACCOUNT_PHONENUMBER);
          WebElement loginPasswordField = waitForElementToBeVisible(By.id("password"));
          loginPasswordField.sendKeys(ACCOUNT_PASSWORD);
          loginPasswordField.sendKeys(Keys.RETURN);
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
