package com.mamunetond;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.web.bind.annotation.*;

public class LoginBookStoreTest {

    private static final String url = "https://demoqa.com/login";
    private String userId;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.standaloneSetup(new AuthController())
                        .build()
        );
    }

    public String mockGenerateToken() {
        String accessToken = "mockedAccessToken";

        Response mockedResponse = Mockito.mock(Response.class);
        when(mockedResponse.statusCode()).thenReturn(200);
        when(mockedResponse.getBody().asString()).thenReturn("{ \"token\": \"" + accessToken + "\" }");
        when(given().post(anyString())).thenReturn(mockedResponse);

        return accessToken;
    }

    @Test
    public void reto1() {
        String token = mockGenerateToken();
        assertEquals("mockedAccessToken", token);
    }

    // Method to create a user

    public void createUser() {
        String endpoint = "/Account/v1/User";
        String requestBody =  "{\n" +
                "  \"userName\": \"mamunetond\",\n" +
                "  \"password\": \"Marioalejandro4*\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(mockGenerateToken())
                .body(requestBody)
                .when()
                .post(url + endpoint)
                .then()
                .statusCode(201)
                .extract().response();

        // Get the userID to use later

        userId = response.jsonPath().getString("userID");

        // Assert to validate the correct creation of the user

        assertEquals(201, response.statusCode());
        System.out.println("User created with ID: " + userId);
    }

    // Method to log in a user

    public void loginUser() {
        String endpoint = "/Account/v1/Login";
        String requestBody = "{\n" +
                "   \"userName\": \"mamunetond\",\n" +
                "   \"password\": \"Marioalejandro4*\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(url + endpoint)
                .then()
                .statusCode(200)
                .extract().response();

        // Assert to validate successful authentication

        String message = response.jsonPath().getString("message");
        assertEquals("User logged in successfully.", message);
        System.out.println("Login successful with response: " + response.asString());
    }

    // Method to delete the user

    public void deleteUser() {
        String endpoint = "/Account/v1/User/" + userId;

        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(mockGenerateToken())
                .when()
                .delete(url + endpoint)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("User deleted with response: " + response.asString());
    }

    // Method to validate login after deleting the user

    public void loginAfterDelete() {
        String endpoint = "/Account/v1/Login";
        String requestBody = "{\n" +
                "   \"userName\": \"mamunetond\",\n" +
                "   \"password\": \"Marioalejandro4*\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(url + endpoint)
                .then()
                .statusCode(401)  // Authentication error expected
                .extract().response();

        // Assert to validate the error message

        String errorMessage = response.jsonPath().getString("message");
        assertEquals("Invalid username or password", errorMessage);
        System.out.println("Error message: " + errorMessage);
    }
}
