package ui.autotests.pageactions;

import ui.autotests.browsers.Browser;
import ui.autotests.pages.YandexSearchResultPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * This class implements methods on Yandex search result page
 */
public class YandexSearchResultPageActions extends YandexMainPageActions<YandexSearchResultPage> {

    public YandexSearchResultPageActions(Browser browser, YandexSearchResultPage yandexSearchResultPage) {
        super(browser, yandexSearchResultPage);
    }

    public void checkSearchTopResult(){
        WebElement searchTopResult = page.searchResultList.getElementByNumber("li", 0);
        WebElement urlText = searchTopResult.findElement(By.cssSelector("div.organic__url-text"));
        Assert.assertTrue(urlText.getText().contains("СИБУР"), "Search top result is incorrect");
    }
}
