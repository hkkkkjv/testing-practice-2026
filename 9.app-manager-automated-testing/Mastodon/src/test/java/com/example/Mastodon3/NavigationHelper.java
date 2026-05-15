package com.example.Mastodon3;

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
