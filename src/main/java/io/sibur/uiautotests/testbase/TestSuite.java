package io.sibur.uiautotests.testbase;

import static io.sibur.uiautotests.browsers.BrowserFactory.getBrowser;

import io.sibur.uiautotests.browsers.Browser;
import java.net.MalformedURLException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * This is the Base class for tests
 */
@Listeners(TestListener.class)
public abstract class TestSuite {

    protected Browser browser;

    /**
     * Initialize browser and maximize browser window before test executed
     *
     * @param context {@link ITestContext} to set browser attribute
     */
    @BeforeMethod
    public void testInitialize(ITestContext context) throws MalformedURLException {
        browser = getBrowser();
        browser.maximize();
        context.setAttribute("browser", browser);
    }

    /**
     * Quit driver after test executed
     */
    @AfterMethod
    public void disposeWebdriver() {
        browser.dispose();
    }
}
