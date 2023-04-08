package ru.yandex.praktikum.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {
    private WebDriver driver;

    private static final String URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    // Заголовок формы восстановления пароля
    private static final By HEADER = By.cssSelector("div[class*='Auth_login']>h2");
    // Ссылка "Вспомнили пароль?"
    private static final By LOGIN_LINK = By.cssSelector("a[href = '/login']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Check that forgot password page is loaded")
    public ForgotPasswordPage checkPageIsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(URL));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(HEADER)));
        return this;
    }

    @Step("Click on login link")
    public void toLoginPage() {
        driver.findElement(LOGIN_LINK).click();
    }
}
