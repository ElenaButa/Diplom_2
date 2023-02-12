package requests;

import data.response.UserResponse;
import data.order.OrderRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderRequests {
    public static Response createOrderWithAuthorization(UserResponse userResponse, OrderRequest orderRequest) {
        if (null != orderRequest) {
            return given().log().all()
                    .header("Content-type", "application/json")
                    .header("authorization", userResponse.getToken())
                    .and()
                    .body(orderRequest)
                    .when()
                    .post("/api/orders");
        }
        return given().log().all()
                .header("Content-type", "application/json")
                .header("authorization", userResponse.getToken())
                .when()
                .post("/api/orders");
    }

    public static Response createOrderWithoutAuthorization(OrderRequest orderRequest) {
        if (null != orderRequest) {
            return given().log().all()
                    .header("Content-type", "application/json")
                    .and()
                    .body(orderRequest)
                    .when()
                    .post("/api/orders");
        }
        return given().log().all()
                .header("Content-type", "application/json")
                .when()
                .post("/api/orders");
    }

    public static Response getOrdersWithAuthorization(UserResponse userResponse) {
        return given().log().all()
                .header("Content-type", "application/json")
                .header("authorization", userResponse.getToken())
                .when()
                .get("/api/orders");
    }

    public static Response getOrdersWithoutAuthorization() {
        return given().log().all()
                .header("Content-type", "application/json")
                .when()
                .get("/api/orders");
    }
}
