package com.nazjara.controller;

import com.nazjara.command.RecipeCommand;
import com.nazjara.exception.NotFoundException;
import com.nazjara.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private RecipeCommand recipeCommand;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
       mockMvc = MockMvcBuilders
               .standaloneSetup(recipeController)
               .setControllerAdvice(new ControllerExceptionHandler()).build();

       when(recipeService.findRecipeCommandById(anyString())).thenReturn(recipeCommand);
       when(recipeCommand.getImage()).thenReturn(getClass().getClassLoader().getResourceAsStream("GrilledChickenTacos.jpg").readAllBytes());
       when(recipeCommand.getId()).thenReturn("2");
       when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
       when(recipeService.findRecipeCommandById(anyString())).thenReturn(recipeCommand);
    }

    @Test
    public void testGetRecipe() throws Exception {
       mockMvc.perform(get("/recipe/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/show"))
               .andExpect(model().attributeExists("recipe"))
               .andExpect(model().attributeExists("image"));

       verify(recipeService).findRecipeCommandById("1");
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        when(recipeService.findRecipeCommandById("1")).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));

        verify(recipeService).findRecipeCommandById("1");
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2"));

        verify(recipeService).saveRecipeCommand(any(RecipeCommand.class));
    }

    @Test
    public void testPostNewRecipeFormValidationFailed() throws Exception {
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "9999")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));

        verify(recipeService, never()).saveRecipeCommand(any(RecipeCommand.class));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findRecipeCommandById("1");
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService).deleteById(eq("1"));
    }
}