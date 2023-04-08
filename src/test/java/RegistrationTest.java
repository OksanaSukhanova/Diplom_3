import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import net.bytebuddy.utility.RandomString;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.praktikum.Base;
import ru.yandex.praktikum.RestRequests;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.page.LoginPage;
import ru.yandex.praktikum.page.RegistrationPage;

public class RegistrationTest {
    private Base base;
    private WebDriver driver;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private User user;
    private RestRequests restRequests;

    @Before
    public void setUp() {
        base = new Base("chrome");
        driver = new ChromeDriver(base.chromeOptions());
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        user = UserGenerator.getRandom();
        restRequests = new RestRequests(driver);
    }

    @DisplayName("Registration with valid data")
    @Description("Check that registration with valid data is successful")
    @Test
    public void registrationWithValidDataIsSuccessful() {
        registrationPage.open().checkPageIsLoaded().register(user);

        String expectedHeader = "Вход";
        String actualHeader = loginPage.checkPageIsLoaded().getHeaderText();

        Assert.assertEquals("Регистрация не завершена", expectedHeader, actualHeader);
    }

    @DisplayName("Registration with invalid password")
    @Description("Check that registration with short password is unsuccessful")
    @Test
    public void registrationWithInvalidPasswordIsFailed() {
        user.setPassword(RandomString.make(4));
        registrationPage.open().checkPageIsLoaded().register(user);

        String expectedError = "Некорректный пароль";
        String actualError = registrationPage.getErrorText();

        Assert.assertEquals("Валидация пароля не сработала", expectedError, actualError);
    }

    @After
    public void clearData() {
        restRequests.deleteUser(user, restRequests.getTokenByUser(user));
        driver.quit();
    }
}
