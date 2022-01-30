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
                .get("https://api.imgur.com/3/gallery/t/{tagName}", testUser.tag)
                .then()
                .extract()
                .response();

        assertThat(response.jsonPath().getString("data.name"), equalTo(testUser.tag));
    }

    @Test
    @DisplayName("GetGallerySearch")

    void GetGallerySearch() {
        given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/gallery/search/?q_exactly=cats");
    }

    @Test
    @DisplayName("PostGalleryComment")
    void PostGalleryComment() {
        given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("comment", "test")
                .when()
                .request("POST","https://api.imgur.com/3/gallery/{galleryHash}/comment",properties.get("galleryHash"));
    }

    @Test
    @DisplayName("GetGalleryComment")
    void GetGalleryComment() {
        User comment = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/gallery/{galleryHash}/comment/{commentId}",properties.get("galleryHash"),"2187802841")
                .then()
                .extract()
                .response()
                .jsonPath()
                .getObject("data", User.class);

        assertThat(comment.author, equalTo(testUser.account_url));
    }

    @Test
    @DisplayName("PostGalleryVote")
    void PostGalleryVote() {
        given()
                .headers(headers)
                .when()
                .request("POST","https://api.imgur.com/3/gallery/{galleryHash}/vote/{vote}",properties.get("galleryHash"),"up");
    }
}

