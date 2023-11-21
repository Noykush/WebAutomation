package com.automation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class SeleniumUtils {
    WebDriverWait wait;
    WebDriver driver;
    EventFiringWebDriver eDriver;
    EventListener eventListener;

    public SeleniumUtils(DriverManager driverManager){
        wait = driverManager.getWait();
        driver =driverManager.getDriver();
        eDriver = driverManager.geteDriver();
        eventListener = driverManager.getEventListener();
    }
    /*  Selenium wrapper methods  */
    public boolean clickElement(WebElement e){
        try{
            scrollToElement(e);
            wait.until(ExpectedConditions.visibilityOf(e));
            e.click();
            System.out.println("clickElement - SUCCESS");
            return true;
        }catch (Exception exception) {
            System.out.println("clickElement -FAILURE \n"+exception.getMessage());
            return false;
        }
    }
    public void scrollToElement(WebElement e){
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Scrolling down the page till the element is found
            js.executeScript("arguments[0].scrollIntoView();", e);
        }catch (Exception exception){
            scrollToElementWithActions(e);
        }
    }
    private void scrollToElementWithActions(WebElement e){
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(e);
        }catch (Exception exception){
            System.out.println("scrollToElementWithActions -FAILURE \n"+exception.getMessage());
        }
    }

    public void waitForElement(WebElement e){
        try{
            wait.until(ExpectedConditions.visibilityOf(e));
            System.out.println("waitForElement - SUCCESS");
        }catch (Exception exception) {
            System.out.println("waitForElement -FAILURE \n"+exception.getMessage());
        }
    }

    public boolean isDisplayed(WebElement e){
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", e);
           e.isDisplayed();
            System.out.println("isDisplayed - SUCCESS");
            return true;
        }catch (Exception exception) {
            System.out.println("Element is Not displayed");
            return false;
        }
    }

    public void waitUntilElementIsGone(WebElement e){
        try{
            wait.until(ExpectedConditions.invisibilityOf(e));
            System.out.println("waitUntilElementIsGone - SUCCESS");
        }catch (Exception exception) {
            System.out.println("waitUntilElementIsGone -FAILURE \n"+exception.getMessage());
        }
    }
    public void closeDriver(){
        eDriver.unregister(eventListener);
        driver.close();
    }
    public void navigate_to(String url){
        driver.get(url);
    }


}
