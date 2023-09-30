package com.automation;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Properties;

public class DriverManager {
    private static DriverManager driverManagerInstance = null;
    private  WebDriver driver = null;
    private WebDriverWait wait = null;
    private static final Long WAIT_SEC = 30L;
    private DriverManager(){handleDriverManager(null,null);}

    private DriverManager(String data_props_path){
        handleDriverManager(data_props_path,null);
    }
    private DriverManager( Long wait_sec){
        handleDriverManager(null,wait_sec);
    }
    private DriverManager(String data_props_path, Long wait_sec){
        handleDriverManager(data_props_path,wait_sec);
    }

    private void handleDriverManager(String data_props_path, Long wait_sec){
        Properties prop = new Properties();
        try {
            FileInputStream inputDriver = new FileInputStream(data_props_path == null ?System.getProperty("user.dir")+"\\src\\main\\resources\\data.props":data_props_path);
            prop.load(inputDriver);
            String browserName = prop.getProperty("browser");
            if (browserName.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_path"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setCapability("browserVersion", "117.0.5938.132");

                driver = new ChromeDriver(options);
            } else if (browserName.equals("firefox")) {
                //System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Mozilla Firefox\\default-browser-agent.exe");
                driver = new FirefoxDriver();
            } else {
                driver = null;
            }
            wait = new WebDriverWait(driver,wait_sec == null ?  Duration.ofSeconds(WAIT_SEC): Duration.ofSeconds(wait_sec));
            driver.manage().timeouts().implicitlyWait(Duration.ofDays(wait_sec == null ?  WAIT_SEC : wait_sec));
            LoggerFactory.getLogger("DriverManager").debug("success to create a webdriver");
        }
        catch (Exception e) {
            LoggerFactory.getLogger("DriverManager").debug("failed to create a webdriver");
        }
    }


    /**
     *
     * @param data_props_path data.props file contains the fields: browser and chromedriver_path or null if you use default
     * @param wait_sec Long type represent seconds for wait, if null - use default- 30L
     * @return WebDriver
     */
    public static DriverManager getInstance(String data_props_path, Long wait_sec) {
        if (driverManagerInstance == null) {       // first init
            driverManagerInstance = new DriverManager(data_props_path,wait_sec);
        }
        return driverManagerInstance;
    }

    public static DriverManager getInstance(Long wait_sec) {
        if (driverManagerInstance == null) {       // first init
            driverManagerInstance = new DriverManager(wait_sec);
        }
        return driverManagerInstance;
    }
    public static DriverManager getInstance(String data_props_path) {
        if (driverManagerInstance == null) {       // first init
            driverManagerInstance = new DriverManager(data_props_path);
        }
        return driverManagerInstance;
    }

    public static DriverManager getInstance() {
        if (driverManagerInstance == null) {       // first init
            driverManagerInstance = new DriverManager();
        }
        return driverManagerInstance;
    }

    public WebDriverWait getWait(){
        return wait;
    }

    public WebDriver getDriver() {
        return driverManagerInstance.driver;
    }
}
