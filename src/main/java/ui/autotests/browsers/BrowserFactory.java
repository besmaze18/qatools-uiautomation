package ui.autotests.browsers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * A Browser Factory class that implements and controls {@link ChromeDriver}, {@link FirefoxDriver}, {@link
 * InternetExplorerDriver} and {@link SafariDriver} running on the local/remote machines.
 */
@Resource.Classpath("webdriver.properties")
public class BrowserFactory {

    private static final Logger logger = LoggerFactory.getLogger(Browser.class);

    private static WebDriverProperties webDriverProperties = new WebDriverProperties();
    private static String browserName = webDriverProperties.getBrowserType();
    private static String isRemoteProperty = webDriverProperties.getIsRemote();

    private static Map<String, WebDriver> drivers = new HashMap<>();
    private static final ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<>();

    /**
     * Get Webdriver by Browser name.
     *
     * @return Webdriver instance of given Browser.
     */
    public static WebDriver getDriver() throws MalformedURLException {

        switch (browserName) {
            case "Firefox":
                return startFirefox();
            case "IE":
                return startIE();
            case "Safari":
                return startSafari();
            default:
                return startChrome();
        }
    }

    /**
     * Get Browser.
     *
     * @return Browser instance.
     */
    public static Browser getBrowser() throws MalformedURLException {
        logger.info("Creating new browser of type '{}' isRemote:{}", browserName, isRemoteProperty);
        return new Browser(getDriver()) {
        };
    }

    /**
     * Start Chrome browser depending on parameters passed by webdriver.properties file.
     *
     * @return ChromeDriver or RemoteDriver depending on passed parameters.
     */
    public static WebDriver startChrome() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(webDriverProperties.getDriverAdditionalOptions());
        Boolean isRemote = Boolean.valueOf(isRemoteProperty);

        if (isRemote) {
            String remoteUrl = webDriverProperties.getRemoteUrl();
            DesiredCapabilities dc = DesiredCapabilities.chrome();
            dc.setCapability(ChromeOptions.CAPABILITY, options);
            URL url = new URL(remoteUrl);
            threadDriver.set(new RemoteWebDriver(url, DesiredCapabilities.chrome()));
            return threadDriver.get();
        }
        ChromeDriverService service = ChromeDriverService.createDefaultService();
        return new ChromeDriver(service, options);
    }

    /**
     * Start Firefox browser
     *
     * @return {@link FirefoxDriver}
     */
    public static WebDriver startFirefox() {
        String isRemoteProperty = webDriverProperties.getIsRemote();
        Boolean isRemote = Boolean.valueOf(isRemoteProperty);
        if (isRemote) {
            throw new NotImplementedException("remote ie browser is not currently supported");
        }
        return new FirefoxDriver(new FirefoxOptions());
    }

    /**
     * Start Safari browser
     *
     * @return {@link SafariDriver}
     */
    public static WebDriver startSafari() {
        String isRemoteProperty = webDriverProperties.getIsRemote();
        Boolean isRemote = Boolean.valueOf(isRemoteProperty);
        if (isRemote) {
            throw new NotImplementedException("remote safari browser is not currently supported");
        }
        return new SafariDriver();
    }

    /**
     * Start Internet Explorer browser
     *
     * @return {@link InternetExplorerDriver}
     */
    public static WebDriver startIE() {
        String isRemoteProperty = webDriverProperties.getIsRemote();
        Boolean isRemote = Boolean.valueOf(isRemoteProperty);
        if (isRemote) {
            throw new NotImplementedException("remote ie browser is not currently supported");
        }
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        return new InternetExplorerDriver(internetExplorerOptions);
    }

    /**
     * Dispose all drivers
     */
    public void dispose() {
        for (Entry<String, WebDriver> stringWebDriverEntry : drivers.entrySet()) {
            stringWebDriverEntry.getValue().close();
            stringWebDriverEntry.getValue().quit();
        }
    }
}
