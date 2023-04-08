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

public class LoginTest {
    private Base base;
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private ForgotPasswordPage forgotPasswordPage;
    private Header header;
    private User user;
    private RestRequests restRequests;

    @Before
    public void setUp() {
        base = new Base("chrome");
        driver = new ChromeDriver(base.chromeOptions());
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        header = new Header(driver);
        restRequests = new RestRequests(driver);
        user = UserGenerator.getRandom();
        registrationPage.open().checkPageIsLoaded().register(user);
    }

    @DisplayName("Authorization by account button in header")
    @Description("Check that authorization by account button is possible")
    @Test
    public void loginByAccountButtonIsSuccessful() {
        mainPage.open().checkPageIsLoaded();
        header.checkIsLoaded().toAccountPageByAccountButton();
        loginPage.checkPageIsLoaded().loginBy(user);
        mainPage.checkPageIsLoaded().checkIfCreateOrderButtonIsVisible();
    }

    @DisplayName("Authorization by login button under order form")
    @Description("Check that authorization by login button is possible")
    @Test
    public void loginByLoginButtonIsSuccessful() {
        mainPage.open().checkPageIsLoaded().toLoginPageByLoginButton();
        loginPage.checkPageIsLoaded().loginBy(user);
        mainPage.checkPageIsLoaded().checkIfCreateOrderButtonIsVisible();
    }

    @DisplayName("Authorization by link from registration page")
    @Description("Check that authorization by link from registration page is possible")
    @Test
    public void loginByLinkFromRegistrationPageIsSuccessful() {
        registrationPage.open().checkPageIsLoaded().toLoginPage();
        loginPage.checkPageIsLoaded().loginBy(user);
        mainPage.checkPageIsLoaded().checkIfCreateOrderButtonIsVisible();
    }

    @DisplayName("Authorization by link from forgot password page")
    @Description("Check that authorization by link from forgot password page is possible")
    @Test
    public void loginByLinkFromForgotPasswordPageIsSuccessful() {
        loginPage.open().checkPageIsLoaded().toForgotPasswordPage();
        forgotPasswordPage.checkPageIsLoaded().toLoginPage();
        loginPage.checkPageIsLoaded().loginBy(user);
        mainPage.checkPageIsLoaded().checkIfCreateOrderButtonIsVisible();
    }

    @After
    public void clearData() {
        restRequests.deleteUser(user, restRequests.getTokenFromLocalStorage());
        driver.quit();
    }
}
