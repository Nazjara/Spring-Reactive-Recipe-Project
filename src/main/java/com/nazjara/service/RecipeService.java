package com.nazjara.service;

import com.nazjara.command.RecipeCommand;
import com.nazjara.model.Recipe;

public interface RecipeService {
    Iterable<Recipe> getRecipes();
    Recipe findById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    RecipeCommand findRecipeCommandById(String id);
    void deleteById(String id);
}
