package com.nazjara.service;

import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImage(String recipeId, MultipartFile image) {
        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    try {
                        recipe.setImage(image.getBytes());
                        return recipe;
                    } catch (Exception e) {
                        log.error("Unable to save image", e);
                        throw new RuntimeException();
                    }
                });

        recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}