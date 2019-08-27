package ui.autotests.browsers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import ui.autotests.awaiter.AwaiterByVisibility;
import ui.autotests.pages.PageBase;

import java.io.File;

/**
 * This class encapsulates a {@link WebDriver} methods.
 */
public class Browser {

    protected WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(Browser.class);

    public Browser(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * The page class elements initialization.
     */
    public void init(PageBase pageObject) {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), pageObject);
    }

    /**
     * Quit Webdriver, close every associated window.
     */
    public void dispose() {
        logger.trace("Disposing of browser with current page title'{}'", driver.getTitle());
        driver.quit();
    }

    /**
     * Maximize browser window.
     */
    public void maximize() {
        logger.trace("Started maximizing window");
        driver.manage().window().maximize();
        logger.trace("Finished maximizing window");
    }

    /**
     * Take screenshot of current screen.
     */
    public File takeScreenShot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    /**
     * Refresh current screen.
     */
    public void refresh() {
        logger.trace("Started refresh on page with title '{}'", driver.getTitle());
        driver.navigate().refresh();
        logger.trace("Finished refresh on page with title '{}'", driver.getTitle());
    }

    /**
     * Navigate to a given Url.
     *
     * @param url the Url to navigate to.
     */
    public void navigate(String url) {
        logger.trace("Started navigate on page with url '{}'", url);
        driver.navigate().to(url);
        logger.trace("Finished navigate, now on page with title '{}' with url '{}'", driver.getTitle(),
                driver.getCurrentUrl());
    }

    /**
     * Get a string representing the current URL that the browser is looking at.
     *
     * @return The URL of the page currently loaded in the browser.
     */
    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Send future commands to a different frame or window.
     */
    public void switchTo() {
        logger.trace("Switching to other frame");
        driver.switchTo();
    }

    /**
     * Switch frame by id.
     */
    public void switchToFramebyId(int id) {
        logger.trace("Switching to other frame");
        driver.switchTo().frame(id);
    }

    /**
     * Switch frame by active element.
     */
    public void switchToFrameByActiveElement() {
        logger.trace("Switching to other frame");
        driver.switchTo().activeElement();
    }

    /**
     * Switches to the currently active modal dialog for this particular driver instance and accepts it.
     */
    public void alertAccept() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    /**
     * Switch to a frame using its previously located {@link WebElement}.
     *
     * @param inlineFrame The frame element to switch to.
     */
    public void switchToFrame(WebElement inlineFrame) {
        WebElement found = driver.findElement(By.id(inlineFrame.getAttribute("id")));
        driver.switchTo().frame(found);
    }

    /**
     * Switch to either the first frame on the page, or the main document when a page contains iframe.
     */
    public void switchToDefault() {

        driver.switchTo().defaultContent();
    }

    /**
     * Scroll to screen location using Javascript execution.
     *
     * @param xPosition The x coordinate to locate element.
     * @param yPosition The y coordinate to locate element.
     */
    public void scrollTo(int xPosition, int yPosition) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String js = "window.scrollTo({xPosition}, {yPosition})";
        javascriptExecutor.executeScript(js);
    }

    /**
     * Scroll to {@link WebElement}.
     *
     * @param element The WebElement scroll to.
     */
    public void scrollToView(WebElement element) {

        scrollTo(element.getLocation().x, element.getLocation().y - 100);
    }

    /**
     * Authenticate via passing username and password parameters to URL.
     *
     * @param userName The username parameter.
     * @param userPassword The password parameter.
     * @param baseUrl The base Url parameter
     */
    public void basicAuthentication(String userName, String userPassword, String baseUrl) {
        String url = "http://" + userName + ":" + userPassword + "@" + baseUrl;
        navigate(url);
    }

    /**
     * Check display and await element/elements using javascript by given selector
     *
     * @param how the way to locate element
     * @param usings the attribute value to locate element
     *
     * @return The boolean value of given elements display
     */
    public Boolean isOnPage(How how, String[] usings) {

        AwaiterByVisibility awaiter = new AwaiterByVisibility(driver);

        logger.trace("Started identity check on page by = '{}', using = '{}'", how, usings);
        for (String using : usings) {
            try {
                awaiter.await(how, using);
            } catch (Exception e) {
                logger.trace("Failed identity check on page by = '{}', using = '{}'", how, usings);
                return false;
            }
        }
        logger.trace("Successfully finished identity check on page by = '{}', using = '{}'", how, usings);
        return true;
    }
}

