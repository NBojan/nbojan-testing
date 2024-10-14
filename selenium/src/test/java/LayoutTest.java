import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LayoutTest {
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
        setWideScreen();
    }
//    public void setup(){
//        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        setWideScreen();
//    }
    @AfterTest
    public void cleanUp(){ driver.quit(); }

    @BeforeMethod
    public void getPage(){ driver.get(utils.url); }

    @Test(testName = "001", priority = 1, groups = { "smoke" }, enabled = true)
    public void VerifyLogoIsDisplayedInTheHeader() {
        Assert.assertTrue(driver.findElement(By.cssSelector("img[alt='NBojan']")).isDisplayed());
    }

    @Test(testName = "002", priority = 2, groups = { "smoke" }, enabled = true)
    public void ValidateTheLogoIsALinkToTheHomepage() {
        driver.get(utils.urlAbout);
        Assert.assertTrue(driver.findElement(By.cssSelector(".about-one")).isDisplayed());
        driver.findElement(By.cssSelector("img[alt='NBojan']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Project page')]")).isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), utils.url, "url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "003", priority = 3, groups = { "smoke" }, enabled = true)
    public void VerifyTheHeaderLinksAreDisplayed() {
        Assert.assertTrue(driver.findElement(By.cssSelector("nav .links")).isDisplayed());
    }

    @Test(testName = "004", priority = 4, groups = { "smoke" }, enabled = true)
    public void VerifyTheLogoIsDisplayedOnScreenWidthBelow768px() {
        setSmallScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector("img[alt='NBojan']")).isDisplayed());
    }

    @Test(testName = "005", priority = 5, groups = { "smoke" }, enabled = true)
    public void VerifyTheHeaderLinksAreNotDisplayedOnScreenWidthBelow768px() {
        setSmallScreen();
        Assert.assertFalse(driver.findElement(By.cssSelector("nav .links")).isDisplayed());
    }

    @Test(testName = "006", priority = 6, groups = { "smoke" }, enabled = true)
    public void VerifyTheBurgerMenuIsDisplayedOnScreenWidthBelow768px() {
        setSmallScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector("nav .burger-btn")).isDisplayed());
    }

    @Test(testName = "007", priority = 7, groups = { "smoke" }, enabled = true)
    public void ValidateTheBurgerMenuButtonDisappearsAfterIncreasingScreenWidthAbove768px() {
        setSmallScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector("nav .burger-btn")).isDisplayed());
        setWideScreen();
        Assert.assertFalse(driver.findElement(By.cssSelector("nav .burger-btn")).isDisplayed());
    }

    @Test(testName = "008", priority = 8, groups = { "smoke" }, enabled = true)
    public void VerifyScrollToTopButtonIsInvisibleIfNoScrollApplied() {
        Assert.assertFalse(driver.findElement(By.cssSelector(".hideScroll")).isDisplayed());
    }

    @Test(testName = "009", priority = 9, groups = { "smoke" }, enabled = true)
    public void ValidateScrollToTopButtonIsVisibleIfScrolledToEndOfPage() {
        WebElement body = driver.findElement(By.tagName("body"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0, 1200)", "");
        body.sendKeys(Keys.ARROW_DOWN);
        Assert.assertTrue(driver.findElement(By.cssSelector(".showScroll")).isDisplayed());
    }

    @Test(testName = "010", priority = 10,  groups = { "smoke" }, enabled = true)
    public void ValidateScrollToTopScrollToTheTopOfThePage() throws InterruptedException {
        WebElement body = driver.findElement(By.tagName("body"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0, 1200)", "");
        body.sendKeys(Keys.ARROW_DOWN);
        Assert.assertTrue(driver.findElement(By.cssSelector(".showScroll")).isDisplayed());

        WebElement logo = driver.findElement(By.cssSelector("img[alt='NBojan']"));
        driver.findElement(By.cssSelector(".showScroll")).click();
        Thread.sleep(1000);
        Assert.assertTrue(utils.isVisibleInViewport(logo));
    }
}
