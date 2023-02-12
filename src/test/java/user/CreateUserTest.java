package user;

import base.BaseTest;
import data.User;
import data.response.UserResponse;
import io.restassured.response.Response;
import org.junit.Test;
import requests.UserRequests;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreateUserTest extends BaseTest {

    @Test
    public void createNewUniqueUserTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        userResponse = response.as(UserResponse.class);
    }

    @Test
    public void createUserWhoIsAlreadyRegistered() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        userResponse = response.as(UserResponse.class);

        response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body(" message", equalTo("User already exists"));
    }

    @Test
    public void createUserWithoutEmail() {
        User user = new User("", "123456", "Alex");
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutPassword() {
        User user = new User("Alex4455@gmail.com", "", "Alex");
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutName() {
        User user = new User("Alex4455@gmail.com", "123456", "");
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

}
