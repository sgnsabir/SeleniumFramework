package seleniumFramework.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import seleniumFramework.pageobjects.LandingPage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BaseTest {
    protected WebDriver driver;
    protected LandingPage landingPage;

    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 2000;
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration IMPLICIT_WAIT = Duration.ofSeconds(0);

    // Docker environment detection
    private boolean isRunningInDocker() {
        return System.getenv("IS_DOCKER") != null ||
                new File("/.dockerenv").exists();
    }

    public WebDriver initializeDriver() throws IOException {
        String browserName = System.getProperty("browser", "chrome").toLowerCase();
        boolean isRemote = Boolean.parseBoolean(System.getProperty("remote",
                String.valueOf(isRunningInDocker())));

        // Get Grid URL with Docker awareness
        String remoteUrl = getGridUrl();

        logConfiguration(browserName, isRemote, remoteUrl);
        cleanupExistingDriver();

        try {
            MutableCapabilities capabilities = createBrowserCapabilities(browserName);

            if (isRemote) {
                driver = createRemoteWebDriver(remoteUrl, capabilities, browserName);
            } else {
                driver = createLocalWebDriver(capabilities, browserName);
            }

            configureDriverDefaults(driver);
            return driver;

        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
            throw e;
        }
    }

    // New method to get correct Grid URL
    private String getGridUrl() {
        // 1. Check environment variables (standard in Docker)
        String gridUrl = System.getenv("SE_NODE_GRID_URL");
        if (gridUrl != null && !gridUrl.isEmpty()) {
            return gridUrl;
        }

        // 2. Check system property
        gridUrl = System.getProperty("selenium.grid.url");
        if (gridUrl != null && !gridUrl.isEmpty()) {
            return gridUrl;
        }

        // 3. Docker-aware default
        if (isRunningInDocker()) {
            return "http://selenium-hub:4444/wd/hub";
        }

        // 4. Local default
        return "http://localhost:4444/wd/hub";
    }

    private void logConfiguration(String browserName, boolean isRemote, String remoteUrl) {
        System.out.println("\n=== WebDriver Configuration ===");
        System.out.println("Browser: " + browserName);
        System.out.println("Mode: " + (isRemote ? "Remote (Selenium Grid)" : "Local"));
        System.out.println("Docker Environment: " + isRunningInDocker());
        System.out.println("Grid URL: " + remoteUrl);
        System.out.println("==============================\n");
    }

    private void cleanupExistingDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Warning: Error cleaning up existing driver: " + e.getMessage());
            }
            driver = null;
        }
    }

    private MutableCapabilities createBrowserCapabilities(String browserName) {
        String downloadPath = setupDownloadsDirectory();

        if (browserName.contains("chrome")) {
            return createChromeOptions(browserName, downloadPath);
        } else if (browserName.contains("firefox")) {
            return createFirefoxOptions(browserName, downloadPath);
        } else if (browserName.contains("edge")) {
            return createEdgeOptions(browserName);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }

    private String setupDownloadsDirectory() {
        String downloadPath = Paths.get(System.getProperty("user.dir"), "downloads").toString();
        new File(downloadPath).mkdirs();
        return downloadPath;
    }

    private ChromeOptions createChromeOptions(String browserName, String downloadPath) {
        ChromeOptions options = new ChromeOptions();

        // Common Chrome options
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--start-maximized",
                "--disable-extensions",
                "--disable-infobars",
                "--disable-notifications",
                "--ignore-certificate-errors",
                "--remote-allow-origins=*"
        );

        // Headless mode for Docker/local if specified
        if (browserName.contains("headless") || isRunningInDocker()) {
            options.addArguments("--headless=new");
        }

        // Set window size for consistent rendering
        options.addArguments("--window-size=1920,1080");

        // Download preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    private FirefoxOptions createFirefoxOptions(String browserName, String downloadPath) {
        FirefoxOptions options = new FirefoxOptions();

        if (browserName.contains("headless") || isRunningInDocker()) {
            options.addArguments("--headless");
        }

        // Download preferences
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", downloadPath);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        options.addPreference("pdfjs.disabled", true);

        return options;
    }

    private EdgeOptions createEdgeOptions(String browserName) {
        EdgeOptions options = new EdgeOptions();

        if (browserName.contains("headless") || isRunningInDocker()) {
            options.addArguments("--headless");
        }

        return options;
    }

    private WebDriver createRemoteWebDriver(String remoteUrl, MutableCapabilities capabilities, String browserName) {
        // Normalize browser name
        String normalizedBrowser = browserName.replaceAll("(headless|\\s)", "").toLowerCase();
        capabilities.setCapability("browserName", normalizedBrowser);

        // Add timeout configuration
        Map<String, Object> timeouts = new HashMap<>();
        timeouts.put("implicit", 30000);
        timeouts.put("pageLoad", 300000);
        timeouts.put("script", 30000);
        capabilities.setCapability("timeouts", timeouts);

        // Add additional Grid options
        Map<String, Object> seleniumOptions = new HashMap<>();
        seleniumOptions.put("name", "Selenium Framework Test");
        capabilities.setCapability("selenium:options", seleniumOptions);

        int attempt = 0;
        while (attempt < MAX_RETRY_ATTEMPTS) {
            try {
                System.out.printf("Initializing Remote WebDriver [Attempt %d/%d]%n",
                        attempt + 1, MAX_RETRY_ATTEMPTS);

                // Validate URL format
                if (!remoteUrl.startsWith("http")) {
                    throw new MalformedURLException("Invalid Grid URL: " + remoteUrl);
                }

                driver = new RemoteWebDriver(new URL(remoteUrl), capabilities);

                System.out.println("Remote WebDriver initialized successfully");
                System.out.println("Session ID: " + ((RemoteWebDriver) driver).getSessionId());
                return driver;

            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL: " + remoteUrl, e);
            } catch (Exception e) {
                attempt++;
                if (attempt >= MAX_RETRY_ATTEMPTS) {
                    throw new RuntimeException("Failed to create remote WebDriver after " +
                            MAX_RETRY_ATTEMPTS + " attempts", e);
                }

                System.err.println("Retrying in " + RETRY_DELAY_MS + "ms... Error: " + e.getMessage());
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during retry", ie);
                }
            }
        }

        throw new RuntimeException("Remote WebDriver initialization failed");
    }

    private WebDriver createLocalWebDriver(MutableCapabilities capabilities, String browserName) {
        System.out.println("Initializing local WebDriver for " + browserName);

        try {
            switch (browserName.replaceAll("(headless|\\s)", "").toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver((ChromeOptions) capabilities);
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver((FirefoxOptions) capabilities);
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    return new EdgeDriver((EdgeOptions) capabilities);
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Local WebDriver initialization failed", e);
        }
    }

    private void configureDriverDefaults(WebDriver driver) {
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
        }
    }

    @BeforeClass(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        if (driver == null) {
            throw new RuntimeException("WebDriver initialization failed");
        }

        String baseUrl = System.getProperty("base.url", "https://automationexercise.com");
        disableAds();

        try {
            System.out.println("Navigating to: " + baseUrl);
            driver.get(baseUrl);

            handleConsentPopup();

            // Verify successful page load
            if (driver.getTitle() == null || driver.getTitle().isEmpty()) {
                throw new RuntimeException("Page title is empty. Page may not have loaded correctly.");
            }

            System.out.println("Page loaded successfully. Title: " + driver.getTitle());

        } catch (Exception e) {
            captureErrorScreenshot("page_load_error");
            throw new RuntimeException("Failed to load application: " + e.getMessage(), e);
        }

        landingPage = new LandingPage(driver);
        return landingPage;
    }

    private void handleConsentPopup() {
        try {
            BrowserUtils.handleConsentPopup(driver);
            System.out.println("Handled consent popup");
        } catch (Exception e) {
            System.out.println("No consent popup found");
        }
    }

    private void captureErrorScreenshot(String screenshotName) {
        try {
            String screenshotPath = getScreenshot(screenshotName, driver);
            System.err.println("Screenshot saved to: " + screenshotPath);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        File reportsDir = new File("target/screenshots");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File(reportsDir, testCaseName + ".png");
        FileUtils.copyFile(source, destination);
        return destination.getAbsolutePath();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            try {
                String sessionId = driver instanceof RemoteWebDriver ?
                        ((RemoteWebDriver) driver).getSessionId().toString() : "local";
                System.out.println("Closing WebDriver session: " + sessionId);

                driver.manage().deleteAllCookies();
                driver.quit();

            } catch (Exception e) {
                System.err.println("Error during WebDriver cleanup: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    public void disableAds() {
        System.out.println("Disabling ads...");
        String mainHandle = driver.getWindowHandle();

        // Get all window handles before ad handling
        Set<String> initialHandles = driver.getWindowHandles();

        // Navigate to ensure we're on the main page
        driver.get("https://automationexercise.com/");

        // Get all window handles after navigation
        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }

        // Switch back to main window
        driver.switchTo().window(mainHandle);

        // Wait for page to stabilize
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }
}