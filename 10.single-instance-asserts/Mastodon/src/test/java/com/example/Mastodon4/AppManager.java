package com.example.Mastodon4;

import com.example.Mastodon4.Helpers.LoginHelper;
import com.example.Mastodon4.Helpers.NavigationHelper;
import com.example.Mastodon4.Helpers.PostHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v147.network.Network;
import org.openqa.selenium.devtools.v147.network.model.Headers;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AppManager {
    private static final ThreadLocal<AppManager> instance = new ThreadLocal<>();

    private WebDriver driver;
    private WebDriverWait wait;
    protected DevTools devTools;
    private final String baseURL = "https://mastodon.social";

    private NavigationHelper navigation;
    private LoginHelper auth;
    private PostHelper post;

    public static AppManager getInstance() {
        if (instance.get() == null) {
            instance.set(new AppManager());
        }
        return instance.get();
    }

    public void close() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
            driver = null;
        }
    }

    public static void clearInstance() {
        if (instance.get() != null) {
            instance.get().close();
            instance.remove();
        }
    }

    private AppManager() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = configureChromeOptions();
        driver = new ChromeDriver(options);

        configureDriverSettings();
        wait = new WebDriverWait(driver, 15L);

        initDevTools();

        navigation = new NavigationHelper(this, baseURL);
        auth = new LoginHelper(this);
        post = new PostHelper(this);
    }

    private ChromeOptions configureChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-features=NetworkService");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--user-agent=" + getUserAgent());
        return options;
    }

    private String getUserAgent() {
        return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/147.0.0.0 Safari/537.36";
    }

    private void configureDriverSettings() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private void initDevTools() {
        if (driver instanceof HasDevTools hasDevTools) {
            devTools = hasDevTools.getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(
                    Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty()));
            addCustomHeaders(getUserAgent());
        }
    }

    @SuppressWarnings("unchecked")
    private void addCustomHeaders(String userAgent) {
        try {
            Map<String, Object> headersMap = Map.ofEntries(
                    Map.entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"),
                    Map.entry("Accept-Encoding", "gzip, deflate, br"),
                    Map.entry("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"),
                    Map.entry("Cache-Control", "no-cache"),
                    Map.entry("Pragma", "no-cache"),
                    Map.entry("Upgrade-Insecure-Requests", "1"),
                    Map.entry("Sec-Ch-Ua", "\"Google Chrome\";v=\"147\", \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"147\""),
                    Map.entry("Sec-Ch-Ua-Mobile", "?0"),
                    Map.entry("Sec-Ch-Ua-Platform", "\"macOS\""),
                    Map.entry("Sec-Fetch-Dest", "document"),
                    Map.entry("Sec-Fetch-Mode", "navigate"),
                    Map.entry("Sec-Fetch-Site", "same-origin"),
                    Map.entry("Sec-Fetch-User", "?1"),
                    Map.entry("User-Agent", userAgent)
            );

            Constructor<Headers> ctor = Headers.class.getDeclaredConstructor(Map.class);
            ctor.setAccessible(true);
            Headers headersObj = ctor.newInstance(headersMap);
            devTools.send(Network.setExtraHTTPHeaders(headersObj));

        } catch (Exception e) {
            throw new RuntimeException("Failed to set custom headers via CDP", e);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public String getBaseUrl() {
        return baseURL;
    }

    public NavigationHelper getNavigation() {
        return navigation;
    }

    public LoginHelper getAuth() {
        return auth;
    }

    public PostHelper getPost() {
        return post;
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
