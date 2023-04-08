import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.praktikum.Base;
import ru.yandex.praktikum.page.MainPage;

public class ConstructorTest {
    private Base base;
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        base = new Base("chrome");
        driver = new ChromeDriver(base.chromeOptions());
        mainPage = new MainPage(driver);
    }

    @DisplayName("Select ingredient tabs in constructor")
    @Description("Check that all ingredient tabs can be selected")
    @Test
    public void clickOnTabInConstructorSelectsTab() throws InterruptedException {
        mainPage.open().checkPageIsLoaded().checkTabsAreSelected();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
