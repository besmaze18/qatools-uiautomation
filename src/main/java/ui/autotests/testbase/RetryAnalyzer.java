package ui.autotests.testbase;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import ru.yandex.qatools.properties.annotations.Resource;
import ui.autotests.browsers.WebDriverProperties;

/**
 * This class implements ability to retry failed tests
 */
@Resource.Classpath("webdriver.properties")
public class RetryAnalyzer implements IRetryAnalyzer {

    //get webdriver properties
    private static final WebDriverProperties webDriverProperties = new WebDriverProperties();
    private static final int MAX_RETRY_COUNT = webDriverProperties.getRetryCount();

    private int retryCount = 0;

    /**
     * Retry failed test
     *
     * @param result test execution result
     *
     * @return boolean value depending on test retry result
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }
}
