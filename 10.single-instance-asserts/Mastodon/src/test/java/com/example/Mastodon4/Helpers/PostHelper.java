package com.example.Mastodon4.Helpers;

import com.example.Mastodon4.AppManager;
import com.example.Mastodon4.Model.PostData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public void editPost(PostData originalPost, String newContent) throws InterruptedException {
        By postTextLocator = By.xpath("//p[normalize-space(text())='" + escapeXPath(originalPost.getContent()) + "']");
        waitForElementVisible(postTextLocator);

        By moreButton = By.xpath(
                "//p[normalize-space(text())='" + escapeXPath(originalPost.getContent()) + "']" +
                        "/ancestor::div[@class='status status-public']" +
                        "//button[@aria-label='Ещё' and contains(@class, 'icon-button')]"
        );
        waitForElementClickable(moreButton);
        driver.findElement(moreButton).click();

        By editOption = By.xpath("//span[@class='dropdown-menu__item-content' and text()='Редактировать']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(editOption));
        driver.findElement(editOption).click();

        By editorLocator = By.xpath("//div[@contenteditable='true'] | //textarea[@placeholder]");
        WebElement editor = wait.until(ExpectedConditions.presenceOfElementLocated(editorLocator));
        Thread.sleep(3000);
        editor.clear();
        editor.sendKeys(newContent);
        Thread.sleep(3000);
        By saveButton = By.xpath("//button[@type='submit' and not(@disabled)]");
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        Thread.sleep(2000);
    }


    public boolean isPostVisible(PostData post) {
        try {
            String content = post.getContent();
            By postLocator = By.xpath("//div[contains(@class, 'status__content__text')]//p[normalize-space(text())='" + escapeXPath(content) + "']");
            WebElement postElement = wait.until(ExpectedConditions.presenceOfElementLocated(postLocator));
            return postElement.isDisplayed() && isElementInViewport(postElement);
        } catch (Exception e) {
            System.out.println("Post not found: " + post.getContent());
            return false;
        }
    }

    private boolean isElementInViewport(WebElement element) {
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return (rect.top >= 0 && rect.left >= 0 && " +
                        "rect.bottom <= window.innerHeight && rect.right <= window.innerWidth);",
                element
        );
    }

    private String escapeXPath(String text) {
        if (text.contains("'")) {
            return text.replaceAll("'", "', \"'\", '");
        }
        return text;
    }

}
