package com.nazjara.service;

import com.nazjara.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    IngredientCommand findIngredientCommandById(String recipeId, String ingredientId);
    void deleteById(String recipeId, String ingredientId);
}
