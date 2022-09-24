import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

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

    @ParameterizedTest
    @CsvSource(value = {"'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30', Mobile, No, Android",
            "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1', Mobile, Chrome, iOS",
            "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', Googlebot, Unknown, Unknown",
            "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0', Web, Chrome, No",
            "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1', Mobile, No, iPhone"
    })
    void ex13Test(String user_agent, String platform, String browser, String device) {

        Map<String, String> query = new HashMap<>();
        query.put("user_agent", user_agent);
        query.put("platform", platform);
        query.put("browser", browser);
        query.put("device", device);

        JsonPath response = RestAssured
                .given()
                .header("User-Agent", user_agent)
                .get("https://playground.learnqa.ru/api/user_agent_check")
                .jsonPath();
        response.prettyPrint();

        String platformRes = response.getJsonObject("platform");
        String browserRes = response.getJsonObject("browser");
        String deviceRes = response.getJsonObject("device");

        System.out.println(user_agent);
        if (!platform.equals(platformRes)) {
            System.out.println("платформа неправильная: " + platform + "; ожидаем: " + platformRes);
        }
        if (!browser.equals(browserRes)) {
            System.out.println("браузер неправильный: " + browser + "; ожидаем: " + browserRes);
        }
        if (!device.equals(deviceRes)) {
            System.out.println("девайс неправильный: " + platform + "; ожидаем: " + deviceRes);
        }
        assertTrue((platform.equals(platformRes)) && (browser.equals(browserRes)) && (device.equals(deviceRes)), "Ошибка");
    }

}








