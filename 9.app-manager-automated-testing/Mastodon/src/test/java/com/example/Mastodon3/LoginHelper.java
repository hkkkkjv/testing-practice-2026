package com.example.Mastodon3;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

    public LoginHelper(AppManager manager) {
        super(manager);
    }

    public void login(AccountData account) throws InterruptedException {
        if (driver.getCurrentUrl().equals(manager.getBaseUrl() + "/")) {
            driver.findElement(By.linkText("Войти")).click();
        }

        waitForElementVisible(By.id("user_email"));

        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys(account.getEmail());

        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys(account.getPassword());

        driver.findElement(By.name("button")).click();

        Thread.sleep(2000);
    }

    public void logout() {
    }

    public boolean isLoggedIn() {
        return true;
    }
}
