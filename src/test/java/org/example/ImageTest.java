package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ImageTest extends BaseTest {

    String uploadImage() {
        File file = new File("src/test/resources/allure_screen.jpg");
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("image", file, "image/jpeg")
                .multiPart("type", "file")
                .multiPart("name", "allure_screen.jpg")
                .when()
                .request("POST", "https://api.imgur.com/3/upload")
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.id");
    }

    @Test
    @DisplayName("PostImageUpload")
    void PostImageUpload() {

        String imageHash = uploadImage();
        assertThat(imageHash, not(equalTo(null)));
    }

    @Test
    @DisplayName("GetImage")
    void GetImage() {
        String imageHash = uploadImage();
        given()
                .headers(headers)
                .when()
                .get((String) properties.get("imageHashUrl"), imageHash);
    }

    @Test
    @DisplayName("PostUpdateImage")
    void PostUpdateImage() {
        String imageHash = uploadImage();
        given()
                .headers(headers)
                .when()
                .post((String) properties.get("imageHashUrl"), imageHash);
    }

    @Test
    @DisplayName("DeleteImage")
    void DeleteImage() {
        String imageHash = uploadImage();
        given()
                .headers(headers)
                .when()
                .delete((String) properties.get("imageHashUrl"), imageHash);
        }
    }

