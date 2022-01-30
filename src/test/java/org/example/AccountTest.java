package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.util.*;

public class AccountTest extends BaseTest {
    @Test
    @DisplayName("GetAccountImages")
    void GetAccountImages() {
        given()
                .headers(headers)
                .when()
                .get((String) properties.get("imageUrl"));
    }

    @Test
    @DisplayName("All image account_url matches given account")
    void getAccountImagesVerification() {
        List<ImageDTO> accountUrl = given()
                .headers(headers)
                .when()
                .get((String) properties.get("imageUrl"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList("data", ImageDTO.class);

        assertThat(accountUrl.stream().allMatch((image) -> image.getAccount_url()
                .equals(testUser.getAccount_url())), equalTo(true));
    }

    @Test
    @DisplayName("GetAccountSettings")
    void getAccountSettings() {
        given()
                .headers(headers)
                .when()
                .get((String) properties.get("imageUrl"));
    }

    @Test
    @DisplayName("Username is correct")
    void getAccountSettingsUserName() {
        User url = given()
                .headers(headers)
                .when()
                .get((String) properties.get("accountUrl"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getObject("data", User.class);

        assertThat(url.account_url, equalTo(testUser.account_url));
    }

    @Test
    @DisplayName("User email is correct")
    void getAccountSettingsUserEmail() {
        User email = given()
                .headers(headers)
                .when()
                .get((String) properties.get("accountUrl"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getObject("data", User.class);

        assertThat(email.email, equalTo(testUser.email));
    }

    @Test
    @DisplayName("Status code is 200")
    void putAccountSettings() {
        given()
                .headers(headers)
                .when()
                .put(((String) properties.get("settingsUrl")),testUser.account_url);
    }

    @Test
    @DisplayName("Settings are changed")
    void putAccountSettingsTrue() {
        given()
                .headers(headers)
                .when()
                .put(((String) properties.get("settingsUrl")),testUser.account_url)
                .then()
                .body("success",equalTo(true));
    }
}



