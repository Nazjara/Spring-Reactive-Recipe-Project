package com.nazjara.service;

import com.nazjara.command.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
    Mono<IngredientCommand> findIngredientCommandById(String recipeId, String ingredientId);
    Mono<Void> deleteById(String recipeId, String ingredientId);
}
