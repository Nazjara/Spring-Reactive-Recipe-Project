package com.nazjara.service;

import com.nazjara.command.RecipeCommand;
import com.nazjara.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getRecipes();
    Mono<Recipe> findById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);
    Mono<RecipeCommand> findRecipeCommandById(String id);
    Mono<Void> deleteById(String id);
}
