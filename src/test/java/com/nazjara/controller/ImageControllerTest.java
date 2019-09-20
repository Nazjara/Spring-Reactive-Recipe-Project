package com.nazjara.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ImageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testShowUploadForm() throws Exception {
        webTestClient
                .get()
                .uri("/recipe/1/image")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "text/html")
                .expectBody(String.class);
    }

    @Test
    @Ignore("is not working")
    public void testSaveImage() throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", getClass().getResource("../../../GrilledChickenTacos.jpg"));

        webTestClient.post()
                .uri("/recipe/1/image")
                .header("Content-disposition", "image")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}