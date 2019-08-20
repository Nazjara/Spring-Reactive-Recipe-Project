package com.nazjara.service;

import com.nazjara.command.RecipeCommand;
import com.nazjara.converter.RecipeCommandToRecipe;
import com.nazjara.converter.RecipeToRecipeCommand;
import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private Recipe recipe;

    @Mock
    private RecipeCommand recipeCommand;

    @Before
    public void setUp() {
        when(recipeRepository.findAll()).thenReturn(Flux.just(recipe));
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(recipe)).thenReturn(Mono.just(recipe));
        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
        when(recipeCommandToRecipe.convert(recipeCommand)).thenReturn(recipe);
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);
    }

    @Test
    public void testGetRecipes() {
        Recipe recipe = recipeService.getRecipes().blockFirst();

        assertEquals(recipe, recipe);

        verify(recipeRepository).findAll();
    }

    @Test
    public void testGetRecipeById() {
        Recipe newRecipe = recipeService.findById("1").block();

        assertSame(newRecipe, recipe);

        verify(recipeRepository).findById("1");
    }

    @Test
    public void testSaveRecipeCommand() {
        assertSame(recipeCommand, recipeService.saveRecipeCommand(recipeCommand).block());

        verify(recipeCommandToRecipe).convert(recipeCommand);
        verify(recipeRepository).save(recipe);
        verify(recipeToRecipeCommand).convert(recipe);
    }

    @Test
    public void getRecipeCommandByIdTest() {
        RecipeCommand commandById = recipeService.findRecipeCommandById("1").block();

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    public void testDeleteById() throws Exception {
        recipeService.deleteById("2");

        verify(recipeRepository).deleteById(anyString());
    }
}