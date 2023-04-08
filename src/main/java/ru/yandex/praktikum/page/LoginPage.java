package ru.yandex.praktikum.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.model.User;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LoginPage {
    private WebDriver driver;

    // Заголовок формы авторизации
    private static final By HEADER = By.cssSelector("div[class*='Auth_login']>h2");
    private static final String URL = "https://stellarburgers.nomoreparties.site/login";
    // Ссылка "Зарегистрироваться"
    private static final By TO_REGISTRATION_PAGE = By.cssSelector("a[href='/register']");
    // Поля формы авторизации
    private static final By EMAIL_INPUT = By.xpath(".//label[text() = 'Email']/../input");
    private static final By PASSWORD_INPUT = By.xpath(".//label[text() = 'Пароль']/../input");
    // Кнопка "Войти"
    private static final By LOGIN_BUTTON = By.xpath(".//button[text() = 'Войти']");
    // Ссылка "Забыли пароль?"
    private static final By FORGOT_PASSWORD_LINK = By.cssSelector("a[href = '/forgot-password']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open login page by URL")
    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    @Step("Check that login page is loaded")
    public LoginPage checkPageIsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(URL));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(HEADER)));
        return this;
    }

    @Step("Go to registration page by link")
    public void toRegistrationPage() {
        driver.findElement(TO_REGISTRATION_PAGE).click();
    }

    @Step("Get login page header value")
    public String getHeaderText() {
        return driver.findElement(HEADER).getText();
    }

    @Step("Authorize by {user}")
    public void loginBy(User user) {
        driver.findElement(EMAIL_INPUT).sendKeys(user.getEmail());
        driver.findElement(PASSWORD_INPUT).sendKeys(user.getPassword());
        driver.findElement(LOGIN_BUTTON).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
    }

    @Step("Go to forgot password page by link")
    public void toForgotPasswordPage() {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(FORGOT_PASSWORD_LINK)).perform();
        driver.findElement(FORGOT_PASSWORD_LINK).click();
    }

    @Step("Check that login form is visible")
    public void checkLoginFormIsVisible() {
        assertTrue(driver.findElement(EMAIL_INPUT).isDisplayed());
        assertTrue(driver.findElement(PASSWORD_INPUT).isDisplayed());
        assertTrue(driver.findElement(LOGIN_BUTTON).isDisplayed());
    }
}
