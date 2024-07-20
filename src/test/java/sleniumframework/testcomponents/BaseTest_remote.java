package sleniumframework.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import sleniumframework.pageobjects.LandingPage;


public class BaseTest_remote {


	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException, URISyntaxException {
		// Properties class to read global data
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//seleniumframework//resources//GlobalData.properties");
		properties.load(fis);

		// Read browser name from system property or global data properties file
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: properties.getProperty("browser");

		// Setup remote WebDriver if using Selenium Grid
		String hubHost = System.getProperty("HUB_HOST");
		if (hubHost != null) {
			// DesiredCapabilities dc = new DesiredCapabilities();
			String host = hubHost != null ? hubHost : "localhost";
			String completeURL = "http://" + host + ":4444/wd/hub";
			if (browserName.contains("chrome")) {
				ChromeOptions options = new ChromeOptions();
				setChromeOptions(options);
				options.setPageLoadTimeout(Duration.ofSeconds(5));
				options.addArguments("--headless"); // Run in headless mode
				options.addArguments("--disable-gpu"); // Disable GPU usage for headless mode
				driver = new RemoteWebDriver(new URI(completeURL).toURL(), options);
			} else if (browserName.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				driver = new RemoteWebDriver(new URI(completeURL).toURL(), options);
			} else if (browserName.equalsIgnoreCase("edge")) {
				EdgeOptions options = new EdgeOptions();
				driver = new RemoteWebDriver(new URI(completeURL).toURL(), options);
			}
		} else {
			// Local browser setup
			if (browserName.contains("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addExtensions(new File(System.getProperty("user.dir") + "\\Stands AdBlocker 2.1.20.0.crx"));
				WebDriverManager.chromedriver().setup();
				if (browserName.contains("headless")) {
					options.addArguments("--headless");
				}
				setChromeOptions(options);
				driver = new ChromeDriver(options);
				driver.manage().window().setSize(new Dimension(1440, 900)); // to run fullscreen
			} else if (browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	private void setChromeOptions(ChromeOptions options) {
		String downloadPath = System.getProperty("user.dir") + "\\downloads";
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		options.setExperimentalOption("prefs", chromePrefs);
	}

//Convert data to HashMap from Json
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// String to HashMap Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
		// {map, map}
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
	}

	@BeforeClass(alwaysRun = true)
	public LandingPage landingPage() throws IOException, URISyntaxException {
		driver = initializeDriver();
		DisableAdsTest();
		landingPage = new LandingPage(driver);
		return landingPage;
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		this.driver.quit();
	}

	public void DisableAdsTest() {
		// Navigate to the webpage
		String mainHandle = driver.getWindowHandle();
		driver.get("https://automationexercise.com/");
		// consentButton.click(); // consent button
		// Get all window handles
		Set<String> windowHandles = driver.getWindowHandles();

		// Iterate over all window handles
		for (String handle : windowHandles) {
			// Switch to each window/tab
			driver.switchTo().window(handle);
			if (!driver.getTitle().equals("Automation Exercise")) {
				driver.close();
				try {
					Thread.sleep(500);// Close the tab
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// Switch back to the original window
		driver.switchTo().window(mainHandle);
		try {
			Thread.sleep(500);// Close the tab
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
