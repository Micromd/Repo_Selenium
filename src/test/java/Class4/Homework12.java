package Class4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Homework12 {

    /**
    * Testcase 1: Verify the feels-like temp value is between low and high temp values at any zipcode
    */

    /**
    *                             Manual test scenario
    * 1) Open darksky.net
    * 2) Retrieve information from fields:
    *      a) High
    *      b) Low
    *      c) Feels like
    * 3) Check if feels like value is within range of Low and High values.
    */
    @Test
    public void toCheckTemp (){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.darksky.net");
        WebElement highTemp = driver.findElement(By.xpath("//span[@class='high-temp-text']"));
        WebElement lowTemp = driver.findElement(By.xpath("//span[@class='low-temp-text']"));
        WebElement feelsLikeTemp = driver.findElement(By.xpath("//span[@class='feels-like-text']"));
        String highTempString = highTemp.getText();
        String lowTempString = lowTemp.getText();
        String feelsLikeTempString = feelsLikeTemp.getText();

        int high = Integer.parseInt(highTempString.replace(highTempString.substring(highTempString.length()-1),""));
        int low = Integer.parseInt(lowTempString.replace(lowTempString.substring(lowTempString.length()-1),""));
        int middle = Integer.parseInt(feelsLikeTempString.replace(feelsLikeTempString.substring(feelsLikeTempString.length()-1),""));
        boolean result = false;
        if (middle>=low && middle<=high) {
            result = true;
        }

        Assert.assertEquals(result,true, "Feels like temperature is not within low and high values");
        driver.quit();
    }


    /**
     * Testcase 2: Verify the dates on the Blog Page page appears in reverse chronological order
     *
     */

    /**
     *                             Manual test scenario
     * 1) Open darksky.net
     * 2) Click on "Blog" hyperlink to reach webpage with blog
     * 3) Retrieve information from all "published dates" fields on Webpage
     * 4) Compare dates and confirm that they appear in reverse chronological order
     */

    @Test
    public void toCheckDates(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.darksky.net");
        driver.findElement(By.xpath("//a[(text()='Blog')]")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList <Date> dates = new ArrayList();

        for (int i = 1; i<4; i++){
            //  loop should run i number of times. i is equal to number of articles we have on a page -1.
            //  Unfortunately, i didn't find a way to automate it, therefore loop limitations entered manually.
            // Please let me know how to automate this part of code.
            String index = String.valueOf(i);
            index = "[".concat(index).concat("]");
            WebElement pubDateWeb = driver.findElement(By.xpath("(//time[@itemprop ='datePublished'])".concat(index)));
                String pubDate = pubDateWeb.getText();
                Date myDate = null;
                SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");
                try {
                    myDate = format.parse(pubDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dates.add(myDate);
            }
        System.out.println(dates);
        boolean result = false;
        for (int i = 0; i< dates.size()-1; i++){
            if(dates.get(i).after(dates.get(i+1))){
                result = true;
            }
            else{
                result = false;
            }

        }
        driver.quit();
        Assert.assertEquals(result,true, "Dates in a blog page are not in reverse chronological order" );

    }


    /**
     * Testcase 3: Verify the temperature value converts as expected as the unit selected
     *
     */

    /**
     *                             Manual test scenario
     * 1) Open darksky.net
     * 2) Retrieve current temperature (In C or ˚F)
     * 3) Calculate expected converted value for C->˚F and for ˚F->C
     * 4) Click on unit change button
     * 5) Compare value on webpage with calculated value
     */

    @Test
    public void checkConversionFC(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.darksky.net");
        WebElement toMenu = driver.findElement(By.xpath("(//b[@class = 'button'])[1]"));
        WebElement toTurnF = driver.findElement(By.xpath("(//li[@data-index='0'])[1]"));
        WebElement toTurnC = driver.findElement(By.xpath("(//li[@class = 'last'])[1]"));
        WebElement retrieveTemp = driver.findElement(By.xpath("//span[@class='summary swap']"));
        toMenu.click();
        toTurnF.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String tempF = retrieveTemp.getText();
        String[] arr = tempF.split(" ");
        int tempFNum = Integer.parseInt(arr[0].replace(arr[0].substring(arr[0].length()-1),""));
        // Fahrenheit to Celsius. Formula  T(°C) = (T(°F) - 32)/1.8

        double cTempDouble = (tempFNum - 32.0)/1.8;
        //I don't know which rounding algorithm Darksky.net uses for rounding, therefore i went with standard  "Rounding  to the nearest integer". It may become a reason why test fails.
        int cTemp = (int)cTempDouble;
        double diff = cTempDouble - cTemp;
        if (diff>=0.5) {
            cTemp = cTemp + 1;
        }
        toMenu.click();
        toTurnC.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        retrieveTemp = driver.findElement(By.xpath("//span[@class='summary swap']"));
        String tempC = retrieveTemp.getText();
        String[] arr1 = tempC.split(" ");
        int tempCNum = Integer.parseInt(arr1[0].replace(arr1[0].substring(arr1[0].length()-1),""));
        driver.quit();
        Assert.assertEquals(tempCNum,cTemp, "Calculated value for F->C conversion is not equal to Webpage value");

    }

    @Test
    public void checkConversionCF(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.darksky.net");
        WebElement toMenu = driver.findElement(By.xpath("(//b[@class = 'button'])[1]"));
        WebElement toTurnF = driver.findElement(By.xpath("(//li[@data-index='0'])[1]"));
        WebElement toTurnC = driver.findElement(By.xpath("(//li[@class = 'last'])[1]"));
        WebElement retrieveTemp = driver.findElement(By.xpath("//span[@class='summary swap']"));
        toMenu.click();
        toTurnF.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String tempF = retrieveTemp.getText();
        String[] arr = tempF.split(" ");
        int tempFNum = Integer.parseInt(arr[0].replace(arr[0].substring(arr[0].length()-1),""));
        // Celsius to Fahrenheit. Formula T(°F) = T(°C) × 1.8 + 32
        toMenu.click();
        toTurnC.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        retrieveTemp = driver.findElement(By.xpath("//span[@class='summary swap']"));
        String tempC = retrieveTemp.getText();
        String[] arr1 = tempC.split(" ");
        int tempCNum = Integer.parseInt(arr1[0].replace(arr1[0].substring(arr1[0].length()-1),""));
        double fTempDouble = tempCNum * 1.8 + 32.0;
        //I don't know which rounding algorithm Darksky.net uses for rounding, therefore i went with standard  "Rounding  to the nearest integer". It may become a reason why test fails.
        int fTemp = (int)fTempDouble;
        double diff = fTempDouble - fTemp;
        if (diff>=0.5) {
            fTemp = fTemp + 1;
        }
        driver.quit();
        Assert.assertEquals(tempFNum,fTemp, "Calculated value for C->F conversion is not equal to Webpage value");


    }

}
