package ru.yandex.praktikum;

import org.openqa.selenium.chrome.ChromeOptions;

public class Base {
    private String browser;

    public Base(String browser) {
        this.browser = browser;
    }

    public ChromeOptions chromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (browser.equals("chrome")) {
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--headless");
        } else if (browser.equals("yandex")) {
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--headless");
            System.setProperty("webdriver.chrome.driver", "C:/Tools/yandexdriver.exe");
        } else {
            System.out.println("Некорректное имя браузера");
        }
        return chromeOptions;
    }
}
