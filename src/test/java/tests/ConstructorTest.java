package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Вкладка 'Булки' отображается")
    @Description("Проверка, что вкладка 'Булки' отображается на главной странице")
    public void bunsTabIsDisplayed() {
        mainPage.open();
        mainPage.waitForPageLoaded();
        assertTrue("Вкладка 'Булки' должна отображаться", mainPage.isBunsTabDisplayed());
    }

    @Test
    @DisplayName("Вкладка 'Соусы' отображается")
    @Description("Проверка, что вкладка 'Соусы' отображается на главной странице")
    public void saucesTabIsDisplayed() {
        mainPage.open();
        mainPage.waitForPageLoaded();
        assertTrue("Вкладка 'Соусы' должна отображаться", mainPage.isSaucesTabDisplayed());
    }

    @Test
    @DisplayName("Вкладка 'Начинки' отображается")
    @Description("Проверка, что вкладка 'Начинки' отображается на главной странице")
    public void fillingsTabIsDisplayed() {
        mainPage.open();
        mainPage.waitForPageLoaded();
        assertTrue("Вкладка 'Начинки' должна отображаться", mainPage.isFillingsTabDisplayed());
    }
}