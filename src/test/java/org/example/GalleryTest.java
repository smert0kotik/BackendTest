package org.example;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GalleryTest extends BaseTest {

    @Test
    @DisplayName("GetGalleryTag")

    void GetGalleryTag() {
        Response response = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/gallery/t/{tagName}", "wallpapers")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("data.name"), equalTo("wallpapers"));
    }

    @Test
    @DisplayName("GetGallerySearch")

    void GetGallerySearch() {
        Response response = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/gallery/search/?q_exactly=cats")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @DisplayName("PostGalleryComment")
    void PostGalleryComment() {
        Response response = given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("comment", "test")
                .when()
                .request("POST","https://api.imgur.com/3/gallery/{galleryHash}/comment", "VQBUKqR")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @DisplayName("GetGalleryComment")
    void GetGalleryComment() {
        Response response = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/gallery/{galleryHash}/comment/{commentId}", "VQBUKqR", "2187802841")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("data.author"), equalTo("elvinrain13"));
    }

    @Test
    @DisplayName("PostGalleryVote")
    void PostGalleryVote() {
        Response response = given()
                .headers(headers)
                .when()
                .request("POST","https://api.imgur.com/3/gallery/{galleryHash}/vote/{vote}", "VQBUKqR", "up")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(200));
    }
}

