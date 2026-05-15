package com.example.Mastodon3;

import org.testng.annotations.Test;

public class MastodonTests extends TestBase {

    @Test(priority = 1)
    public void testAuth() throws InterruptedException {
        AccountData user = new AccountData(
                "olgaperovskaa0513@gmail.com",
                "hkkkkjv",
                "password"
        );

        app.getNavigation().goToHomePage();
        app.getAuth().login(user);
    }

    @Test(priority = 2)
    public void testCreatePost() throws InterruptedException {
        AccountData user = new AccountData(
                "olgaperovskaa0513@gmail.com",
                "hkkkkjv",
                "password"
        );
        PostData newPost = new PostData(
                "Automated testing is magic " + System.currentTimeMillis()
        );

        app.getNavigation().goToHomePage();
        app.getAuth().login(user);
        app.getPost().createPost(newPost);

    }
}