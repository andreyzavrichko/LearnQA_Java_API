import io.restassured.RestAssured;

import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

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

    @Test
    void ex8Test() throws InterruptedException {
        JsonPath response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();
        String token = response.get("token");

        JsonPath beforeTask = RestAssured
                .given()
                .param("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String sc = beforeTask.get("status");
        beforeTask.prettyPrint();
        Assertions.assertEquals("Job is NOT ready", sc);

        if (sc.equals("Job is NOT ready")) {
        int time = response.get("seconds");
            Thread.sleep(time * 1000L);}
            JsonPath afterTaskIsReady = RestAssured
                    .given()
                    .with()
                    .param("token", token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            sc = afterTaskIsReady.get("status");
            afterTaskIsReady.prettyPrint();
            Assertions.assertEquals("Job is ready", sc);
            String result = afterTaskIsReady.get("result");

        Assertions.assertNotNull(result);
        }
















    }








