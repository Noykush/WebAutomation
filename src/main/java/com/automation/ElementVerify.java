package com.automation;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ElementVerify {
    private WebDriver driver;
    WebDriverWait wait;
    public ElementVerify(DriverManager driverManager){
        this.driver = driverManager.getDriver();
        this.wait = driverManager.getWait();
    }
    public void verifyElementText(String text, WebElement e){
        try {
            wait.until(ExpectedConditions.visibilityOf(e));
            Assert.isTrue(e.getText().equals( text),text+" is NOT appear in element!");
        }catch (Exception exception){
            System.out.println("verifyElementText FAILURE\n"+exception.getMessage());
        }
    }

}
