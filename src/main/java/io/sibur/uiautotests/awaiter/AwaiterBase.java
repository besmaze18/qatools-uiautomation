package io.sibur.uiautotests.awaiter;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mechanism used to locate elements and await it visibility using Javascript
 */
public class AwaiterBase implements Awaiter {

    private static final Logger logger = LoggerFactory.getLogger(AwaiterBase.class);

    private String script;
    private WebDriver driver;

    protected AwaiterBase(String actionScript, WebDriver driver) {
        String defaultScript = getScript("src/main/resources/awaiter-scipt-generic.js");
        String convertedActionScript = getScript(actionScript);
        this.driver = driver;
        script = defaultScript + convertedActionScript;
    }

    /**
     * Get javascript file by file path
     *
     * @param filePath the path to Javascript file
     */
    public String getScript(String filePath) {
        File file = new File(filePath);
        String jsScript;
        try {
            jsScript = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return jsScript;
    }

    /**
     * await element display by executing javascript
     *
     * @param how the way to locate element
     * @param using the attribute value to locate element
     */
    @Override
    public void await(How how, String using) throws AwaiterException {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        if (jsExecutor == null) {
            throw new AwaiterException("Cannot execute js due to it not implementing {nameof(IJavaScriptExecutor)}");
        }
        Map<String, Boolean> result = (Map<String, Boolean>) doExecuteScript(how, using, jsExecutor, 0);
        Boolean actualResult = result.get("Result");
        if (!actualResult) {
            throw new AwaiterException("Result of element awaiting is invalid!");
        }
    }

    /**
     * Get result of executed Javascript
     *
     * @param how the way to locate element
     * @param using the attribute value to locate element
     * @param jsExecutor Javascript executor
     *
     * @return Javascript file execution result
     */
    private Object doExecuteScript(How how, String using, JavascriptExecutor jsExecutor, int attempts)
            throws AwaiterException {
        if (attempts++ > 10) {
            throw new InvalidArgumentException(
                    "Document unloaded exception occured 10 times! It is not supposed to occure so much.");
        }
        Object result;
        try {
            String howStr = how.toString();
            result = jsExecutor.executeAsyncScript(script, howStr, using);
        } catch (Exception ex) {
            if (ex.getMessage().contains("document unloaded while waiting for result")) {
                result = doExecuteScript(how, using, jsExecutor, attempts);
            } else {
                throw new AwaiterException("Result of element awaiting is invalid!");
            }
        }
        return result;
    }
}
