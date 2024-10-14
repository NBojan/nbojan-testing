import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class QaProjectsPageTest {
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
    public void getPage() { driver.get(utils.urlQaProjects); }

    @Test(testName = "026", priority = 1, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px() {
        Assert.assertFalse(driver.findElement(By.cssSelector("img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "027", priority = 2, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px() {
        setWideScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector("img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "028", priority = 3, groups = { }, enabled = true)
    public void VerifyTheProjectsTitleIsNbTestingProjects() {
        String correctTitle = "NB Testing Projects";
        Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='NB Testing Projects']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//h2[text()='NB Testing Projects']")).getText(), correctTitle, "title does not match");
    }

    @Test(testName = "029", priority = 4, groups = { "smoke" }, enabled = true)
    public void VerifyTheProjectsAreDisplayed() {
        Assert.assertFalse(driver.findElements(By.cssSelector(".card")).isEmpty());
    }

    @Test(testName = "030", priority = 5, groups = { "smoke" }, enabled = true)
    public void VerifyTheFiltersAreNotDisplayed() {
        Assert.assertTrue(driver.findElements(By.cssSelector(".Filters__Wrapper-sc-uerh6j-0")).isEmpty());
    }
}
