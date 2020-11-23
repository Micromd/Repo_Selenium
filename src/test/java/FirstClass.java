import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class FirstClass {

    @Test
    public void enterAddressInDarksky() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.darksky.net");
        // write code to remove the present text in search bar
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("10001");
        // write code to click on search icon
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("10001");

    }



}
