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

public class GetOrderTest extends BaseTest {

    @Test
    public void getOrderListWithAuthorizationTest() {
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

        response = OrderRequests.getOrdersWithAuthorization(userResponse);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));

    }

    @Test
    public void getOrderListWithoutAuthorizationTest() {
        Response response = OrderRequests.getOrdersWithoutAuthorization();
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
