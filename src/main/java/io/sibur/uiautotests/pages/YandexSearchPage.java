package io.sibur.uiautotests.pages;

import static org.openqa.selenium.support.How.XPATH;

import io.sibur.uiautotests.browsers.Browser;
import io.sibur.uiautotests.pageactions.YandexSearchPageActions;
import org.openqa.selenium.support.How;

/**
 * This class describes Yandex Search Page elements
 */
public class YandexSearchPage extends YandexMainPage {

    public YandexSearchPage(Browser browser) {
        super(browser);
        actions = new YandexSearchPageActions(this.browser, this);
    }

    public YandexSearchPageActions actions;

    /**
     * elements for page identification and page loading awaiter
     */
    public static final How HOW = XPATH;
    public static final String[] USING = {"//div[@class='home-arrow__tabs']",
            "//div[contains(@class,'container__heap container__line')]"};
}
