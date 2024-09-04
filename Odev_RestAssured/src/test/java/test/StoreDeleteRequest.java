package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class StoreDeleteRequest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void deleteRequest() {
        String url = "/store/order/1";

        Map<String, Object> header = new HashMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(header)
                .when().log().all()
                .delete(url);

        response.then().log().all()
                .statusCode(200);

        if (response.body().asString().isEmpty()) {
            Assertions.assertThat(response.body().jsonPath().getString("code")).isEqualTo("200");
            Assertions.assertThat(response.body().jsonPath().getString("type")).isEqualTo("unknown");
            Assertions.assertThat(response.body().jsonPath().getString("message")).isEqualTo("1");
        }
    }
}
