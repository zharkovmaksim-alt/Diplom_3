package tests;

import api.UserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;
import pageobject.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.*;

public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private UserApi userApi;
    private String accessToken;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        userApi = new UserApi();
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userApi.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка, что пользователь может зарегистрироваться с валидными данными")
    public void successfulRegistration() {
        String email = "user_" + System.currentTimeMillis() + "@yandex.ru";
        String password = "password123";
        String name = "TestUser";

        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickRegisterLink();
        registerPage.register(name, email, password);

        wait.until(driver -> driver.getCurrentUrl().contains("login"));

        accessToken = userApi.getAccessToken(email, password);
        assertNotNull("Пользователь должен быть создан", accessToken);
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверка, что при вводе пароля короче 6 символов появляется ошибка")
    public void registrationWithShortPasswordShouldReturnError() {
        String email = "short_" + System.currentTimeMillis() + "@yandex.ru";
        String shortPassword = "12345";
        String name = "ShortUser";

        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickRegisterLink();
        registerPage.register(name, email, shortPassword);

        String error = registerPage.getErrorMessage();
        assertTrue("Должна появиться ошибка о коротком пароле", error.contains("Некорректный пароль"));
    }
}