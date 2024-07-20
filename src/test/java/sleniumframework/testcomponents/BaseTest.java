package sleniumframework.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import sleniumframework.pageobjects.LandingPage;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "//src//main//java//seleniumframework//resources//GlobalData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to load properties file.", e);
        }

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser").trim()
                : properties.getProperty("browser").trim();

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addExtensions(new File(System.getProperty("user.dir") + "\\Stands AdBlocker 2.1.20.0.crx"));
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("headless")) {
                options.addArguments("--headless");
            }

            String downloadPath = System.getProperty("user.dir") + "\\downloads";
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadPath);
            options.setExperimentalOption("prefs", chromePrefs);

            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440, 900)); // to run fullscreen
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
        FileUtils.copyFile(source, destination);
        return destination.getAbsolutePath();
    }

    @BeforeClass(alwaysRun = true)
    public LandingPage landingPage() throws IOException {
        driver = initializeDriver();
        if (driver == null) {
            throw new RuntimeException("Failed to initialize WebDriver.");
        }
        DisableAdsTest();
        landingPage = new LandingPage(driver);
        return landingPage;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void DisableAdsTest() {
        String mainHandle = driver.getWindowHandle();
        driver.get("https://automationexercise.com/");
        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            if (!driver.getTitle().equals("Automation Exercise")) {
                driver.close();
                try {
                    Thread.sleep(500); // Allow time for tab closure
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }

        driver.switchTo().window(mainHandle);
        try {
            Thread.sleep(500); // Allow time for tab closure
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}