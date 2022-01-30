package org.example;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {
    static Map<String, String> headers = new HashMap<>();
    static Properties properties = new Properties();
    static ResponseSpecification responseSpecification;
    static User testUser = new User();
    static {
        testUser.setAccount_url("elvinrain13");
        testUser.setEmail("elvinrain13@gmail.com");
        testUser.setTag("wallpapers");
    }

    @BeforeAll
    static void setUp() throws IOException {
        headers.put("Authorization", "Bearer 17958e4d5d5345e12c330b51da55c1e1fb5ef222");

        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream("src/test/resources/my.properties");
        properties.load(fileInputStream);
    }

    @BeforeEach
    void init() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
