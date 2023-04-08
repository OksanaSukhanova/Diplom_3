package ru.yandex.praktikum.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class AccountPage {
    private final WebDriver driver;

    private static final String URL = "https://stellarburgers.nomoreparties.site/account/profile";
    // Подсказка в нижней части страницы
    private static final By FOOTER = By.xpath(".//p[text() = 'В этом разделе вы можете изменить свои персональные данные']");
    // Поля формы
    private static final By NAME_INPUT = By.xpath(".//label[text() = 'Имя']/../input");
    private static final By LOGIN_INPUT = By.xpath(".//label[text() = 'Логин']/../input");
    private static final By PASSWORD_INPUT = By.xpath(".//label[text() = 'Пароль']/../input");
    // Ссылки на другие страницы и выход из аккаунта
    private static final By PROFILE_LINK = By.cssSelector("a[href = '/account/profile']");
    private static final By ORDERS_HISTORY_LINK = By.cssSelector("a[href = '/account/order-history']");
    private static final By EXIT_BUTTON = By.xpath(".//button[text() = 'Выход']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Check that account page is loaded")
    public AccountPage checkPageIsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(URL));
        wait.until(ExpectedConditions.presenceOfElementLocated(FOOTER));
        return this;
    }

    @Step("Check that account elements are visible")
    public void checkAccountIsVisible() {
        // Проверяем видимость полей формы
        assertTrue(driver.findElement(NAME_INPUT).isDisplayed());
        assertTrue(driver.findElement(PASSWORD_INPUT).isDisplayed());
        assertTrue(driver.findElement(LOGIN_INPUT).isDisplayed());
        // Проверяем видимость ссылок в левой части страницы
        assertTrue(driver.findElement(PROFILE_LINK).isDisplayed());
        assertTrue(driver.findElement(ORDERS_HISTORY_LINK).isDisplayed());
        assertTrue(driver.findElement(EXIT_BUTTON).isDisplayed());
    }

    @Step("Click on exit from account button")
    public void exitFromAccount() {
        driver.findElement(EXIT_BUTTON).click();
    }
}
