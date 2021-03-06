package com.nazjara.configuration;

import com.nazjara.controller.ImageController;
import com.nazjara.model.Recipe;
import com.nazjara.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(RecipeService recipeService, ImageController imageController) {
        return RouterFunctions.route(GET("/api/recipes"), serverRequest ->
            ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(recipeService.getRecipes(), Recipe.class))
                .andRoute(GET("/recipe/{id}/recipeimage"), imageController::renderImageFromDB);
    }
}
