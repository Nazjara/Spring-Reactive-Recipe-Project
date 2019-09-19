package com.nazjara.service;

import com.nazjara.exception.NotFoundException;
import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Recipe> saveImage(String recipeId, byte[] image) {
        return recipeRepository.findById(recipeId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Recipe with id = %s not found", recipeId))))
                .flatMap(recipe -> {
                    try {
                        recipe.setImage(image);
                        return recipeRepository.save(recipe);
                    } catch (Exception e) {
                        log.error("Unable to save image", e);
                        throw new RuntimeException();
                    }
                });
    }
}