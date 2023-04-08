package ru.yandex.praktikum.page;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class MainPage {
    private WebDriver driver;

    private static final String URL = "https://stellarburgers.nomoreparties.site/";
    // Заголовок конструктора
    private static final By HEADER = By.xpath(".//h1[text() = 'Соберите бургер']");
    // Кнопка авторизации, расположенная под формой заказа (для неавторизованного пользователя)
    private static final By LOGIN_BUTTON = By.xpath(".//button[text() = 'Войти в аккаунт']");
    // Кнопка оформления заказа, расположенная под формой заказа (для авторизованного пользователя)
    private static final By CREATE_ORDER_BUTTON = By.xpath(".//button[text() = 'Оформить заказ']");
    // Вкладки с ингредиентами
    private static final By CONSTRUCTOR_TAB_BUNS = By.xpath(".//span[text() = 'Булки']");
    private static final By CONSTRUCTOR_TAB_SAUCES = By.xpath(".//span[text() = 'Соусы']");
    private static final By CONSTRUCTOR_TAB_FILLINGS = By.xpath(".//span[text() = 'Начинки']");
    // Заголовки блоков внутри конструктора
    private static final By BUNS_BLOCK_HEADER = By.xpath(".//h2[text() = 'Булки']");
    private static final By SAUCES_BLOCK_HEADER = By.xpath(".//h2[text() = 'Соусы']");
    private static final By FILLINGS_BLOCK_HEADER = By.xpath(".//h2[text() = 'Начинки']");
    // Ингредиенты в конструкторе
    private static final By INGREDIENTS = By.cssSelector("a[class*='BurgerIngredient']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open main page by URL")
    public MainPage open() {
        driver.get(URL);
        return this;
    }

    @Step("Check that main page is loaded")
    public MainPage checkPageIsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(HEADER));
        return this;
    }

    @Step("Go to login page by login button")
    public void toLoginPageByLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }

    @Step("Check that authorization is successful")
    public void checkIfCreateOrderButtonIsVisible() {
        assertTrue(driver.findElement(CREATE_ORDER_BUTTON).getText().contains("Оформить заказ"));
    }

    @Step("Check that tabs in constructor can be selected")
    public void checkTabsAreSelected() throws InterruptedException {
        // Собираем селекторы вкладок в список
        List<By> tabs = new ArrayList<>();
        tabs.add(CONSTRUCTOR_TAB_FILLINGS);
        tabs.add(CONSTRUCTOR_TAB_BUNS);
        tabs.add(CONSTRUCTOR_TAB_SAUCES);
        // Собираем селекторы заголовков блоков в список
        List<By> blocksHeaders = new ArrayList<>();
        blocksHeaders.add(BUNS_BLOCK_HEADER);
        blocksHeaders.add(SAUCES_BLOCK_HEADER);
        blocksHeaders.add(FILLINGS_BLOCK_HEADER);
        // Ждем прогрузки шрифтов (нет атрибутов в DOM или стилей CSS, по которым можно дождаться загрузки явно)
        Thread.sleep(3000);

        for (int i = 0; i < tabs.size(); i++) {
            // Клик по вкладке
            driver.findElement(tabs.get(i)).click();
            // Находим родительский элемент для проверки атрибута
            WebElement tabParent = driver.findElement(tabs.get(i)).findElement(By.xpath("parent::div"));
            // Ожидаем, когда вкладка будет выделена - цвет шрифта станет белым
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(x -> tabParent.getCssValue("color").contains("rgba(255, 255, 255, 1)"));
            // Проверяем, что класс содержит нужное значение
            assertTrue(tabParent.getAttribute("class").contains("current"));
            // Проверяем, что нужный заголовок отображается
            assertTrue(driver.findElement(blocksHeaders.get(i)).isEnabled());
            // Проверяем, что ингредиенты в конструкторе отображаются
            assertTrue(driver.findElements(INGREDIENTS).size() > 0);
        }
    }
}
