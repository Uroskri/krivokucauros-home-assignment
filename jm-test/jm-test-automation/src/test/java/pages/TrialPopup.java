package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrialPopup extends BasePage {

    private final By continueWithTrialButton =
            AppiumBy.xpath("//Button[@Name='Continue with Trial']");

    public TrialPopup(WindowsDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {

        try {
            return new WebDriverWait(
                    driver,
                    Duration.ofSeconds(3)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            continueWithTrialButton
                    )
            ).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickContinue() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueWithTrialButton
                )
        ).click();
    }
}