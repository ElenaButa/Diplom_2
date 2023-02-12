package user;

import base.BaseTest;
import data.User;
import data.response.UserResponse;
import data.GenerateUser;
import io.restassured.response.Response;
import org.junit.Test;
import requests.UserRequests;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.core.IsEqual.equalTo;

public class ChangingUserDataTest extends BaseTest {
    @Test
    public void changeUserDataWithoutAuthorizationTest() {
        User user = new User();
        userResponse = UserRequests.createUser(user).as(UserResponse.class);

        Response response = UserRequests.updateUserDataWithoutToken(user);
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    public void changeUserDataWithAuthorizationTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;

        User userEmail = new User(GenerateUser.generateEmail(), user.getPassword(), user.getName());
        UserRequests.updateUserWithToken(userResponse, userEmail)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(userEmail.getEmail()));
    }
}
