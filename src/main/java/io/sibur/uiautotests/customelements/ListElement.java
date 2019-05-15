package io.sibur.uiautotests.customelements;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

/**
 * This class implements methods for {@link List} of {@link WebElement}
 */
public class ListElement extends TypifiedElement {
    public ListElement(WebElement wrappedElement) {
        super(wrappedElement);
    }

    /**
     * Get {@link WebElement} from {@link List} by element attribute.
     *
     * @param selector CSS selector value.
     * @param attribute the attribute value to get {@link WebElement} from List.
     * @param expectedText expected attribute value.
     *
     * @return {@link WebElement} by given attribute.
     */
    public WebElement getElementFromListByAttributeValue(String selector, String attribute, String expectedText) {
        List<WebElement> list = getList(selector);
        Optional<WebElement> element = list != null ? list.stream()
                .filter(x -> x.getAttribute(attribute).contains(expectedText))
                .findFirst() : Optional.empty();
        return element.orElse(null);
    }

    /**
     * Get element from {@link List} by {@link WebElement} text value.
     *
     * @param selector CSS selector value.
     * @param expectedText expected {@link WebElement} text value.
     *
     * @return {@link WebElement} by given text value.
     */
    public WebElement getElementFromListByTextValue(String selector, String expectedText) {
        List<WebElement> list = getList(selector);
        Optional<WebElement> element = list != null ? list.stream()
                .filter(x -> x.getText().equalsIgnoreCase(expectedText))
                .findFirst() : Optional.empty();
        return element.orElse(null);
    }

    /**
     * Get {@link WebElement} from {@link List} by number
     *
     * @param selector CSS selector value.
     * @param expectedNumber the int value of {@link WebElement} from {@link List}
     */
    public WebElement getElementByNumber(String selector, int expectedNumber) {
        List<WebElement> list = getList(selector);
        return list != null ? list.get(expectedNumber) : null;
    }

    /**
     * Get {@link List} by CSS selector.
     *
     * @param selector CSS selector value.
     *
     * @return {@link List} of {@link WebElement} by given CSS selector.
     */
    private List<WebElement> getList(String selector) {
        try {
            return getWrappedElement().findElements(By.cssSelector(selector));
        } catch (NoSuchElementException e) {
            return Collections.emptyList();
        }
    }
}

