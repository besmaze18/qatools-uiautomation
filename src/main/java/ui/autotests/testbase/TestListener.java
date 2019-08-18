package ui.autotests.testbase;

import ui.autotests.browsers.Browser;
import ui.autotests.browsers.WebDriverProperties;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import ru.yandex.qatools.properties.annotations.Resource;
import testrail.TestRail;
import testrail.TestRailProperties;
import testrail.yandexdrive.YandexApiServiceClient;
import testrail.yandexdrive.YandexApiServiceGenerator;
import testrail.yandexdrive.YandexApiUploadFilesServiceController;

/**
 * This class implements listener for test running
 */
@Resource.Classpath({"webdriver.properties", "testrail.properties", "postgresql.properties"})
public class TestListener implements ITestListener {

    private Browser browser;

    //get testrail properties
    private static TestRailProperties testRailProperties = new TestRailProperties();
    private static final String TESTRAIL_RUN = testRailProperties.getTestrailRun();

    //create yandex api service client
    private static final YandexApiServiceClient yandexApiServiceClient = YandexApiServiceGenerator.createService();

    //get webdriver properties
    private static final WebDriverProperties webDriverProperties = new WebDriverProperties();
    private static final String SCREENSHOT_PATH = webDriverProperties.getScreenshotPath();

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

    /**
     * Invoked each time before a test will be invoked.
     *
     * @param result the partially filled {@link ITestResult}
     */
    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("TEST '{}' EXECUTION STARTED", result.getTestContext().getName());
    }

    /**
     * Invoked each time a test succeeds.
     *
     * @param result {@link ITestResult} containing information about the run test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        TestRail apiCall = new TestRail();
        String[] caseIds = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class)
                .testName()
                .split(",");
        //adding pass result to TestRail
        try {
            apiCall.setCaseResult(caseIds, TESTRAIL_RUN, false, "", "");
            LOGGER.info("TEST '{}' PASSED", result.getMethod().getMethodName());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result {@link ITestResult} containing information about the run test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        browser = (Browser) result.getTestContext().getAttribute("browser");
        takeScreenshot(browser, result);
        LOGGER.info("TEST '{}' FAILED", result.getMethod().getMethodName());
    }

    /**
     * Invoked each time a test skipped.
     *
     * @param result {@link ITestResult} containing information about the run test
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        browser = (Browser) result.getTestContext().getAttribute("browser");
        takeScreenshot(browser, result);
        LOGGER.info("TEST '{}' SKIPPED", result.getMethod().getMethodName());
    }

    /**
     * Invoked each time a method fails but has been annotated with successPercentage and this failure still keeps it
     * within the success percentage requested.
     *
     * @param result {@link ITestResult} containing information about the run test
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //this method implementation is unnecessary
    }

    /**
     * Invoked after the test class is instantiated and before any configuration method is called.
     */
    @Override
    public void onStart(ITestContext context) {
        //using onTestStart instead of this method
    }

    /**
     * Invoked after all the tests have run and all their Configuration methods have been called.
     */
    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("TEST '{}' EXECUTION FINISHED", context.getName());
    }

    private void takeScreenshot(Browser browser, ITestResult iTestResult) {
        TestRail apiCall = new TestRail();
        String[] caseIds = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class)
                .testName()
                .split(",");
        try {
            File scrFile = browser.takeScreenShot();
            Date date = new Date();
            String strDateFormat = "hh.mm.ss";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String timeStamp = dateFormat.format(date);
            String fileName = iTestResult.getMethod().getMethodName();
            File targetFile = new File(
                    SCREENSHOT_PATH + fileName + timeStamp + ".png");
            FileUtils.copyFile(scrFile, targetFile);

            //save test result message to local variable
            String message = iTestResult.getThrowable().getMessage();

            //upload screenshot to Yandex Drive and return screenshot link
            YandexApiUploadFilesServiceController yandexApiUploadFilesServiceController = new YandexApiUploadFilesServiceController();
            String uploadUrl = yandexApiUploadFilesServiceController
                    .getDownloadLink(yandexApiServiceClient, fileName + timeStamp, targetFile);

            //add failed result to TestRail with screenshot link from Yandex Drive
            apiCall.setCaseResult(caseIds, TESTRAIL_RUN, true, uploadUrl, message);

            LOGGER.info("Successfully captured a screenshot");
        } catch (Exception e) {
            LOGGER.error("Exception while taking screenshot '{}'", e.getMessage());
        }
    }
}
