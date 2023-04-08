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

public class AccountTest {
    private Base base;
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private AccountPage accountPage;
    private Header header;
    private RestRequests restRequests;
    private User user;

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
        header.checkIsLoaded().toAccountPageByAccountButton();
    }

    @DisplayName("Open user account by account button in header")
    @Description("Check that account button opens authorized user account")
    @Test
    public void accountButtonOpensUserAccount() {
        accountPage.checkPageIsLoaded().checkAccountIsVisible();
    }

    @DisplayName("Exit user account by exit button in account")
    @Description("Check that exit button closes authorized user account")
    @Test
    public void exitButtonEndsAuthorization() {
        accountPage.checkPageIsLoaded().exitFromAccount();
        loginPage.checkPageIsLoaded().checkLoginFormIsVisible();
    }

    @After
    public void clearData() {
        restRequests.deleteUser(user, restRequests.getTokenByUser(user));
        driver.quit();
    }
}
