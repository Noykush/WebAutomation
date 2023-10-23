package com.automation;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

 public class DriverManager {
    private static DriverManager driverManagerInstance = null;
    private  WebDriver driver = null;
    private WebDriverWait wait = null;
    private Long waitL;
    private EventFiringWebDriver eDriver;
    private EventListener eventListener;
    private static final Long WAIT_SEC = 30L;

    // -----Constructors ------//
    private DriverManager(){
        createWebDriver(null);
        createListeners();
        setWaits();
    }

    private DriverManager(String data_props_path){
        createWebDriver(data_props_path);
        createListeners();
        setWaits();
    }
    private void createListeners(){
        eDriver = new EventFiringWebDriver(driver);
        eventListener = new EventListener();
        eDriver.register(eventListener);
        driver = eDriver.getWrappedDriver();

    }
    private void setWaits(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitL));
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitL));

    }

    private Properties initDefaultProps() {
        Properties prop = new Properties();
        prop.setProperty("browser", SetUpConfig.BROWSER);
        prop.setProperty("chromedriver_path", SetUpConfig.CHROME_DRIVER_PATH);
        prop.setProperty("wait_sec", Long.toString(SetUpConfig.WAIT_SEC));
        return prop;
    }
    private void createWebDriver(String data_props_path){
        Properties prop = new Properties();
        try {
            if(data_props_path != null) {
                FileInputStream inputDriver = new FileInputStream(data_props_path);
                prop.load(inputDriver);
            }else{
                prop = initDefaultProps();
            }
            String browserName = prop.getProperty("browser");
            if (browserName.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_path"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                //options.setCapability("browserVersion", "117.0.5938.132");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
            } else if (browserName.equals("firefox")) {
                System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\default-browser-agent.exe");
                driver = new FirefoxDriver();
            } else {
                driver = null;
            }
            waitL = Long.parseLong(prop.getProperty("wait_sec"));
            LoggerFactory.getLogger("DriverManager").debug("success to create a webdriver");
        }
        catch (Exception e) {
            LoggerFactory.getLogger("DriverManager").debug("failed to create a webdriver");
        }
    }


    /**
     *
     * @param data_props_path data.props file contains the fields: browser and chromedriver_path or null if you use default
     * @return WebDriver
     */
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

    public EventFiringWebDriver geteDriver() {
        return eDriver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public EventListener getEventListener() {
        return eventListener;
    }
    public void close(){
        eDriver.unregister(eventListener);
        driver.close();
    }
}
