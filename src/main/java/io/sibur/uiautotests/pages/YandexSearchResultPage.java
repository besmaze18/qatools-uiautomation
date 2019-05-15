package io.sibur.uiautotests.pages;

import static org.openqa.selenium.support.How.XPATH;

import io.sibur.uiautotests.browsers.Browser;
import io.sibur.uiautotests.customelements.ListElement;
import io.sibur.uiautotests.pageactions.YandexSearchResultPageActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 *  * This class describes Yandex Search Results Page elements
 */
public class YandexSearchResultPage extends YandexMainPage{
    public YandexSearchResultPage(Browser browser) {
        super(browser);
        actions = new YandexSearchResultPageActions(this.browser, this);
    }

    public YandexSearchResultPageActions actions;

    /**
     * elements for page identification and page loading awaiter
     */
    public static final How HOW = XPATH;
    public static final String[] USING = {"//ul[@aria-label='Результаты поиска']", "//div[@class='navigation__region']"};

    @FindBy(xpath = "//ul[@aria-label='Результаты поиска']")
    public ListElement searchResultList;
}
