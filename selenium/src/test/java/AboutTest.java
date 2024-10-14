import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AboutTest {
    WebDriver driver;
    Utils utils = new Utils();

    public void setWideScreen(){
        driver.manage().window().setSize(new Dimension(1400, 800));
    }
    public void setSmallScreen(){
        driver.manage().window().setSize(new Dimension(600, 800));
    }

    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) throws Exception {
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        } else {
            throw new Exception("Incorrect Browser");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        setSmallScreen();
    }
//    public void setup() {
//        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        setSmallScreen();
//    }
    @AfterTest
    public void cleanUp() { driver.quit(); }

    @BeforeMethod
    public void getPage() throws InterruptedException {
        driver.get(utils.urlAbout);
    }

    @Test(testName = "039", priority = 1, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px() {
        Assert.assertFalse(driver.findElement(By.cssSelector(".about-one img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "040", priority = 2, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px() {
        setWideScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector(".about-one img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "041", priority = 3, groups = { }, enabled = true)
    public void VerifyAboutMeTextIsDisplayed() {
        Assert.assertTrue(driver.findElement(By.cssSelector(".about-two .info-div")).isDisplayed());
    }
}
