package io.sibur.uiautotests.pages;

import io.sibur.uiautotests.browsers.Browser;

/**
 * Base class for Pages and for blocks initialization
 */
public abstract class YandexMainPage extends PageBase{
    protected YandexMainPage(Browser browser) {
        super(browser);
    }

    public SearchBlock searchBlock;
}
