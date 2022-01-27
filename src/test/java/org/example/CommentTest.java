package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class CommentTest extends BaseTest {

    String createComment() {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("image_id", "bJqXRNy")
                .multiPart("comment", "test")
                .when()
                .request("POST", "https://api.imgur.com/3/comment")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @DisplayName("Comment is created")
    void PostComment() {
        String commentId = createComment();
        assertThat(commentId, not(equalTo(null)));
    }

    @Test
    @DisplayName("Comment is deleted")
    void DeleteComment() {
        String commentId = createComment();
        int statusCode = given()
                .headers(headers)
                .when()
                .delete("https://api.imgur.com/3/comment/{commentId}", commentId)
                .then()
                .extract()
                .response()
                .statusCode();
        assertThat(statusCode, equalTo(200));
    }
}
