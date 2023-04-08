package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;
import ru.yandex.praktikum.model.User;

import static io.restassured.RestAssured.given;

public class RestRequests {
    private final WebDriver driver;

    public RestRequests(WebDriver driver) {
        this.driver = driver;
    }

    // Где возможно, использую метод получения токена из localStorage. Предполагаю, что он быстрее
    public String getTokenFromLocalStorage() {
        return ((WebStorage) driver)
                .getLocalStorage()
                .getItem("accessToken");
    }

    // Этот метод нужен, когда пользователь создан, но не авторизован
    public String getTokenByUser(User user) {
        String json = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", user.getEmail(), user.getPassword());

        ValidatableResponse loginResponse = given()
                .headers("Content-Type", "application/json")
                .body(json)
                .when().post("https://stellarburgers.nomoreparties.site/api/auth/login").then();

        return loginResponse.extract().path("accessToken");
    }

    @Step("Delete user {user}")
    public void deleteUser(User user, String bearerToken) {
        String json = String.format("{\"email\": \"%s\"}", user.getEmail());

        try {
            given()
                    .headers("Content-Type", "application/json")
                    .headers("Authorization", bearerToken)
                    .body(json)
                    .when().delete("https://stellarburgers.nomoreparties.site/api/auth/user").then();
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }
}
