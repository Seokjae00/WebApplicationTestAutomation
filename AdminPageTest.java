import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import static junit.framework.TestCase.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminPageTest extends TestHelper {
    private final String username = "admin";    // Admin username
    private final String password = "admin";    // Admin user password

    @Test
    public void Test01_titleExistsTest() {
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void Test02_adminLoginLogout() {
        adminLogin(username, password);
        goToPage("Admin");
        assertTrue(isElementPresent(By.id(username)));

        adminLogout();
        assertFalse(isElementPresent(By.id(username)));
    }

    @Test
    public void Test03_adminAccountRegister() {
        String registerUserName = "a";
        String registerPassword = "a";

        driver.get(baseAdminUrl);
        goToPage("Register");

        driver.findElement(By.id("user_name")).sendKeys(registerUserName);
        driver.findElement(By.id("user_password")).sendKeys(registerPassword);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(registerPassword);

        driver.findElement(By.name("commit")).click();

        goToPage("Admin");
        assertTrue(isElementPresent((By.id("a"))));
    }

    @Test
    public void Test04_deleteAccount() {
        String registerUserName = "a";
        String registerPassword = "a";

        adminLogin(registerUserName, registerPassword);
        goToPage("Admin");

        driver.findElement(By.linkText("Delete")).click();
        assertEquals("User was successfully deleted.", driver.findElement(By.id("notice")).getText());

        adminLogin(registerUserName, registerPassword);
        assertEquals("Invalid user/password combination", driver.findElement(By.id("notice")).getText());
    }

    @Test
    public void Test05_addNewProduct() {
        String title = "Product";
        String description = "Product";
        String prodType = "Sunglasses";
        String price = "10";

        adminLogin(username, password);

        driver.findElement(By.linkText("New product")).click();

        driver.findElement(By.id("product_title")).sendKeys(title);
        driver.findElement(By.id("product_description")).sendKeys(description);
        driver.findElement(By.id("product_prod_type")).sendKeys(prodType);
        driver.findElement(By.id("product_price")).sendKeys(price);
        driver.findElement(By.name("commit")).click();

        assertTrue(isElementPresent(By.id("Product")));
    }

    @Test
    public void Test06_editProduct() {
        String editTitle = "Pro";
        String editDescription = "Pro";
        String editProdType = "Other";
        String editPrice = "5";

        adminLogin(username, password);

        driver.findElement(By.xpath("//*[@id=\"Product\"]/td[3]/a")).click();
        driver.findElement(By.id("product_title")).clear();
        driver.findElement(By.id("product_description")).clear();
        driver.findElement(By.id("product_price")).clear();

        driver.findElement(By.id("product_title")).sendKeys(editTitle);
        driver.findElement(By.id("product_description")).sendKeys(editDescription);
        driver.findElement(By.id("product_prod_type")).sendKeys(editProdType);
        driver.findElement(By.id("product_price")).sendKeys(editPrice);
        driver.findElement(By.name("commit")).click();
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/a[2]")).click();

        assertEquals(editTitle, driver.findElement(By.xpath("//*[@id=\"Pro\"]/td[2]/a")).getText());
        assertEquals(editProdType, driver.findElement(By.xpath("//*[@id=\"Pro\"]/td[2]/div/span")).getText());
    }

    @Test
    public void Test07_deleteProduct() {
        adminLogin(username, password);

        By productXpath = By.xpath("//*[@id=\"Pro\"]");

        driver.findElement(By.xpath("//*[@id=\"Pro\"]/td[4]/a")).click();

        assertEquals("Product was successfully destroyed.", driver.findElement(By.id("notice")).getText());
        assertFalse(isElementPresent(productXpath));
    }
}
