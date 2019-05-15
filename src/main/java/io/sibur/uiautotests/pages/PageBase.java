package io.sibur.uiautotests.pages;

import io.sibur.uiautotests.browsers.Browser;
import org.openqa.selenium.support.How;

/**
 * This is Base class for Pages
 */
public abstract class PageBase {

    protected Browser browser;

    /**
     * {@link Browser} and Page initialization
     */
    protected PageBase(Browser browser) {
        this.browser = browser;
        this.browser.init(this);
    }

    /**
     * Check display and await element/elements using javascript by given selector
     *
     * @param how the way to locate element
     * @param using the attribute value to locate element
     *
     * @return The boolean value of given elements display
     */
    public Boolean isOnCurrentPage(How how, String[] using) {
        return browser.isOnPage(how, using);
    }
}
