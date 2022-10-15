package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static lib.ApiCoreRequests.*;

class UserDeleteTest extends BaseTestCase {
    @Test
    @Epic("Удаление пользователей")
    @DisplayName("Удаление пользователя по ID 2")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("example.com")
    void testDeleteUser() {
        Response responseGetAuth = authRequest("vinkotov@example.com", "1234");
        Response responseDelete = deleteUserRequest(
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"), "2");

        Assertions.assertResponseTextEquals(responseDelete, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
    }

    @Test
    @Epic("Удаление пользователей")
    @DisplayName("Удаление созданного пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("example.com")
    void testDeleteCreatedUser() {
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = generateUserRequest(userData);
        String userId = responseCreateAuth.getString("id");
        String userEmail = userData.get("email");
        Response responseGetAuth = authRequest(userEmail, userData.get("password"));

        deleteUserRequest(
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                userId);

        Response userDataResponse = getUserDataRequest(
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                userId);
        Assertions.assertResponseTextEquals(userDataResponse, "User not found");
    }

    @Test
    @Epic("Удаление пользователей")
    @DisplayName("Удаление пользователя, авторизованным другим пользователем")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("example.com")
    void testDeleteAnotherUser() {
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = generateUserRequest(userData);
        String userId = responseCreateAuth.getString("id");
        String userEmail = userData.get("email");
        Response responseSecondGetAuth = authRequest("vinkotov@example.com", "1234");

        Response responseDelete = deleteUserRequest(
                this.getHeader(responseSecondGetAuth, "x-csrf-token"),
                this.getCookie(responseSecondGetAuth, "auth_sid"),
                userId);

        Assertions.assertResponseTextEquals(responseDelete, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

        Response responseGetAuth = authRequest(userEmail, userData.get("password"));

        Response userDataResponse = getUserDataRequest(
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                userId);
        Assertions.assertJsonByName(userDataResponse, "id", userId);
        Assertions.assertJsonByName(userDataResponse, "username", userData.get("username"));
        Assertions.assertJsonByName(userDataResponse, "email", userData.get("email"));
        Assertions.assertJsonByName(userDataResponse, "firstName", userData.get("firstName"));
        Assertions.assertJsonByName(userDataResponse, "lastName", userData.get("lastName"));
    }
}
