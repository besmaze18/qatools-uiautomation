package ui.autotests.pageactions;

import ui.autotests.browsers.Browser;
import ui.autotests.pages.PageBase;

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
