import io.restassured.RestAssured;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;



class lessonTwo {
    @Test
    void testExample() {


        Response response = RestAssured
               .given()
               .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

//print(json_text['messages'][0])
       //String answer = response.prettyPrint();

        response.print();

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
}
