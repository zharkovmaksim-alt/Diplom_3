package api;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserApi extends BaseClient {
    private static final Gson gson = new Gson();

    @Step("Создание пользователя: {email}")
    public Response createUser(String email, String password, String name) {
        UserRequest request = new UserRequest(email, password, name);
        String body = gson.toJson(request);
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/auth/register");
    }

    @Step("Логин пользователя: {email}")
    public String getAccessToken(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        String body = gson.toJson(request);
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/auth/login");
        System.out.println("Ответ на логин: " + response.asString());
        return response.jsonPath().getString("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            RestAssured.given()
                    .header("Authorization", accessToken)
                    .delete("/auth/user");
        }
    }
}