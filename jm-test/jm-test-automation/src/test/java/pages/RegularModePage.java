package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegularModePage extends BasePage {

    private final By minimeModeButton =
            AppiumBy.accessibilityId("BtnMiniMode");

    public RegularModePage(WindowsDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        minimeModeButton
                )
        ).isDisplayed();
    }

    public void clickMinimeMode() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        minimeModeButton
                )
        ).click();

        switchToWindowContaining(
                AppiumBy.accessibilityId("BtnNormalMode")
        );
    }
}