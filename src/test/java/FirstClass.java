import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstClass {

    @Test
    public void toCheckDate() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.darksky.net");
        JavascriptExecutor exec = (JavascriptExecutor)driver;
        exec.executeScript("scrollBy(0,700);");


        driver.quit();
    }
}
