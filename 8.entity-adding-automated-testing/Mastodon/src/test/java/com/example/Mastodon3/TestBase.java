package com.example.Mastodon3;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {
    protected AppManager app;

    @BeforeClass
    public void setUp() {
        app = new AppManager();
    }

    @AfterClass
    public void tearDown() {
        app.stop();
    }
}
