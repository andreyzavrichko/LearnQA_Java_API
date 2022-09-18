import io.restassured.RestAssured;

import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
            Thread.sleep(time * 1000L);
        }
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

    @Test
    void ex9Test() {
        // 2019 год
        ArrayList<String> passwords = new ArrayList<>();
        passwords.add("123456");
        passwords.add("123456789");
        passwords.add("qwerty");
        passwords.add("password");
        passwords.add("1234567");
        passwords.add("12345678");
        passwords.add("12345");
        passwords.add("iloveyou");
        passwords.add("111111");
        passwords.add("123123");
        passwords.add("abc123");
        passwords.add("qwerty123");
        passwords.add("1q2w3e4r");
        passwords.add("admin");
        passwords.add("qwertyuiop");
        passwords.add("654321");
        passwords.add("555555");
        passwords.add("lovely");
        passwords.add("7777777");
        passwords.add("welcome");
        passwords.add("888888");
        passwords.add("princess");
        passwords.add("dragon");
        passwords.add("password1");
        passwords.add("123qwe");


        for (String pass : passwords) {
            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", pass);
            Response getSecretPass = RestAssured
                    .given()
                    .body(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String cookie = getSecretPass.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", cookie);

            Response checkCookie = RestAssured
                    .given()
                    .body(params)
                    .cookies(cookies)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            String answer = checkCookie.asString();

            if (!answer.equals("You are NOT authorized")) {
                System.out.println(answer);
                System.out.println(pass);
                break;
            }
        }


    }

}








