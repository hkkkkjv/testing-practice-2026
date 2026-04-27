package com.example.Mastodon2;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MastodonTests extends TestBase {

    @Test(priority = 1)
    public void testAuth() throws InterruptedException {
        AccountData user = new AccountData("olgaperovskaa0513@gmail.com", "password");

        openHomePage();
        login(user);

        boolean isLoggedIn = driver.findElement(
                By.cssSelector("[data-testid='compose-form'], .compose-form, [data-testid='user-avatar']")
        ).isDisplayed();

        Assert.assertTrue(isLoggedIn, "Авторизация не прошла: элемент профиля/формы не найден");
    }

    @Test(priority = 2)
    public void testCreatePost() throws InterruptedException {
        AccountData user = new AccountData("olgaperovskaa0513@gmail.com", "password");
        PostData newPost = new PostData("Automated testing is magic" + System.currentTimeMillis());

        openHomePage();
        login(user);
        createPost(newPost);

        Assert.assertTrue(
                isPostVisible(newPost),
                "Пост не опубликован: текст '" + newPost.getContent() + "' не найден"
        );
    }
}