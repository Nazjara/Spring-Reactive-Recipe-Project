package com.nazjara.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class IngredientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testListIngredients() throws Exception {
        webTestClient
                .get()
                .uri("/recipe/1/ingredients")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "text/html")
                .expectBody(String.class);
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        webTestClient
                .get()
                .uri("/recipe/1/ingredient/new")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "text/html")
                .expectBody(String.class);
    }
}