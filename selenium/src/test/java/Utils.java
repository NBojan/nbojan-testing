import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class Utils {
    String url = "https://nbojan-gatsby.netlify.app/";
    String urlAbout = "https://nbojan-gatsby.netlify.app/about/";
    String urlProjects = "https://nbojan-gatsby.netlify.app/projects/";
    String urlQaProjects = "https://nbojan-gatsby.netlify.app/qaprojects/";
    String urlContact = "https://nbojan-gatsby.netlify.app/contact/";

    String featuredProjectsContainerLocator = "//h2[text()='featured projects']/ancestor::section";
    String featuredQaProjectsContainerLocator = "//h2[text()='featured QA projects']/ancestor::section";
    String nbPortfolioCardLocator = "//h3[text()='nb portfolio']/parent::header/parent::div/parent::div";
    String nbTwitterCardLocator = "//h3[text()='nb twitter']/parent::header/parent::div/parent::div";

    public Boolean isVisibleInViewport(WebElement element) {
        WebDriver driver = ((RemoteWebElement)element).getWrappedDriver();

        return (Boolean)((JavascriptExecutor)driver).executeScript(
                "var elem = arguments[0],                 " +
                        "  box = elem.getBoundingClientRect(),    " +
                        "  cx = box.left + box.width / 2,         " +
                        "  cy = box.top + box.height / 2,         " +
                        "  e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {         " +
                        "  if (e === elem)                        " +
                        "    return true;                         " +
                        "}                                        " +
                        "return false;                            "
                , element);
    }
}
