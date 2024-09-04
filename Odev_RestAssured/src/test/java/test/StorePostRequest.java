package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import request.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class StorePostRequest {

    @DataProvider(name = "postData")
    public Object[][] postData() {
        return new Object[][] {
                {1, 1, 1, "2025-08-27T23:06:39.586+0000", "placed", true}
        };
    }

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test(dataProvider = "postData")
    public void postRequest(int id, int petId, int quantity, String shipDate, String status, Boolean complete) {
        String url = "/store/order";

            Map<String, Object> header = new HashMap<>();
            header.put("Accept", "application/json");
            header.put("Content-Type", "application/json");

            String contentType = ContentType.JSON.toString();

            PostRequest request = PostRequest.builder()
                    .id(id)
                    .petId(petId)
                    .quantity(quantity)
                    .shipDate(shipDate)
                    .status(status)
                    .complete(complete)
                    .build();

            Response response = RestAssured.given()
                    .contentType(contentType)
                    .headers(header)
                    .body(request)
                    .when().log().all()
                    .post(url);

            response.then().log().all()
                    .statusCode(200);

            Assertions.assertThat(response.statusCode()).isNotEqualTo(400);
            Assertions.assertThat(response.body().jsonPath().getString("status")).isEqualTo(status);
            Assertions.assertThat(response.body().jsonPath().getBoolean("complete")).isTrue();
    }
}
