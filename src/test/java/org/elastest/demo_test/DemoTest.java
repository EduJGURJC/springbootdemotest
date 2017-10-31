package org.elastest.demo_test;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.awt.Desktop;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Capabilities;
import static org.openqa.selenium.remote.DesiredCapabilities.chrome;
import static java.lang.System.getenv;

import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.*;

/**
 * Simple example of plain Selenium usage.
 */
public class DemoTest {
    RemoteWebDriver driver;

    @BeforeEach
    void setup() throws MalformedURLException {
        Capabilities capabilities = chrome();
        String driverUrl = getenv("ET_EUS_API");
        if (driverUrl == null) {
            driverUrl = "http://172.21.0.10:8040/eus/v1/";
        }
        System.out.println("Using EUS URL " + driverUrl);
        driver = new RemoteWebDriver(new URL(driverUrl), capabilities);
    }

    @Test
    public void simplePlainSeleniumTest() {
        try {

            System.out
                    .println("Docker Env ip: " + System.getenv("DOCKER_HOST"));

            String appIP = System.getenv("APP_IP");
            System.out.println("App ip: " + appIP);

            driver.get(appIP);

            WebElement page1 = driver.findElementById("page1_button");
            System.out.println("sleep 2000");
            sleep(2000);
            System.out.println("pageclick");
            page1.click();

            boolean expectedTextFound = driver.findElementById("content")
                    .findElement(By.cssSelector("span")).getText()
                    .contains("Page 1");
            System.out.println("assert");
            assertTrue("The word 'Page 1' is found on a page about Page 1",
                    expectedTextFound);
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validUrl(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URLName)
                    .openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
