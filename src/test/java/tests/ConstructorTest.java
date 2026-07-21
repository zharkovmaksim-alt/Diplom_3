package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;
import pageobject.WebDriverFactory;

import static org.junit.Assert.*;

public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void bunsTabIsActiveByDefault() {
        mainPage.open();
        mainPage.waitForPageLoad();
        assertEquals("Булки", mainPage.getActiveTabText());
    }

    @Test
    public void switchToSaucesTab() {
        mainPage.open();
        mainPage.waitForPageLoad();
        mainPage.clickSaucesTab();
        mainPage.waitForPageLoad();
        // Проверяем, что активная вкладка изменилась
        String activeTab = mainPage.getActiveTabText();
        System.out.println("Активная вкладка после клика на Соусы: " + activeTab);
        assertEquals("Соусы", activeTab);
    }

    @Test
    public void switchToFillingsTab() {
        mainPage.open();
        mainPage.waitForPageLoad();
        mainPage.clickFillingsTab();
        mainPage.waitForPageLoad();
        assertEquals("Начинки", mainPage.getActiveTabText());
    }

    @Test
    public void switchBackToBunsTab() {
        mainPage.open();
        mainPage.waitForPageLoad();
        mainPage.clickSaucesTab();
        mainPage.waitForPageLoad();
        mainPage.clickBunsTab();
        mainPage.waitForPageLoad();
        assertEquals("Булки", mainPage.getActiveTabText());
    }
}