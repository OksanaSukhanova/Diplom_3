package ru.yandex.praktikum.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.model.User;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;

    // Заголовок формы регистрации
    private static final By HEADER = By.xpath(".//h2[text() = 'Регистрация']");
    private static final String URL = "https://stellarburgers.nomoreparties.site/register";
    // Поля формы регистрации
    private static final By NAME_INPUT = By.xpath(".//label[text() = 'Имя']/../input");
    private static final By EMAIL_INPUT = By.xpath(".//label[text() = 'Email']/../input");
    private static final By PASSWORD_INPUT = By.xpath(".//label[text() = 'Пароль']/../input");
    // Кнопка "Зарегистрироваться"
    private static final By REGISTER_BUTTON = By.xpath(".//button[text() = 'Зарегистрироваться']");
    // Ошибка валидации пароля
    private static final By INVALID_PASSWORD_ERROR = By.cssSelector("p.input__error");
    // Ссылка "Войти"
    private static final By LOGIN_LINK = By.cssSelector("a[href='/login']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open registration page by URL")
    public RegistrationPage open() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        return this;
    }

    @Step("Check that registration page is loaded")
    public RegistrationPage checkPageIsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(URL));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(HEADER)));
        return this;
    }

    private void fillName(String name) {
        driver.findElement(NAME_INPUT).sendKeys(name);
    }

    private void fillEmail(String email) {
        driver.findElement(EMAIL_INPUT).sendKeys(email);
    }

    private void fillPassword(String password) {
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
    }

    @Step("Register as {user}")
    public void register(User user) {
        fillName(user.getName());
        fillEmail(user.getEmail());
        fillPassword(user.getPassword());
        driver.findElement(REGISTER_BUTTON).click();
    }

    @Step("Get password validation error")
    public String getErrorText() {
        return driver.findElement(INVALID_PASSWORD_ERROR).getText();
    }

    @Step("Go to login page by link from registration page")
    public void toLoginPage() {
        driver.findElement(LOGIN_LINK).click();
    }
}
