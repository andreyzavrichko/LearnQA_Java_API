import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class lessonThree {
    @ParameterizedTest
    @ValueSource(strings = {"123456789012345", "1234567890123456", "12345"})
    void ex10Test(String inputValue) {
        assertTrue(inputValue.length() >= 15, "Значение inputValue больше или равно 15: " + inputValue);
    }

    @Test
    void ex11Test() {
        Response response = RestAssured
                .given()
                .log().all()
                .when()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        assertEquals("hw_value", response.getCookie("HomeWork"));
        System.out.println(response.getCookies());
    }

    @Test
    void ex12Test() {
        Response response = RestAssured
                .given()
                .log().all()
                .when()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        assertEquals("Apache", response.getHeader("server"));
        assertEquals("15", response.getHeader("content-length"));
        assertEquals("Some secret value", response.getHeader("x-secret-homework-header"));
        System.out.println(response.getHeaders());
    }
}







