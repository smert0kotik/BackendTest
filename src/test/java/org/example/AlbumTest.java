package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import java.util.ArrayList;


public class AlbumTest extends BaseTest {

    String createAlbumRequest() {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("title", "new_album")
                .when()
                .request("POST", "https://api.imgur.com/3/album")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @DisplayName("Album is created")
    void PostAlbum() {
        String albumHash = createAlbumRequest();
        assertThat(albumHash, not(equalTo(null)));
    }

    @Test
    @DisplayName("GetAlbum")
    void GetAlbum() {
        String albumHash = createAlbumRequest();
        Response response = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/album/{albumHash}", albumHash)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("data.title"), equalTo("new_album"));
    }

    @Test
    @DisplayName("GetAlbumImages")
    void GetAlbumImages() {
        String albumHash = createAlbumRequest();
        ArrayList<String> dataType = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/album/{albumHash}/images", albumHash)
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.type");

        assertThat(dataType.stream().allMatch((type) -> type.equals("image/jpeg")), equalTo(true));
    }

    @Test
    @DisplayName("AddImagesToAlbum")
    void PostImagesToAlbum() {
        String albumHash = createAlbumRequest();
         Response response = given()
                 .config(
                 RestAssured.config()
                 .encoderConfig(encoderConfig()
                 .encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                .headers(headers)
                .contentType("multipart/form-data")
                 .multiPart("ids[]", "fbnzTHv")
                .when()
                .request("POST", "https://api.imgur.com/3/album/{albumHash}/add", albumHash)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @DisplayName("PostFavouriteAlbum")
    void PostFavouriteAlbum() {
        String albumHash = createAlbumRequest();
        Response response = given()
                .headers(headers)
                .when()
                .request("POST", "https://api.imgur.com/3/album/{albumHash}/favorite", albumHash)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @DisplayName("DeleteAlbum")
    void DeleteAlbum() {
        String albumHash = createAlbumRequest();
        int statusCode = given()
                .headers(headers)
                .when()
                .delete("https://api.imgur.com/3/album/{albumHash}", albumHash)
                .then()
                .extract()
                .response()
                .statusCode();
        assertThat(statusCode, equalTo(200));
    }
}
