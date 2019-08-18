package ui.autotests.testbase;

import ui.autotests.pages.YandexSearchPage;
import ui.autotests.pages.YandexSearchResultPage;
import org.testng.annotations.Test;

public class YandexSearchDemoTest extends TestSuite {

    @Test(retryAnalyzer = RetryAnalyzer.class, testName = "your TestRail testcase id")
    public void yandexSearchDemoTest(){
        browser.navigate("https://yandex.ru/");
        YandexSearchPage yandexSearchPage = new YandexSearchPage(browser);
        yandexSearchPage.isOnCurrentPage(YandexSearchPage.HOW, YandexSearchPage.USING);
        YandexSearchResultPage yandexSearchResultPage = yandexSearchPage.actions.searchByText("Сибур");
        yandexSearchResultPage.actions.checkSearchTopResult();
    }
}
