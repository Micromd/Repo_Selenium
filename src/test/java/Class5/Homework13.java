package Class5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework13 {
    /**
     * Testcase-1: User should be able to click on the yahoo notification
     * 1. Launch yahoo.com
     * 2. Move mouse to bell icon
     * 3. user should be able to click the first notification
    */

    @Test
    public void toCheckNotification() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.yahoo.com");
        WebElement bell = driver.findElement(By.xpath("//label[@for='ybarNotificationMenu']"));
        Actions act = new Actions(driver);
        act.moveToElement(bell).build().perform();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='yns-content']")).isEnabled(), "User unable to click on the first notification");
        driver.quit();
    }

    /**
     * Testcase-2: User should get error on invalid date of birth
     * 1. Launch facebook.com
     * 2. Click 'Create new Account' button
     * 3. Enter fname as Firstname
     * 4. Enter lname as Lastname
     * 5. "abcd@test.com" as email address
     * 6. "abcd@1234" as New Password
     * 7. Enter your "Jan 4 1998" as birth date
     * 8. Click the 'Sign Up' button
     * 9. Verify user see the error msg for gender. (Please choose a gender. You can change who can see this later.)
     */

    @Test
    public void toCheckError() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.facebook.com");
        driver.findElement(By.linkText("Create New Account")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@aria-label='First name']")).sendKeys("Firstname");
        driver.findElement(By.xpath("//input[@aria-label='Last name']")).sendKeys("Lastname");
        driver.findElement(By.xpath("//input[@aria-label='Mobile number or email']")).sendKeys("abcd@test.com");
        driver.findElement(By.xpath("//input[@aria-label='New password']")).sendKeys("abcd@1234");
        driver.findElement(By.xpath("//input[@aria-label='Re-enter email']")).sendKeys("abcd@test.com");
        WebElement monthE = driver.findElement(By.id("month"));
        WebElement dayE = driver.findElement(By.id("day"));
        WebElement yearE = driver.findElement(By.id("year"));
        Select month = new Select(monthE);
        Select day = new Select(dayE);
        Select year = new Select(yearE);
        month.selectByVisibleText("Jan"); day.selectByVisibleText("4"); year.selectByVisibleText("1998");
        driver.findElement(By.xpath("//button[text()='Sign Up' and @name='websubmit']")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = driver.findElement(By.xpath("//div[@class='uiContextualLayer uiContextualLayerLeft'] /following::div[text()='Please choose a gender. You can change who can see this later.']")).getText();

        Assert.assertEquals(text,"Please choose a gender. You can change who can see this later.", "Error message does not match");
        driver.quit();


    }





}
