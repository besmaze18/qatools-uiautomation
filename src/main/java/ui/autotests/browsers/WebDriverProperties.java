package ui.autotests.browsers;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * This class gets parameters from webdriver.properties file
 */
@Resource.Classpath("webdriver.properties")
public class WebDriverProperties {

    @Property("isRemote")
    private String isRemote;

    @Property("driverAdditionalOptions")
    private String driverAdditionalOptions;

    @Property("remoteUrl")
    private String remoteUrl;

    @Property("baseUrl")
    private String baseUrl;

    @Property("login")
    private String login;

    @Property("password")
    private String password;

    @Property("browserType")
    private String browserType;

    @Property("screenshotPath")
    private String screenshotPath;

    public WebDriverProperties() {

        PropertyLoader.populate(this);
    }

    /**
     * Get isRemote parameter value from webdriver.properties file
     *
     * @return String value of isRemote parameter
     */
    public String getIsRemote() {
        return isRemote;
    }

    /**
     * Get additional WebDriver options from webdriver.properties file
     *
     * @return String value of additional WebDriver options
     */
    public String getDriverAdditionalOptions() {
        return driverAdditionalOptions;
    }

    /**
     * Get URL of remote machine from webdriver.properties file
     *
     * @return String value of remote URL
     */
    public String getRemoteUrl() {
        return remoteUrl;
    }

    /**
     * Get Browser type from webdriver.properties file
     *
     * @return String value of Browser type
     */
    public String getBrowserType() {
        return browserType;
    }

    /**
     * Get base URL of app under test from webdriver.properties file
     *
     * @return String value of base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Get username for tested app from webdriver.properties file
     *
     * @return String value of username
     */
    public String getLogin() {
        return login;
    }

    /**
     * Get password for tested app from webdriver.properties file
     *
     * @return String value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get file path for screenshot save from webdriver.properties file
     *
     * @return String value of file path for screenshot save
     */
    public String getScreenshotPath() {
        return screenshotPath;
    }
}
