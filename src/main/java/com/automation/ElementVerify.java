package com.automation;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ElementVerify {
    private WebDriver driver;
    WebDriverWait wait;
    public ElementVerify(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    public void verifyElementText(String text, WebElement e){
        try {
            wait.until(ExpectedConditions.visibilityOf(e));
            Assert.assertEquals(e.getText(),text);
        }catch (Exception exception){
            LoggerFactory.getLogger("verifyElementText").debug("FAILURE\n"+exception.getMessage());
        }
    }
}
