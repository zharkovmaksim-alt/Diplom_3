package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    private static final String BROWSER = System.getProperty("browser", "chrome");

    public static WebDriver getDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        if ("yandex".equalsIgnoreCase(BROWSER)) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");
        }

        return new ChromeDriver(options);
    }
}
