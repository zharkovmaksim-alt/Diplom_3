package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.MainPage;
import pageobject.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.*;

public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Вкладка 'Булки' активна по умолчанию")
    @Description("Проверка, что при открытии главной страницы активна вкладка 'Булки'")
    public void bunsTabIsActiveByDefault() {
        mainPage.open();
        wait.until(driver -> !driver.getCurrentUrl().isEmpty());
        String activeTab = mainPage.getActiveTabText();
        System.out.println("Активная вкладка по умолчанию: " + activeTab);
        assertEquals("Булки", activeTab);
    }

    @Test
    @DisplayName("Переключение на вкладку 'Соусы'")
    @Description("Проверка, что при клике на вкладку 'Соусы' она становится активной")
    public void switchToSaucesTab() {
        mainPage.open();
        wait.until(driver -> !driver.getCurrentUrl().isEmpty());
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String activeTab = mainPage.getActiveTabText();
        System.out.println("Активная вкладка после клика на Соусы: " + activeTab);
        assertEquals("Соусы", activeTab);
    }

    @Test
    @DisplayName("Переключение на вкладку 'Начинки'")
    @Description("Проверка, что при клике на вкладку 'Начинки' она становится активной")
    public void switchToFillingsTab() {
        mainPage.open();
        wait.until(driver -> !driver.getCurrentUrl().isEmpty());
        mainPage.clickFillingsTab();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String activeTab = mainPage.getActiveTabText();
        System.out.println("Активная вкладка после клика на Начинки: " + activeTab);
        assertEquals("Начинки", activeTab);
    }

    @Test
    @DisplayName("Возврат на вкладку 'Булки'")
    @Description("Проверка, что можно вернуться на вкладку 'Булки' после переключения")
    public void switchBackToBunsTab() {
        mainPage.open();
        wait.until(driver -> !driver.getCurrentUrl().isEmpty());
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainPage.clickBunsTab();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String activeTab = mainPage.getActiveTabText();
        System.out.println("Активная вкладка после клика на Булки: " + activeTab);
        assertEquals("Булки", activeTab);
    }
}