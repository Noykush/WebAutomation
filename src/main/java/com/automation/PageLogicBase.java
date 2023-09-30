package com.automation;

import com.automation.ElementVerify;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.LoggerFactory;

public abstract class PageLogicBase {
    protected WebDriver driver;
    private DriverManager driverManager;
    public ElementVerify verifyElement;
    public PageLogicBase(String data_props_path, Long wait_sec,String logger_name){
        driverManager = DriverManager.getInstance(data_props_path, wait_sec);
        driver = driverManager.getDriver();
        verifyElement = new ElementVerify(driver, driverManager.getWait());
    }

    public abstract void navigate();



    /*  Selenium wrapper methods  */
    protected void clickElement(WebElement e){
        try{
            driverManager.getWait().until(ExpectedConditions.visibilityOf(e));
            e.click();
            LoggerFactory.getLogger("clickElement").debug("SUCCESS");
        }catch (Exception exception) {
            LoggerFactory.getLogger("clickElement").debug("FAILURE \n"+exception.getMessage());
        }
    }
    public void closeDriver(){
        driver.close();
    }
    public void navigate_to(String url){
        driver.get(url);
    }

}
