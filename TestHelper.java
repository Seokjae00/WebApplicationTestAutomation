import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestHelper {

    static WebDriver driver;

    String baseAdminUrl = "http://192.168.45.129:3000/admin";
    String baseUrl = "http://192.168.45.129:3000";

    @Before
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\selab\\Downloads\\edgedriver\\msedgedriver.exe");

        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    void goToPage(String page) {
        WebElement element = driver.findElement(By.linkText(page));
        element.click();
        waitForElementById(page);
    }

    void waitForElementById(String id) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    void waitForElementByXpath(String xpath) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    void adminLogin(String username, String password) {
        driver.get(baseAdminUrl);
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        By loginButtonXpath = By.xpath("//input[@value='Login']");
        driver.findElement(loginButtonXpath).click();
    }

    void adminLogout() {
        driver.findElement(By.linkText("Logout")).click();
        waitForElementById("Admin");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}