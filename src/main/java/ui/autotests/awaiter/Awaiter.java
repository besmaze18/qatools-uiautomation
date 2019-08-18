package ui.autotests.awaiter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

/**
 * This interface to implement ability to await {@link WebElement} by given selector
 */
public interface Awaiter {

    void await(How how, String using) throws AwaiterException;
}
