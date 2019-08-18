package com.nazjara.service;

import com.nazjara.command.RecipeCommand;
import com.nazjara.converter.RecipeCommandToRecipe;
import com.nazjara.converter.RecipeToRecipeCommand;
import com.nazjara.exception.NotFoundException;
import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Iterable<Recipe> getRecipes() {
        log.debug("getRecipes() called");

        return recipeRepository.findAll().toIterable();
    }

    @Override
    public Recipe findById(String id) {
        return recipeRepository.findById(id).blockOptional().orElseThrow(() ->
                new NotFoundException(String.format("Recipe not found for id: %s", id)));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(recipe).block();
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(String id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
