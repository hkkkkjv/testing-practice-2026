package com.example.Mastodon3;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class HelperBase {
    protected final AppManager manager;
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected boolean acceptNextAlert = true;

    public HelperBase(AppManager manager) {
        this.manager = manager;
        this.driver = manager.getDriver();
        this.wait = manager.getWait();
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected String getBaseUrl() {
        return manager.getBaseUrl();
    }
}