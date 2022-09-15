import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

class lessonTwo {
    @Test
    void testExample() {

        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

       // response.prettyPrint();
        String answer = response.get("");
        System.out.println(answer);
    }
}
