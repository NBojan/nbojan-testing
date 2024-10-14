import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ProjectsPageTest {
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
    public void getPage() { driver.get(utils.urlProjects); }

    @Test(testName = "017", priority = 1, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px() {
        Assert.assertFalse(driver.findElement(By.cssSelector("img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "018", priority = 2, groups = { }, enabled = true)
    public void VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px() {
        setWideScreen();
        Assert.assertTrue(driver.findElement(By.cssSelector("img[alt='Fancy Person']")).isDisplayed());
    }

    @Test(testName = "019", priority = 3, groups = { }, enabled = true)
    public void VerifyTheProjectsTitleIsNbWebProjects() {
        String correctTitle = "NB Web Projects";
        Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='NB Web Projects']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//h2[text()='NB Web Projects']")).getText(), correctTitle, "title does not match");
    }

    @Test(testName = "020", priority = 4, groups = { "smoke" }, enabled = true)
    public void VerifyTheProjectsAreDisplayed() {
        Assert.assertFalse(driver.findElements(By.cssSelector(".card")).isEmpty());
    }

    @Test(testName = "021", priority = 5, groups = { "smoke" }, enabled = true)
    public void VerifyTheFiltersAreDisplayed() {
        Assert.assertTrue(driver.findElement(By.cssSelector(".Filters__Wrapper-sc-uerh6j-0")).isDisplayed());
    }

    @Test(testName = "022", priority = 6, groups = { "smoke" }, enabled = true)
    public void ValidateTheFiltersAreWorkingByClickingOnAnyFilterExceptAll() throws InterruptedException {
        int numberOfProjects = driver.findElements(By.cssSelector(".card")).size();
        Thread.sleep(200);
        driver.findElement(By.xpath("//button[text()='next']")).click();
        Thread.sleep(500);
        int filteredNumberOfProjects = driver.findElements(By.cssSelector(".card")).size();
        Assert.assertNotEquals(numberOfProjects, filteredNumberOfProjects, "filter didn't work");
    }

    @Test(testName = "023", priority = 7, groups = { }, enabled = true)
    public void ValidateTheAllFilterDisplaysAllTheProjectsAfterUsingADifferentFilter() throws InterruptedException {
        int numberOfProjects = driver.findElements(By.cssSelector(".card")).size();
        Thread.sleep(200);
        driver.findElement(By.xpath("//button[text()='next']")).click();
        Thread.sleep(500);
        int filteredNumberOfProjects = driver.findElements(By.cssSelector(".card")).size();
        Assert.assertNotEquals(numberOfProjects, filteredNumberOfProjects, "filter didn't work");
        // using all filter
        driver.findElement(By.xpath("//button[text()='all']")).click();
        Thread.sleep(500);
        int usingAllFilterProjects = driver.findElements(By.cssSelector(".card")).size();
        Assert.assertEquals(numberOfProjects, usingAllFilterProjects, "ALL filter didn't work");
    }

    @Test(testName = "025", priority = 8, groups = { }, enabled = true)
    public void ValidateTheChosenFilterButtonHasAnActiveClass() throws InterruptedException {
        Thread.sleep(200);
        driver.findElement(By.xpath("//button[text()='next']")).click();
        String filterClasses = driver.findElement(By.xpath("//button[text()='next']")).getAttribute("class");
        Assert.assertTrue(filterClasses.contains("active"));
    }

    @Test(testName = "024", priority = 9, groups = { }, enabled = true)
    public void ValidateFilterResetsAfterSwitchingPageAndComingBack() throws InterruptedException {
        int numberOfProjects = driver.findElements(By.cssSelector(".card")).size();
        Thread.sleep(200);
        driver.findElement(By.xpath("//button[text()='next']")).click();
        Thread.sleep(500);
        int filteredNumberOfProjects = driver.findElements(By.cssSelector(".card")).size();
        Assert.assertNotEquals(numberOfProjects, filteredNumberOfProjects, "filter didn't work");

        driver.get(utils.urlAbout);
        Assert.assertTrue(driver.findElement(By.cssSelector(".about-one")).isDisplayed());
        driver.get(utils.urlProjects);

        int numberOfProjectsAfterReroute = driver.findElements(By.cssSelector(".card")).size();
        Assert.assertEquals(numberOfProjectsAfterReroute, numberOfProjects, "filter did not reset");
    }
}
