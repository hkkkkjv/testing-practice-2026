package com.example.Mastodon4.Tests;

import com.example.Mastodon4.Model.AccountData;
import com.example.Mastodon4.Model.PostData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MastodonTests extends TestBase {

    private static final String POST_TEXT_TEMPLATE = "Automated testing is magic";

    private String generatePostText(String suffix) {
        return POST_TEXT_TEMPLATE + " " + System.currentTimeMillis() + suffix;
    }

    private PostData createdPost;

    @Test(priority = 1)
    public void testAuth() throws InterruptedException {
        AccountData user = new AccountData(
                "olgaperovskaa0513@gmail.com",
                "hkkkkjv",
                ""
        );
        app.getNavigation().goToHomePage();
        app.getAuth().login(user);
        Assert.assertTrue(
                app.getAuth().isLoggedIn(user.getUsername()),
                "Пользователь не авторизован после login()"
        );
    }

    @Test(priority = 2)
    public void testCreatePost() throws InterruptedException {
        createdPost = new PostData(generatePostText(""));
        app.getNavigation().goToHomePage();
        app.getPost().createPost(createdPost);
        Assert.assertTrue(
                app.getPost().isPostVisible(createdPost),
                "Созданный пост не найден: '" + createdPost.getContent() + "'"
        );
    }

    @Test(priority = 3)
    public void testEditPost() throws InterruptedException {
        String editedContent = createdPost.getContent() + " [EDITED]";
        app.getPost().editPost(createdPost, editedContent);
        createdPost.setContent(editedContent);
        Assert.assertTrue(
                app.getPost().isPostVisible(createdPost),
                "Отредактированный пост не найден: '" + createdPost.getContent() + "'"
        );
    }
}