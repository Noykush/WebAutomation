package com.automation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

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
    public void clickElement(WebElement e){
        try{
            scrollToElement(e);
            wait.until(ExpectedConditions.visibilityOf(e));
            e.click();
            System.out.println("clickElement - SUCCESS");
        }catch (Exception exception) {
            System.out.println("clickElement -FAILURE \n"+exception.getMessage());
        }
    }
    public void scrollToElement(WebElement e){
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Scrolling down the page till the element is found
            js.executeScript("arguments[0].scrollIntoView();", e);
        }catch (Exception exception){
            System.out.println("scrollToElement -FAILURE \n"+exception.getMessage());
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
    public void closeDriver(){
        eDriver.unregister(eventListener);
        driver.close();
    }
    public void navigate_to(String url){
        driver.get(url);
    }


}
