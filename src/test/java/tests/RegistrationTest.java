package tests;

import api.UserApi;
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

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
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
    public void successfulRegistration() {
        String email = "user_" + System.currentTimeMillis() + "@yandex.ru";
        String password = "password123";
        String name = "TestUser";

        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.clickRegisterLink();
        registerPage.register(name, email, password);

        // Ждём, пока пользователь создастся в базе
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        accessToken = userApi.getAccessToken(email, password);
        assertNotNull("Пользователь должен быть создан", accessToken);
    }

    @Test
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