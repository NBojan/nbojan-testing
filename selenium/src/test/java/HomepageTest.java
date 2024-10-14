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

public class HomepageTest {
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
//     public void setup(){
//         driver = new ChromeDriver();
//         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//         setSmallScreen();
//     }
    @AfterTest
    public void cleanUp(){ driver.quit(); }

    @BeforeMethod
    public void getPage(){ driver.get(utils.url); }

    @Test(testName = "011", priority = 1, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px() {
        Assert.assertFalse(driver.findElement(By.cssSelector("img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "012", priority = 2, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px() {
        setWideScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector("img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "013", priority = 3, groups = { "smoke" }, enabled = true)
    public void VerifyTheFeaturedProjectsContainerIsDisplayed() {
        Assert.assertTrue(driver.findElement(By.xpath(utils.featuredProjectsContainerLocator)).isDisplayed());
    }

    @Test(testName = "014", priority = 4, groups = { "smoke" }, enabled = true)
    public void VerifyTheFeaturedQaProjectsContainerIsDisplayed() {
        Assert.assertTrue(driver.findElement(By.xpath(utils.featuredQaProjectsContainerLocator)).isDisplayed());
    }

    @Test(testName = "015", priority = 5, groups = { }, enabled = true)
    public void ValidateTheLinkInsideTheFeaturedProjectsContainerRedirectsToTheProjectsPage() {
        driver.findElement(By.xpath(utils.featuredProjectsContainerLocator + "//a[text()='See all projects']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='NB Web Projects']")).isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlProjects, "url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "016", priority = 6, groups = { }, enabled = true)
    public void ValidateTheLinkInsideTheFeaturedQaProjectsContainerRedirectsToTheQaProjectsPage() {
        driver.findElement(By.xpath(utils.featuredQaProjectsContainerLocator + "//a[text()='See all projects']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='NB Testing Projects']")).isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlQaProjects, "url is: " + driver.getCurrentUrl());
    }
}
