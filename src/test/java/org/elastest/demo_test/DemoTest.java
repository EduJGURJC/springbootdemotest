package org.elastest.demo_test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import static java.lang.System.getenv;

import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;

/**
 * Simple example of plain Selenium usage.
 */
public class DemoTest {
    RemoteWebDriver driver;

    @Test
    public void simplePlainSeleniumTest() {
        try {
            String driverUrl = getenv("ET_EUS_API");
            if (driverUrl == null) {
                driverUrl = "http://172.27.0.12:8040/eus/v1/";
            }
            System.out.println("Using EUS URL " + driverUrl);
            driver = new RemoteWebDriver(new URL(driverUrl), DesiredCapabilities.chrome());

            String appIP = System.getenv("ET_SUT_HOST");
            if(appIP == null){
                appIP = "http://172.17.0.6:8080";
            }
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
