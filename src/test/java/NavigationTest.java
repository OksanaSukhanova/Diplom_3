import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.praktikum.Base;
import ru.yandex.praktikum.RestRequests;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.page.*;

public class NavigationTest {
    private Base base;
    private WebDriver driver;
    private User user;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private Header header;
    private RestRequests restRequests;

    @Before
    public void setUp() {
        base = new Base("chrome");
        driver = new ChromeDriver(base.chromeOptions());
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        accountPage = new AccountPage(driver);
        header = new Header(driver);
        restRequests = new RestRequests(driver);
        user = UserGenerator.getRandom();
        registrationPage.open().checkPageIsLoaded().register(user);
        loginPage.checkPageIsLoaded().loginBy(user);
        mainPage.checkPageIsLoaded();
        header.checkIsLoaded().toAccountPageByAccountButton();
    }

    @DisplayName("Open constructor from account page by constructor button")
    @Description("Check that constructor opens by constructor button from account page")
    @Test
    public void constructorButtonInAccountLeadsToConstructor() {
        accountPage.checkPageIsLoaded();
        header.checkIsLoaded().toMainPageByConstructorButton();
        mainPage.checkPageIsLoaded();
    }

    @DisplayName("Open constructor from account page by logo")
    @Description("Check that constructor opens by logo from account page")
    @Test
    public void logoInAccountLeadsToConstructor() {
        accountPage.checkPageIsLoaded();
        header.checkIsLoaded().toMainPageByLogo();
        mainPage.checkPageIsLoaded();
    }

    @After
    public void clearData() {
        restRequests.deleteUser(user, restRequests.getTokenFromLocalStorage());
        driver.quit();
    }
}
