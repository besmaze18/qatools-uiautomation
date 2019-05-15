package io.sibur.uiautotests.awaiter;

import org.openqa.selenium.WebElement;

/**
 * {@code AwaiterException} is the superclass of those
 * exceptions that can be thrown during {@link WebElement} await
 */
public class AwaiterException extends Exception {
    public AwaiterException(String message) {
        super(message);
    }
}