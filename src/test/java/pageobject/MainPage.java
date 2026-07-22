package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String URL = "https://stellarburgers.education-services.ru/";

    private By loginButtonMain = By.xpath(".//button[text()='Войти в аккаунт']");
    private By personalAccountButton = By.xpath(".//p[@class='AppHeader_header__linkText__3q_va ml-2' and text()='Личный Кабинет']");
    private By bunsTab = By.xpath(".//span[text()='Булки']");
    private By saucesTab = By.xpath(".//span[text()='Соусы']");
    private By fillingsTab = By.xpath(".//span[text()='Начинки']");
    private By activeTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(URL);
    }

    @Step("Клик на кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonMain)).click();
    }

    @Step("Клик на 'Личный кабинет'")
    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
    }

    @Step("Клик на вкладку 'Булки'")
    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    @Step("Клик на вкладку 'Соусы'")
    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    @Step("Клик на вкладку 'Начинки'")
    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }

    @Step("Получить текст активной вкладки")
    public String getActiveTabText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab)).getText();
        } catch (Exception e) {
            // Если не нашли активную вкладку, пробуем найти по другой логике
            return "";
        }
    }

    @Step("Получить текущий URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Ожидание загрузки страницы")
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonMain));
    }
}