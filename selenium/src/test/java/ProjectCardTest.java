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
import java.util.Objects;

public class ProjectCardTest {
    WebDriver driver;
    Utils utils = new Utils();

    public void setWideScreen(){
        driver.manage().window().setSize(new Dimension(1400, 800));
    }
    public void setSmallScreen(){
        driver.manage().window().setSize(new Dimension(600, 800));
    }

    @BeforeTest
//    @Parameters("browser")
//    public void setup(String browser) throws Exception {
//        if (browser.equalsIgnoreCase("firefox")) {
//            driver = new FirefoxDriver();
//        } else if (browser.equalsIgnoreCase("chrome")) {
//            driver = new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("Edge")) {
//            driver = new EdgeDriver();
//        } else {
//            throw new Exception("Incorrect Browser");
//        }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        setSmallScreen();
//    }
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        setSmallScreen();
    }
    @AfterTest
    public void cleanUp() { driver.quit(); }

    @BeforeMethod
    public void getPage() throws InterruptedException {
        driver.get(utils.url);
        Thread.sleep(200);
    }

    @Test(testName = "031", priority = 1, groups = { "smoke" }, enabled = true)
    public void VerifyAWebProjectCardIncludesAnImageTitleDescrGithubAndPageLinkOnScreenWidthBelow768px() {
        WebElement webProjectCard = driver.findElement(By.xpath(utils.nbPortfolioCardLocator));
        Assert.assertTrue(webProjectCard.findElement(By.xpath("//img[@alt='Preview']")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector(".card-title")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector(".desc")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.xpath("//a[text()='Github']")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.xpath("//a[text()='Visit']")).isDisplayed());
    }

    @Test(testName = "032", priority = 2, groups = { "smoke" }, enabled = true)
    public void VerifyAQaProjectCardIncludesAnImageTitleDescrGithubLinkAndTypesOfTestingUsedOnScreenWidthBelow768px() {
        WebElement qaProjectCard = driver.findElement(By.xpath(utils.featuredQaProjectsContainerLocator + utils.nbTwitterCardLocator));
        Assert.assertTrue(qaProjectCard.findElement(By.xpath("//img[@alt='Preview']")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.cssSelector(".card-title")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.cssSelector(".desc")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.cssSelector("p.mb-1")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.xpath("//a[text()='Github']")).isDisplayed());
    }

    @Test(testName = "033", priority = 3, groups = { "smoke" }, enabled = true)
    public void VerifyAWebProjectCardIncludesAnImageTitleDescrGithubAndPageLink() {
        setWideScreen();
        WebElement webProjectCard = driver.findElement(By.xpath(utils.nbPortfolioCardLocator));
        Assert.assertTrue(webProjectCard.findElement(By.xpath("//img[@alt='Preview']")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector(".card-title")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector(".desc")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.xpath("//a[text()='Github']")).isDisplayed());
        Assert.assertTrue(webProjectCard.findElement(By.xpath("//a[text()='Visit']")).isDisplayed());
    }

    @Test(testName = "034", priority = 4, groups = { "smoke" }, enabled = true)
    public void VerifyAQaProjectCardIncludesAnImageTitleDescrGithubLinkAndTypesOfTestingUsed() {
        WebElement qaProjectCard = driver.findElement(By.xpath(utils.featuredQaProjectsContainerLocator + utils.nbTwitterCardLocator));
        Assert.assertTrue(qaProjectCard.findElement(By.xpath("//img[@alt='Preview']")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.cssSelector(".card-title")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.cssSelector(".desc")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.cssSelector("p.mb-1")).isDisplayed());
        Assert.assertTrue(qaProjectCard.findElement(By.xpath("//a[text()='Github']")).isDisplayed());
    }

    @Test(testName = "035", priority = 5, groups = { }, enabled = true)
    public void ValidateImageSwitchWhenClickingOnTheNextButton() throws InterruptedException {
        setWideScreen();
        WebElement webProjectCard = driver.findElement(By.xpath(utils.nbPortfolioCardLocator));
        String currentImage = webProjectCard.findElement(By.cssSelector("div.active img[alt='Preview']")).getAttribute("src");
        webProjectCard.findElements(By.cssSelector(".chev-btn")).getLast().click();
        Thread.sleep(200);
        String newImage = webProjectCard.findElement(By.cssSelector("div.active img[alt='Preview']")).getAttribute("src");
        Assert.assertFalse(Objects.equals(newImage, currentImage));
    }

    @Test(testName = "036", priority = 6, groups = { }, enabled = true)
    public void ValidateImageSwitchWhenClickingOnThePreviousButton() throws InterruptedException {
        WebElement webProjectCard = driver.findElement(By.xpath(utils.nbPortfolioCardLocator));
        String currentImage = webProjectCard.findElement(By.cssSelector("div.active img[alt='Preview']")).getAttribute("src");
        webProjectCard.findElements(By.cssSelector(".chev-btn")).getFirst().click();
        Thread.sleep(200);
        String newImage = webProjectCard.findElement(By.cssSelector("div.active img[alt='Preview']")).getAttribute("src");
        Assert.assertFalse(Objects.equals(newImage, currentImage));
    }

    @Test(testName = "037", priority = 7, groups = { }, enabled = true)
    public void ValidateClickingOnReadMoreOpensUpTheText() {
        WebElement webProjectCard = driver.findElement(By.xpath(utils.nbPortfolioCardLocator));
        webProjectCard.findElement(By.cssSelector(".read-more")).click();
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector("span.line-clamp-none")).isDisplayed());
        Assert.assertEquals(webProjectCard.findElement(By.cssSelector(".read-more")).getText(), "read less");
    }

    @Test(testName = "038", priority = 8, groups = { }, enabled = true)
    public void ValidateClickingOnReadLessShortensTheText() {
        WebElement webProjectCard = driver.findElement(By.xpath(utils.nbPortfolioCardLocator));
        webProjectCard.findElement(By.cssSelector(".read-more")).click();
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector("span.line-clamp-none")).isDisplayed());
        Assert.assertEquals(webProjectCard.findElement(By.cssSelector(".read-more")).getText(), "read less");

        webProjectCard.findElement(By.cssSelector(".read-more")).click();
        Assert.assertTrue(webProjectCard.findElement(By.cssSelector("span.line-clamp-2")).isDisplayed());
        Assert.assertEquals(webProjectCard.findElement(By.cssSelector(".read-more")).getText(), "read more");
    }
}
