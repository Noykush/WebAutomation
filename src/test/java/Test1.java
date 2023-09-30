import com.automation.DriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;


public class Test1 {
    @Test
    public void test1()  {
        WebDriver driver = DriverManager.getInstance().getDriver();
    }
}
