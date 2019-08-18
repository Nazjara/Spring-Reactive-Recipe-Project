package com.nazjara.service;

import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImage(String recipeId, MultipartFile image) {
        Recipe recipe = recipeRepository.findById(recipeId).block();

        try {
            recipe.setImage(image.getBytes());
        } catch (Exception e) {
            log.error("Unable to save image", e);
        }

        recipeRepository.save(recipe).block();
    }
}
