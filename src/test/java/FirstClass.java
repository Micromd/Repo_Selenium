import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstClass {

    @Test
    public void enterAddressInDarksky() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        if(!driver.findElement(By.xpath("(//input[@type='radio'])[2]")).isSelected()){
            driver.findElement(By.xpath("(//input[@type='radio'])[2]")).click();
        }
        Assert.assertTrue(driver.findElement(By.xpath("(//input[@type='radio'])[2]")).isSelected());
    }



}
