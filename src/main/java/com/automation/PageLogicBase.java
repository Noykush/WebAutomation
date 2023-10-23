package com.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.LoggerFactory;

public abstract class PageLogicBase {
    protected WebDriver driver;
    private DriverManager driverManager;
    public ElementVerify elementVerify;
    public SeleniumUtils seleniumUtils;
    public PageLogicBase(String data_props_path){
        driverManager = DriverManager.getInstance(data_props_path);
        driver = driverManager.getDriver();
        elementVerify = new ElementVerify(driverManager);
        seleniumUtils = new SeleniumUtils(driverManager);
    }

    // common actions for all web pages
    public abstract void navigate(Boolean byUrl);

    public DriverManager getDriverManager() {
        return driverManager;
    }
}
