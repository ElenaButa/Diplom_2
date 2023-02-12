package data.ingredients;

import java.util.List;

public class Ingredients {
    private boolean success;
    private List<Ingredient> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }

    public Ingredient getIngredientWithName(String name) {
        if (null != getData()) {
            for (Ingredient ingredient : getData()) {
                if (ingredient.getName().equals(name))
                    return ingredient;
            }
        }
        return null;
    }

}
