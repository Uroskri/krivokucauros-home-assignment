# JM Test Automation — JPEGmini Pro

## Overview

UI automation tests for JPEGmini Pro, focusing on Minime Mode.

**Technologies:** Java 17, Maven, Appium, Selenium WebDriver, JUnit 5, Allure.

## Prerequisites

- Windows
- JPEGmini Pro
- Java JDK 17
- IntelliJ IDEA with Maven support
- Appium and the Appium Windows driver
- Windows Application Driver (WinAppDriver)

## Configuration

1. Navigate to `src/test/resources/`.
2. Copy `config.properties.example` and rename the copy to `config.properties`.
3. Update the application path and server URLs for your machine.

The configuration includes:
- `appium.server.url` — Appium server URL.
- `winappdriver.url` — WinAppDriver server URL.

## Start the Servers

Start WinAppDriver in a separate PowerShell window (run as Administrator):

```powershell
& "C:\Program Files (x86)\Windows Application Driver\WinAppDriver.exe" 4725
```

Start Appium in another PowerShell window:

```powershell
appium --address 127.0.0.1 --port 4723
```

Keep both server windows open while running the tests. The ports must match the URLs configured in `config.properties`.

## Run the Tests

1. Open the project in IntelliJ IDEA.
2. Ensure both servers are running.
3. In the Maven panel, navigate to **Lifecycle → test** and run `test`.

## Allure Report

A generated HTML report snapshot is included in `allure-report/`, and the raw execution results are available in `allure-results/`.

To generate and open an interactive report in IntelliJ IDEA, navigate to **Maven → Plugins → allure-maven-plugin → serve** and run `serve`.

Keep the process running while viewing the report in your browser.