package io.sibur.uiautotests.pageactions;

import io.sibur.uiautotests.browsers.Browser;
import io.sibur.uiautotests.pages.PageBase;

/**
 * Test project Page actions Base class
 */
public class YandexMainPageActions<T extends PageBase> implements PageActionsBase {

    protected T page;
    protected Browser browser;

    protected YandexMainPageActions(Browser browser, T page)
    {
        this.browser = browser;
        this.page = page;
    }
}
