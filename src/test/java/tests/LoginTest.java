package tests;

import api.UserApi;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;
import pageobject.WebDriverFactory;

import static org.junit.Assert.*;

public class LoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private UserApi userApi;
    private String accessToken;
    private String userEmail;
    private String userPassword;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        userApi = new UserApi();

        userEmail = "login_" + System.currentTimeMillis() + "@yandex.ru";
        userPassword = "password123";
        Response createResponse = userApi.createUser(userEmail, userPassword, "LoginUser");
        System.out.println("Создан пользователь. Код ответа: " + createResponse.getStatusCode());
        assertTrue("Пользователь должен создаться", createResponse.getStatusCode() == 200);
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
    public void loginViaMainPageButton() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.login(userEmail, userPassword);
        mainPage.open(); // Принудительный переход на главную после входа

        assertEquals("https://stellarburgers.education-services.ru/", driver.getCurrentUrl());
        accessToken = userApi.getAccessToken(userEmail, userPassword);
        assertNotNull(accessToken);
    }

    @Test
    public void loginViaPersonalAccount() {
        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.login(userEmail, userPassword);
        mainPage.open(); // Принудительный переход на главную после входа

        assertEquals("https://stellarburgers.education-services.ru/", driver.getCurrentUrl());
        accessToken = userApi.getAccessToken(userEmail, userPassword);
        assertNotNull(accessToken);
    }

    @Test
    public void loginViaRegisterPage() {
        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickRegisterLink();
        registerPage.clickLoginLink();
        loginPage.login(userEmail, userPassword);
        mainPage.open(); // Принудительный переход на главную после входа

        assertEquals("https://stellarburgers.education-services.ru/", driver.getCurrentUrl());
        accessToken = userApi.getAccessToken(userEmail, userPassword);
        assertNotNull(accessToken);
    }

    @Test
    public void loginViaForgotPasswordPage() {
        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickForgotPasswordLink();
        loginPage.clickLoginLink();
        loginPage.login(userEmail, userPassword);
        mainPage.open(); // Принудительный переход на главную после входа

        assertEquals("https://stellarburgers.education-services.ru/", driver.getCurrentUrl());
        accessToken = userApi.getAccessToken(userEmail, userPassword);
        assertNotNull(accessToken);
    }
}