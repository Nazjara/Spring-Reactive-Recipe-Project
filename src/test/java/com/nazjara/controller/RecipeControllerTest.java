package com.nazjara.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class RecipeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetRecipe() {
        webTestClient
                .get()
                .uri("/recipe/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "text/html")
                .expectBody(String.class);
    }

    @Test
    public void testGetRecipeNotFound() {
        webTestClient
                .get()
                .uri("/recipe/99999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testGetNewRecipeForm() {
        webTestClient
                .get()
                .uri("/recipe/new")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "text/html")
                .expectBody(String.class);
    }

    @Test
    public void testPostNewRecipeForm() {
        webTestClient
                .post()
                .uri("/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .attribute("id", "")
                .attribute("description", "some string")
                .attribute("directions", "some string")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPostNewRecipeFormValidationFailed() {
        webTestClient
                .post()
                .uri("/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .attribute("id", "")
                .attribute("cookTime", "9999")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testGetUpdateView() {
        webTestClient
                .get()
                .uri("/recipe/1/update")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testDeleteAction() {
        webTestClient
                .get()
                .uri("/recipe/2/delete")
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}