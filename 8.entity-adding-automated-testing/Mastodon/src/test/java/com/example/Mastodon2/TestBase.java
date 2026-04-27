package com.example.Mastodon2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "https://mastodon.social";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15L);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void openHomePage() {
        driver.get(baseUrl);
    }

    public void login(AccountData account) throws InterruptedException {
        driver.findElement(By.linkText("Войти")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_email")));
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys(account.getEmail());
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys(account.getPassword());
        driver.findElement(By.name("button")).click();
        Thread.sleep(10000);
    }

    public void createPost(PostData post) throws InterruptedException {
        driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea")).click();
        driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea")).clear();
        driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea")).sendKeys(post.getContent());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(5000);
    }

    public boolean isPostVisible(PostData post) {
        try {
            return driver.findElement(By.xpath("//*[contains(text(), '" + post.getContent() + "')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    protected void waitForElement(By locator) {
        driver.findElement(locator);
    }
}