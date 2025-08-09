package seleniumFramework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BrowserUtils {
    
    private static final String CONSENT_OVERLAY = ".fc-dialog-overlay";
    private static final String CONSENT_ACCEPT_BUTTON = "button.fc-cta-consent, button.fc-button.fc-cta-consent";
    
    /**
     * Handles consent popup if it appears
     * @param driver WebDriver instance
     */
    public static void handleConsentPopup(WebDriver driver) {
        try {
            // Use minimal wait for overlay
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            
            // Check if overlay is present without waiting if not immediately visible
            List<WebElement> overlays = driver.findElements(By.cssSelector(CONSENT_OVERLAY));
            if (!overlays.isEmpty() && overlays.get(0).isDisplayed()) {
                // Find and click the accept button if it exists and is visible
                List<WebElement> buttons = driver.findElements(By.cssSelector(CONSENT_ACCEPT_BUTTON));
                for (WebElement button : buttons) {
                    if (button.isDisplayed() && button.isEnabled()) {
                        button.click();
                        // Wait briefly for the overlay to start disappearing
                        try {
                            shortWait.until(ExpectedConditions.invisibilityOf(overlays.get(0)));
                        } catch (Exception e) {
                            // Continue even if overlay doesn't disappear immediately
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Silently continue if any error occurs
        }
    }
    
    /**
     * Waits for the page to be fully loaded
     * @param driver WebDriver instance
     * @param timeoutInSeconds Maximum time to wait in seconds
     */
    public static void waitForPageLoad(WebDriver driver, long timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript(
                "return document.readyState"
            ).equals("complete")
        );
    }
}
