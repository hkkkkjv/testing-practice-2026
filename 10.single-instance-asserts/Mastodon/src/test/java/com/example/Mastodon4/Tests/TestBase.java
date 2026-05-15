package com.example.Mastodon4.Tests;

import com.example.Mastodon4.AppManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
    protected AppManager app;

    @BeforeSuite
    public void setUpSuite() {
        app = AppManager.getInstance();
    }

    @AfterSuite
    public void tearDown() {
        AppManager.clearInstance();
    }
}
