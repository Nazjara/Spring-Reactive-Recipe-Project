package com.nazjara.service;

import com.nazjara.model.Recipe;
import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<Recipe> saveImage(String recipeId, byte[] image);
}