package com.example.Mastodon3;

import org.openqa.selenium.By;

public class PostHelper extends HelperBase {

    public PostHelper(AppManager manager) {
        super(manager);
    }

    public void createPost(PostData post) throws InterruptedException {
        waitForElementClickable(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea"));

        var textarea = driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea"));
        textarea.click();
        textarea.clear();
        textarea.sendKeys(post.getContent());

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(3000);
    }

    public boolean isPostVisible(PostData post) {
        try {
            String escapedContent = post.getContent().replace("'", "\\'");
            return driver.findElement(
                    By.xpath("//*[contains(text(), '" + escapedContent + "')]")
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
