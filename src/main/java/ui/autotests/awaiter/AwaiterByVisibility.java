package ui.autotests.awaiter;

import org.openqa.selenium.WebDriver;

/**
 * Mechanism to await element visibility using Javascript file
 */
public class AwaiterByVisibility extends AwaiterBase {

    public AwaiterByVisibility(WebDriver driver) {
        super("src/main/resources/awaiter-script-byVisbility.js", driver);
    }
}
