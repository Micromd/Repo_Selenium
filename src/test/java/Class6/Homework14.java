package Class6;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Homework14 {
    WebDriver driver = new FirefoxDriver();
    JavascriptExecutor j1 = (JavascriptExecutor)driver;
    /**
     * Testcase-1: Verify low/high temp on Today timeline
     */
    @Test
    public void toCheckTemp (){

        driver.get("https://www.darksky.net");
        WebElement highTemp = driver.findElement(By.xpath("//span[@class='high-temp-text']"));
        WebElement lowTemp = driver.findElement(By.xpath("//span[@class='low-temp-text']"));
        String highTempString = highTemp.getText();
        String lowTempString = lowTemp.getText();
        int high = Integer.parseInt(highTempString.replace(highTempString.substring(highTempString.length()-1),""));
        int low = Integer.parseInt(lowTempString.replace(lowTempString.substring(lowTempString.length()-1),""));
        j1.executeScript("scrollBy(0,500);"); //Just for practice
        WebElement highTemp1 = driver.findElement(By.xpath("//a[@data-day='0']//span[@class='maxTemp']"));
        WebElement lowTemp1 = driver.findElement(By.xpath("//a[@data-day='0']//span[@class='minTemp']"));
        String highTempString1 = highTemp1.getText();
        String lowTempString1 = lowTemp1.getText();
        int high1 = Integer.parseInt(highTempString1.replace(highTempString1.substring(highTempString1.length()-1),""));
        int low1 = Integer.parseInt(lowTempString1.replace(lowTempString1.substring(lowTempString1.length()-1),""));


        Assert.assertEquals(high1,high, "Today's high temperature is not equal on different parts of the page");
        Assert.assertEquals(low1,low, "Today's low temperature is not equal on different parts of the page");
        driver.quit();
    }

    /**
     * Testcase-2: Verify the number of nights on black briefcase
     * checkin: tomorrow
     * checkout: 7 days from checkin date
     */
    @Test
    public void toCheckDates (){
        driver.get("https://www.hotels.com");
        Date todayDate = new Date();
        SimpleDateFormat d = new SimpleDateFormat("d");
        String day = d.format(todayDate);
        int tomorrow = Integer.valueOf(day) + 1;
        driver.findElement(By.id("qf-0q-localised-check-in")).click();
        List<WebElement> dates = driver.findElements(By.xpath("//td[starts-with(@data-date,'2020-11')]"));
        for (WebElement date : dates) {
            if (Integer.valueOf(date.getText())==tomorrow) {
                date.click();
                break;
            }
        }
        int numNightsPlanned = 7;
        driver.findElement(By.id("qf-0q-localised-check-out")).click();
        List<WebElement> datesOut = driver.findElements(By.xpath("//td[starts-with(@data-date,'2020-11')]"));
        for (WebElement date : datesOut) {
            if (Integer.valueOf(date.getText())==(tomorrow+numNightsPlanned)) {
                date.click();
                break;
            }
        }
        String nights = driver.findElement(By.xpath("//span[@class='widget-query-num-nights']")).getText();
        int numNights = Integer.valueOf(nights);
        Assert.assertEquals(numNights,numNightsPlanned, "Displayed number of nights is not equal actual number of nights");
        driver.quit();
    }



     /**
     * Testcase-3: Enter the user details as mentioned
     * Rooms - 1
     * Adult - 1
     * Children - 2 (Ages: 1, 2)
     * Verify user details on Search page as entered/selected on Homepage
     */

     @Test
     public void toCheckUserDetails () {
         driver.get("https://www.hotels.com");
         driver.findElement(By.id("qf-0q-destination")).clear();
         driver.findElement(By.id("qf-0q-destination")).sendKeys("Atlanta");
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         By autoSuggestionsLocator = By.xpath("//div[@class='autosuggest-category-result']");
         List<WebElement> suggestions = driver.findElements(autoSuggestionsLocator);
         for (WebElement suggestion : suggestions) {
             String suggestionText = suggestion.getText();
             if ("Atlanta, Georgia, United States of America".equalsIgnoreCase(suggestionText)) {
                 suggestion.click();
                 break;
             }
         }
         WebElement roomsE = driver.findElement(By.id("qf-0q-rooms"));
         WebElement adultsE = driver.findElement(By.id("qf-0q-room-0-adults"));
         WebElement childrenE = driver.findElement(By.id("qf-0q-room-0-children"));



         Select rooms = new Select(roomsE);
         Select adults = new Select(adultsE);
         Select children = new Select(childrenE);


         String numRooms = "1", numAdults = "1", numKids = "2", child1NumAge = "1", child2NumAge = "2";


         rooms.selectByVisibleText(numRooms);adults.selectByVisibleText(numAdults); children.selectByVisibleText(numKids);
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         WebElement children1AgeE = driver.findElement(By.id("qf-0q-room-0-child-0-age"));
         WebElement children2AgeE = driver.findElement(By.id("qf-0q-room-0-child-1-age"));
         Select children1Age = new Select(children1AgeE);
         Select children2Age = new Select(children2AgeE);
         children1Age.selectByVisibleText(child1NumAge); children2Age.selectByVisibleText(child2NumAge);
         driver.findElement(By.xpath("//button[@type='submit']")).click();
         try {
             Thread.sleep(3000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         String numRoomsS = driver.findElement(By.xpath("//select[@id='q-rooms']//option[@selected='selected']")).getText();
         String numAdultsS = driver.findElement(By.xpath("//select[@id='q-room-0-adults']//option[@selected='selected']")).getText();
         String numKidsS = driver.findElement(By.xpath("//select[@id='q-room-0-children']//option[@selected='selected']")).getText();
         String Child1NumAgeS = driver.findElement(By.xpath("//select[@id='q-room-0-child-0-age']//option[@selected='selected']")).getText();
         String Child2NumAgeS = driver.findElement(By.xpath("//select[@id='q-room-0-child-1-age']//option[@selected='selected']")).getText();

         Assert.assertEquals(numRoomsS,numRooms, "Number of rooms on a search page is not equal entered value");
         Assert.assertEquals(numAdultsS,numAdults, "Number of adults on a search page is not equal entered value");
         Assert.assertEquals(numKidsS,numKids, "Number of children on a search page is not equal entered value");
         Assert.assertEquals(Child1NumAgeS,child1NumAge, "Age of the first child on a search page is not equal entered value");
         Assert.assertEquals(Child2NumAgeS,child2NumAge, "Age of the second child on a search page is not equal entered value");
         driver.quit();
     }




}
