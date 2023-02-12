package user;

import base.BaseTest;
import data.User;
import data.response.UserResponse;
import io.restassured.response.Response;
import org.junit.Test;
import requests.UserRequests;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginUserTest extends BaseTest {

    @Test
    public void createAndLoginUserTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        userResponse = response.as(UserResponse.class);

        response = UserRequests.loginUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void loginUserWithInvalidLoginTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        userResponse = response.as(UserResponse.class);

        user = new User("Alex@", user.getPassword());
        response = UserRequests.loginUser(user);
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void loginUserWithInvalidPasswordTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        userResponse = response.as(UserResponse.class);

        user = new User(user.getEmail(), "");
        response = UserRequests.loginUser(user);
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
