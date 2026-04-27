package com.example.Mastodon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class Record {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testRecord() throws Exception {
        driver.get("https://mastodon.social");
        driver.findElement(By.linkText("Войти")).click();
        driver.get("https://mastodon.social/auth/sign_in");
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("olgaperovskaa0513@gmail.com");
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("password");
        driver.findElement(By.name("button")).click();
        driver.get("https://mastodon.social/");
        driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea")).click();
        driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea")).clear();
        driver.findElement(By.xpath("//div[@id='mastodon']/div/div/div/div/div/div/form[2]/div[2]/div[2]/textarea")).sendKeys("Поскорее бы сдать 2 задание по тестированию");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(3000);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

