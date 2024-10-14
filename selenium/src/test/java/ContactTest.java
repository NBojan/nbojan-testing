import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ContactTest {
    WebDriver driver;
    Utils utils = new Utils();

    public void setWideScreen(){
        driver.manage().window().setSize(new Dimension(1400, 800));
    }
    public void setSmallScreen(){
        driver.manage().window().setSize(new Dimension(600, 800));
    }

    String validName = "Bojan";
    String validEmail = "bojan@live.com";
    String validMsg = "hello Sir";
    String urlFormspree = "https://formspree.io/thanks?language=en";

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
//        driver.manage().window().maximize();
//    }
    @AfterTest
    public void cleanUp() { driver.quit(); }

    @BeforeMethod
    public void getPage() {
        driver.get(utils.urlContact);
    }

    @Test(testName = "042", priority = 1, groups = { "smoke" }, enabled = true)
    public void VerifyContactFormIsDisplayed() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
    }

    @Test(testName = "043", priority = 2, groups = { "smoke" }, enabled = true)
    public void ValidateSubmittingTheFormAfterInputtingValidData() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(validName);
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(validEmail);
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(validMsg);
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//p[text()='The form was submitted successfully.']")).isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), urlFormspree, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "044", priority = 3, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterSubmittingWithoutFillingAnyField() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "045", priority = 4, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterSubmittingWithoutInputtingAName() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(validEmail);
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(validMsg);
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "046", priority = 5, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterSubmittingWithoutInputtingAnEmail() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(validName);
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(validMsg);
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "047", priority = 6, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterSubmittingWithoutInputtingAMessage() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(validName);
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(validEmail);
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "048", priority = 7, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInAllTheFields() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(" ");
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(" ");
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(" ");
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "049", priority = 8, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInTheNameField() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(" ");
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(validEmail);
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(validMsg);
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "050", priority = 9, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInTheEmailField() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(validName);
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(" ");
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(validMsg);
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

    @Test(testName = "051", priority = 10, groups = { }, enabled = true)
    public void ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInTheMessageField() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form")).isDisplayed());
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys(validName);
        driver.findElement(By.cssSelector("input[id='email']")).sendKeys(validEmail);
        driver.findElement(By.cssSelector("textarea[id='message']")).sendKeys(" ");
        driver.findElement(By.xpath("//button[text()='submit']")).click();

        Assert.assertTrue(driver.findElements(By.xpath("//p[text()='The form was submitted successfully.']")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), utils.urlContact, "current url is: " + driver.getCurrentUrl());
    }

}
