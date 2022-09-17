import io.restassured.RestAssured;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


class lessonTwo {
    @Test
    void ex5Test() {
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        String answer = response.get("messages.message[1]");
        System.out.println(answer);
    }


    @Test
    void ex6Test() {
        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        Header responseHeaders = response.getHeaders().get("X-Host");
        System.out.println(responseHeaders);

    }

    @Test
    void ex7Test() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String responseHeaders = String.valueOf(response.getHeader("Location"));
        System.out.println(responseHeaders);
        int sc = response.getStatusCode();
        System.out.println(sc);

        while (sc != 200) {
            response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(String.valueOf(responseHeaders))
                    .andReturn();

            responseHeaders = response.getHeader("Location");
            System.out.println(responseHeaders);
            sc = response.getStatusCode();
            System.out.println(sc);
        }
    }


}





