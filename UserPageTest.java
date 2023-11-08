import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;

import java.util.List;

import static junit.framework.TestCase.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserPageTest extends TestHelper {

    @Test
    public void Test01_addCart() {
        String expectedProductName = driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/h3/a")).getText();
        String expectedProductPrice = driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/span")).getText();
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();

        assertEquals(expectedProductName, driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[2]")).getText());
        assertEquals(expectedProductPrice, driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]")).getText());
    }

    @Test
    public void Test02_increaseItem() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();

        driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[5]/a")).click();
        Thread.sleep(1000);
        assertEquals("2×", driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[1]")).getText());
    }

    @Test
    public void Test03_decreaseItem() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[5]/a")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[4]/a")).click();

        Thread.sleep(1000);
        assertEquals("1×", driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[1]")).getText());
    }

    @Test
    public void Test04_deleteItem() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"Sunglasses 2AR_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"Sunglasses B45593_entry\"]/div[2]/form/input[1]")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"delete_button\"]/a")).click();
        assertEquals("Item successfully deleted from cart.", driver.findElement(By.id("notice")).getText());

        List<WebElement> elements = driver.findElements(By.cssSelector("tr[class='cart_row']"));
        assertEquals(2, elements.size());

        assertFalse(isElementPresent(By.id("current_item")));
    }

    @Test
    public void Test05_emptyCart() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"Sunglasses 2AR_entry\"]/div[2]/form/input[1]")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"cart\"]/form[1]/input[2]")).click();
        assertFalse(isElementPresent(By.id("current_item")));
    }

    @Test
    public void Test06_searchBook() {
        String expectedProductTitle = "Web Application Testing Book";
        List<WebElement> elements = driver.findElements(By.xpath("//*[@class=\"entry\"]/h3/a"));

        for (WebElement element : elements){
            if(element.getText().equals(expectedProductTitle)) {
                driver.findElement(By.id("search_input")).sendKeys("book");
                assertEquals(expectedProductTitle, element.getText());
            }
        }

    }

    @Test
    public void Test07_existSunglassesCategory() {
        List<WebElement> mainElements = driver.findElements(By.xpath("//*[@class=\"entry\"]/h3/a"));
        long count = mainElements.stream().filter(element->element.getText().contains("Sunglasses")).count();

        goToPage("Sunglasses");
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"category\"]"));
        for (WebElement element : elements){
            assertEquals("Category: Sunglasses", element.getText());
        }

        assertEquals(count, elements.size());
    }

    @Test
    public void Test08_existBookCategory() {
        List<WebElement> mainElements = driver.findElements(By.xpath("//*[@class=\"entry\"]/h3/a"));
        long count = mainElements.stream().filter(element->element.getText().contains("Book")).count();

        goToPage("Books");
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"category\"]"));
        for (WebElement element : elements){
            assertEquals("Category: Books", element.getText());
        }

        assertEquals(count, elements.size());
    }

    @Test
    public void Test09_existOtherCategory() {
        List<WebElement> mainElements = driver.findElements(By.xpath("//*[@class=\"entry\"]/h3/a"));
        long count = mainElements.stream().filter(element->element.getText().contains("Other")).count();

        goToPage("Other");
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"category\"]"));
        for (WebElement element : elements){
            assertEquals("Category: Other", element.getText());
        }

        assertEquals(count, elements.size());
    }

    @Test
    public void Test10_checkOutItem() throws InterruptedException {
        String name = "AA";
        String address = "Seoul";
        String email = "AA@dgu.ac.kr";
        String payType = "Credit card";

        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[5]/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"checkout_button\"]/input")).click();
        Thread.sleep(1000);

        driver.findElement(By.name("order[name]")).sendKeys(name);
        driver.findElement(By.name("order[address]")).sendKeys(address);
        driver.findElement(By.name("order[email]")).sendKeys(email);
        driver.findElement(By.name("order[pay_type]")).sendKeys(payType);
        driver.findElement(By.name("commit")).click();

        assertEquals("€52.00", driver.findElement(By.xpath("//*[@id=\"check_out\"]/tbody/tr[2]/td[2]")).getText());
    }
}
