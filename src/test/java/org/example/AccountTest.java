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
                .get((String) properties.get("imageUrl"))
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("All image account_url matches given account")
    void getAccountImagesVerification() {
        ArrayList<String> str = given()
                .headers(headers)
                .when()
                .get((String) properties.get("imageUrl"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.account_url");
        assertThat(str.stream().allMatch((i) -> Objects.equals(i, "elvinrain13")), equalTo(true));
    }

    @Test
    @DisplayName("Status code is 200")
    void getAccountSettings() {
        given()
                .headers(headers)
                .when()
                .get((String) properties.get("imageUrl"))
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Username is correct")
    void getAccountSettingsUserName() {
        String url = given()
                .headers(headers)
                .when()
                .get((String) properties.get("accountUrl"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.account_url");
        assertThat(url, equalTo("elvinrain13"));
    }

    @Test
    @DisplayName("User email is correct")
    void getAccountSettingsUserEmail() {
        String email = given()
                .headers(headers)
                .when()
                .get((String) properties.get("accountUrl"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.email");
        assertThat(email, equalTo("elvinrain13@gmail.com"));
    }

    @Test
    @DisplayName("Status code is 200")
    void putAccountSettings() {
        given()
                .headers(headers)
                .when()
                .put(((String) properties.get("settingsUrl")),"elvinrain13")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Settings are changed")
    void putAccountSettingsTrue() {
        given()
                .headers(headers)
                .when()
                .put(((String) properties.get("settingsUrl")),"elvinrain13")
                .then()
                .body("success",equalTo(true));
    }
}



