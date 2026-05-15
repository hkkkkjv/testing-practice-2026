package com.example.Mastodon4.Helpers;

import com.example.Mastodon4.AppManager;

public class NavigationHelper extends HelperBase {
    private final String baseURL;

    public NavigationHelper(AppManager manager, String baseURL) {
        super(manager);
        this.baseURL = baseURL;
    }

    public void goToHomePage() {
        driver.get(baseURL);
    }
}
