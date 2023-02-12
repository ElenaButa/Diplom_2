package order;

import base.BaseTest;
import data.User;
import data.response.UserResponse;
import data.ingredients.Ingredient;
import data.ingredients.Ingredients;
import data.order.OrderRequest;
import io.restassured.response.Response;
import org.junit.Test;
import requests.IngredientRequests;
import requests.OrderRequests;
import requests.UserRequests;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateOrderTest extends BaseTest {

    @Test
    public void createNewOrderWithAuthorizationAndWithIngredientsTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;
        List<String> ingredients = new ArrayList<>();
        response = IngredientRequests.getIngredientsList();
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));

        Ingredients dtoIngredients = response.as(Ingredients.class);
        Ingredient ingredient = dtoIngredients.getIngredientWithName("Флюоресцентная булка R2-D3");
        ingredients.add(ingredient.getId());
        ingredients.add(ingredient.getId());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIngredients(ingredients);

        OrderRequests.createOrderWithAuthorization(userResponse, orderRequest);

        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void createNewOrderWithAuthorizationAndWithoutIngredientsTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;

        OrderRequests.createOrderWithAuthorization(userResponse, null)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void createNewOrderWithoutAuthorizationAndWithIngredientsTest() {
        List<String> ingredients = new ArrayList<>();
        Response response = IngredientRequests.getIngredientsList();
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));

        Ingredients dtoIngredients = response.as(Ingredients.class);
        Ingredient ingredient = dtoIngredients.getIngredientWithName("Флюоресцентная булка R2-D3");
        ingredients.add(ingredient.getId());
        ingredients.add(ingredient.getId());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIngredients(ingredients);

        OrderRequests.createOrderWithoutAuthorization(orderRequest);

        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void createNewOrderWithoutAuthorizationAndWithoutIngredientsTest() {
        OrderRequests.createOrderWithoutAuthorization(null)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }


    @Test
    public void createNewOrderWithAuthorizationAndWithInvalidIngredientsTest() {
        User user = new User();
        Response response = UserRequests.createUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;
        List<String> ingredients = new ArrayList<>();
        ingredients.add("soil");

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIngredients(ingredients);

        OrderRequests.createOrderWithAuthorization(userResponse, orderRequest)
                .then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
