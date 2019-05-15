package io.sibur.uiautotests.awaiter;

import org.openqa.selenium.support.How;
import org.openqa.selenium.WebElement;

/**
 * This interface to implement ability to await {@link WebElement} by given selector
 */
public interface Awaiter {

    void await(How how, String using) throws AwaiterException;
}
