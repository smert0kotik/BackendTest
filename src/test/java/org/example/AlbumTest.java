package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import java.util.List;

public class AlbumTest extends BaseTest {

    AlbumDTO createAlbumRequest() {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("title", "new_album")
                .when()
                .request("POST", "https://api.imgur.com/3/album")
                .then()
                .extract()
                .jsonPath()
                .getObject("data", AlbumDTO.class);
    }

    @Test
    @DisplayName("Album is created")
    void PostAlbum() {
        AlbumDTO album = createAlbumRequest();
        assertThat(album.getId(), not(equalTo(null)));
    }

    @Test
    @DisplayName("GetAlbum")
    void GetAlbum() {
        AlbumDTO album = createAlbumRequest();
        AlbumDTO response = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/album/{albumHash}", album.getId())
                .then()
                .extract()
                .response()
                .jsonPath().getObject("data", AlbumDTO.class);

        assertThat(response.getTitle(), equalTo("new_album"));
    }

    @Test
    @DisplayName("GetAlbumImages")
    void GetAlbumImages() {
        AlbumDTO album = createAlbumRequest();
        List<ImageDTO> dataType = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/album/{albumHash}/images", album.getId())
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList("data", ImageDTO.class);

        assertThat(dataType.stream().allMatch((image) -> image.getType().equals("image/jpeg")), equalTo(true));
    }

    @Test
    @DisplayName("AddImagesToAlbum")
    void PostImagesToAlbum() {
        AlbumDTO album = createAlbumRequest();
         given()
                 .config(
                 RestAssured.config()
                 .encoderConfig(encoderConfig()
                 .encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                 .headers(headers)
                 .contentType("multipart/form-data")
                 .multiPart("ids[]", "fbnzTHv")
                 .when()
                 .request("POST", "https://api.imgur.com/3/album/{albumHash}/add", album.getId());
    }

    @Test
    @DisplayName("PostFavouriteAlbum")
    void PostFavouriteAlbum() {
        AlbumDTO album = createAlbumRequest();
        given()
                .headers(headers)
                .when()
                .request("POST", "https://api.imgur.com/3/album/{albumHash}/favorite", album.getId());
    }

    @Test
    @DisplayName("DeleteAlbum")
    void DeleteAlbum() {
        AlbumDTO album = createAlbumRequest();
        given()
                .headers(headers)
                .when()
                .delete("https://api.imgur.com/3/album/{albumHash}", album.getId());
    }
}
