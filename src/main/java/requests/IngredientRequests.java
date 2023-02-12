package requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientRequests {

    public static Response getIngredientsList() {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/ingredients");
    }
}
