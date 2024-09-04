package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class StoreGetRequest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void getRequest(){
        String url = "/store/order/1";

        String contentType= ContentType.JSON.toString();

        Map<String, Object> header=new HashMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");

        Response response= RestAssured.given()
                .contentType(contentType)
                .headers(header)
                .when().log().all()
                .get(url);

        response.then().log().all()
                .statusCode(200);

        Assertions.assertThat(response.statusCode()).isNotEqualTo(400);
        Assertions.assertThat(response.body().jsonPath().getString("quantity")).isEqualTo("1");
        Assertions.assertThat(response.body().jsonPath().getString("shipDate")).isEqualTo("2025-08-27T23:06:39.586+0000");
    }
}
