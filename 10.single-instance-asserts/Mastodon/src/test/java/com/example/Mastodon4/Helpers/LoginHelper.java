package com.example.Mastodon4.Helpers;

import com.example.Mastodon4.AppManager;
import com.example.Mastodon4.Model.AccountData;
import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

    public LoginHelper(AppManager manager) {
        super(manager);
    }

    public void login(AccountData account) throws InterruptedException {
        driver.get(manager.getBaseUrl() + "/auth/sign_in");

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

    public boolean isLoggedIn(String username) {
        try {
            By accountIndicator = By.xpath("//span[@class='display-name__account' and text()='@" + username + "']");
            return isElementPresent(accountIndicator);
        } catch (Exception e) {
            return false;
        }
    }
}
