package io.sibur.uiautotests.testbase;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * This class implements ability to retry failed tests
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;

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
