package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MinimeModePage extends BasePage {

    private final By expandButton =
            AppiumBy.accessibilityId("BtnNormalMode");

    public MinimeModePage(WindowsDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        expandButton
                )
        ).isDisplayed();
    }

    public void clickExpand() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        expandButton
                )
        ).click();

        switchToWindowContaining(
                AppiumBy.accessibilityId("BtnMiniMode")
        );
    }
}