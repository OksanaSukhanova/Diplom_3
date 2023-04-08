package ru.yandex.praktikum.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Хэдер выделен в отдельный класс, так как он отображается на всех страницах
public class Header {
    private final WebDriver driver;

    // Кнопка "Личный кабинет"
    private static final By ACCOUNT_BUTTON = By.cssSelector("a[href='/account']");
    // Логотип сайта
    private static final By LOGO = By.cssSelector("div[class*='logo']");
    // Кнопка "Конструктор"
    private static final By CONSTRUCTOR_BUTTON = By.xpath(".//p[text() = 'Конструктор']");

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Check that header is loaded")
    public Header checkIsLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(LOGO)));
        return this;
    }

    @Step("Go to account page by account button")
    public void toAccountPageByAccountButton() {
        driver.findElement(ACCOUNT_BUTTON).click();
    }

    @Step("Go to main page by constructor button")
    public void toMainPageByConstructorButton() {
        driver.findElement(CONSTRUCTOR_BUTTON).click();
    }

    @Step("Go to main page by logo")
    public void toMainPageByLogo() {
        driver.findElement(LOGO).click();
    }
}
