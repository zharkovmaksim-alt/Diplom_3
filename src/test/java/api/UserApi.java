package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserApi {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    public Response createUser(String email, String password, String name) {
        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}", email, password, name);
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(BASE_URL + "/auth/register");
    }

    public String getAccessToken(String email, String password) {
        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(BASE_URL + "/auth/login");
        System.out.println("Ответ на логин: " + response.asString());
        return response.jsonPath().getString("accessToken");
    }

    public void deleteUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            RestAssured.given()
                    .header("Authorization", accessToken)
                    .delete(BASE_URL + "/auth/user");
        }
    }
}