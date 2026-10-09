package pages;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BasePage {

    protected final WindowsDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WindowsDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected boolean isElementDisplayed(By locator) {

        return driver.findElements(locator)
                .stream()
                .anyMatch(WebElement::isDisplayed);
    }

    protected void switchToWindowContaining(By locator) {

        wait.until(driver -> {

            Set<String> windowHandles = driver.getWindowHandles();

            for (String handle : windowHandles) {

                try {
                    driver.switchTo().window(handle);

                    if (driver.findElements(locator)
                            .stream()
                            .anyMatch(WebElement::isDisplayed)) {

                        return true;
                    }

                } catch (Exception ignored) {
                    // Try the next window handle.
                }
            }

            return false;
        });
    }
}