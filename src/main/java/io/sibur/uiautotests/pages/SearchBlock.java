package io.sibur.uiautotests.pages;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Block contains reusable Yandex search block
 */
@Name("Search Block")
@FindBy(xpath = "//form[contains(@class,'search2')]")
public class SearchBlock extends HtmlElement {

    @FindBy(xpath ="//div[@class='search2__input']//input[@class='input__control input__input']")
    public TextInput searchInput;

    @FindBy(xpath ="//div[@class='search2__button']/button")
    public TextInput searchButton;
}
