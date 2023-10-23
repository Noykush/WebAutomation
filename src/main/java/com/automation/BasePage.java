package com.automation;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

abstract public class BasePage {
    WebDriverWait wait;
    public BasePage(DriverManager driverManager){
        System.out.println("Fdsf");

        wait = driverManager.getWait();
    }
    public Boolean assetInPage(WebElement elementToAssert){
        try {
            wait.until(ExpectedConditions.visibilityOf(elementToAssert));
            LoggerFactory.getLogger("BasePage").debug("success to assert element in page");
            return true;

        }catch (Exception e){
            LoggerFactory.getLogger("BasePage").debug("couldnt assert element in page");
            return false;
        }
    }
}
