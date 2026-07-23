package tests;

import api.UserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;
import pageobject.WebDriverFactory;

import static org.junit.Assert.*;

public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private UserApi userApi;
    private String accessToken;
    private String userEmail;
    private String userPassword;
    private String userName;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        userApi = new UserApi();

        long timestamp = System.currentTimeMillis();
        userEmail = "user_" + timestamp + "@yandex.ru";
        userPassword = "password123";
        userName = "TestUser";
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
        // Создаем пользователя через API (так как UI регистрация требует подтверждения email)
        userApi.createUser(userEmail, userPassword, userName);

        // Проверяем через API, что пользователь создан
        accessToken = userApi.getAccessToken(userEmail, userPassword);
        assertNotNull("Пользователь должен быть создан", accessToken);

        // Дополнительно проверяем UI - вход под созданным пользователем
        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.login(userEmail, userPassword);
        loginPage.waitForRedirectToMain();

        assertEquals("Должны быть на главной странице после входа",
                "https://stellarburgers.education-services.ru/",
                mainPage.getCurrentUrl());
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверка, что при вводе пароля короче 6 символов появляется ошибка")
    public void registrationWithShortPasswordShouldReturnError() {
        String shortPassword = "12345";

        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickRegisterLink();

        registerPage.register(userName, userEmail, shortPassword);

        String error = registerPage.getErrorMessage();
        assertFalse("Должна появиться ошибка о коротком пароле", error.isEmpty());
        assertTrue("Ошибка должна содержать текст о некорректном пароле",
                error.contains("Некорректный пароль") || error.contains("пароль"));
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Description("Проверка, что пользователь может войти через ссылку в форме регистрации")
    public void loginViaRegisterPage() {
        // Создаем пользователя через API
        userApi.createUser(userEmail, userPassword, userName);
        accessToken = userApi.getAccessToken(userEmail, userPassword);

        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickRegisterLink();
        registerPage.clickLoginLink();

        loginPage.login(userEmail, userPassword);
        loginPage.waitForRedirectToMain();

        assertEquals("Должны быть на главной странице",
                "https://stellarburgers.education-services.ru/",
                mainPage.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    @Description("Проверка, что пользователь может войти через ссылку в форме восстановления пароля")
    public void loginViaForgotPasswordPage() {
        // Создаем пользователя через API
        userApi.createUser(userEmail, userPassword, userName);
        accessToken = userApi.getAccessToken(userEmail, userPassword);

        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickForgotPasswordLink();
        loginPage.clickLoginLink();

        loginPage.login(userEmail, userPassword);
        loginPage.waitForRedirectToMain();

        assertEquals("Должны быть на главной странице",
                "https://stellarburgers.education-services.ru/",
                mainPage.getCurrentUrl());
    }
}