package requests;

import data.User;
import data.response.UserResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequests {
    public static Response createUser(User user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    public static Response deleteUser(UserResponse userResponse) {
        return given().log().all()
                .header("Content-type", "application/json")
                .header("authorization", userResponse.getToken())
                .when()
                .delete("/api/auth/user");
    }

    public static Response loginUser(User user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login");

    }

    public static Response updateUserDataWithoutToken(User user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .patch("/api/auth/user");
    }

    public static Response updateUserWithToken(UserResponse userResponse, User user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .header("authorization", userResponse.getToken())
                .body(user)
                .when()
                .patch("/api/auth/user");
    }
}
