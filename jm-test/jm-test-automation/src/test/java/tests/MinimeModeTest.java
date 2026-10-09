package tests;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MinimeModePage;
import pages.RegularModePage;
import pages.TrialPopup;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinimeModeTest extends BaseTest {

    @Test
    @DisplayName("User can switch between Minime Mode and Regular Mode")
    void userCanSwitchBetweenMinimeAndRegularMode() {

        TrialPopup trialPopup = new TrialPopup(driver);
        RegularModePage regularMode = new RegularModePage(driver);
        MinimeModePage minimeMode = new MinimeModePage(driver);

        Allure.step("Handle Trial popup if present", () -> {
            if (trialPopup.isDisplayed()) {
                trialPopup.clickContinue();
            }
        });

        Allure.step("Verify application is in Regular Mode", () -> {
            assertTrue(
                    regularMode.isDisplayed(),
                    "Application should be in Regular Mode before starting the test"
            );
        });

        Allure.step("Click 'Minime Mode'", () -> {
            regularMode.clickMinimeMode();
        });

        Allure.step("Verify Minime Mode is displayed", () -> {
            assertTrue(
                    minimeMode.isDisplayed(),
                    "Minime Mode should be displayed after clicking Minime Mode"
            );
        });

        Allure.step("Click 'Expand'", () -> {
            minimeMode.clickExpand();
        });

        Allure.step("Verify Regular Mode is displayed", () -> {
            assertTrue(
                    regularMode.isDisplayed(),
                    "Regular Mode should be displayed after clicking Expand"
            );
        });
    }
}