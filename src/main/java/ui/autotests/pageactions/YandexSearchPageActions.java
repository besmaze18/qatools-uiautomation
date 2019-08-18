package ui.autotests.pageactions;

import ui.autotests.browsers.Browser;
import ui.autotests.pages.YandexSearchPage;
import ui.autotests.pages.YandexSearchResultPage;

/**
 * This class implements methods on Yandex search page
 */
public class YandexSearchPageActions extends YandexMainPageActions<YandexSearchPage> {
    public YandexSearchPageActions(Browser browser, YandexSearchPage yandexSearchPage) {
        super(browser, yandexSearchPage);
    }

    public YandexSearchResultPage searchByText(String expectedText){
        page.searchBlock.searchInput.sendKeys(expectedText);
        page.searchBlock.searchButton.click();
        YandexSearchResultPage yandexSearchResultPage = new YandexSearchResultPage(browser);
        yandexSearchResultPage.isOnCurrentPage(YandexSearchResultPage.HOW, YandexSearchResultPage.USING);
        return yandexSearchResultPage;
    }
}
