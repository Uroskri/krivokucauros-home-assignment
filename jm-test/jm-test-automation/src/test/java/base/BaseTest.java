package base;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.options.WindowsOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import utils.ConfigReader;

import java.net.URI;

public class BaseTest {

    protected WindowsDriver driver;

    @BeforeEach
    public void setUp() throws Exception {

        String appiumServerUrl =
                ConfigReader.get("appium.server.url");

        String winAppDriverUrl =
                ConfigReader.get("winappdriver.url");

        String jpegMiniAppPath =
                ConfigReader.get("jpegmini.app.path");

        WindowsOptions options = new WindowsOptions()
                .setPlatformName("Windows")
                .setAutomationName("Windows")
                .setApp(jpegMiniAppPath)
                .amend("appium:wadUrl", winAppDriverUrl);

        driver = new WindowsDriver(
                new URI(appiumServerUrl).toURL(),
                options
        );
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}