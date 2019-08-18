package ui.autotests.pages;

import ui.autotests.browsers.Browser;

/**
 * Base class for Pages and for blocks initialization
 */
public abstract class YandexMainPage extends PageBase{
    protected YandexMainPage(Browser browser) {
        super(browser);
    }

    public SearchBlock searchBlock;
}
