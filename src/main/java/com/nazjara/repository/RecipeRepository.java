package com.nazjara.repository;

import com.nazjara.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeRepository extends ReactiveMongoRepository<Recipe, String> {
}