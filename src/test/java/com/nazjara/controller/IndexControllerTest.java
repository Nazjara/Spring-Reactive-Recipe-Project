package com.nazjara.controller;

import com.nazjara.model.Recipe;
import com.nazjara.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

    @InjectMocks
    public IndexController indexController;

    @Mock
    public RecipeService recipeService;

    @Mock
    public Model model;

    @Before
    public void setUp() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));
        when(model.addAttribute(anyString(), any())).thenReturn(model);
    }

    @Test
    public void testGetIndexPage() {
        String view = indexController.getIndexPage(model);

        ArgumentCaptor<List<Recipe>> captor = ArgumentCaptor.forClass(List.class);

        verify(recipeService).getRecipes();
        verify(model).addAttribute(eq("recipes"), captor.capture());

        assertEquals("index", view);
        assertEquals(2, captor.getValue().size());
    }
}