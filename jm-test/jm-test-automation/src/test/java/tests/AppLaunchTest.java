package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppLaunchTest extends BaseTest {

    @Test
    void jpegMiniApplicationIsLaunched() {

        assertNotNull(driver.getSessionId(),
                "Appium session should be created successfully");
    }
}