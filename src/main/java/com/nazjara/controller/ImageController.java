package com.nazjara.controller;

import com.nazjara.service.ImageService;
import com.nazjara.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    @Autowired
    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipeId", id);

        return "recipe/imageform";
    }

    @PostMapping(path = "recipe/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> saveImage(@PathVariable String id, @RequestPart("image") Mono<FilePart> file) {
        return file.flatMap(it -> {
            it.transferTo(Paths.get("/tmp/" + it.filename()));
            try {
                return imageService.saveImage(id, IOUtils.toByteArray(Files.newInputStream(Paths.get("/tmp/" + it.filename()))));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).then(Mono.just(String.format("redirect:/recipe/%s", id)));
    }

    public Mono<ServerResponse> renderImageFromDB(ServerRequest serverRequest) {
        return recipeService.findRecipeCommandById(serverRequest.pathVariable("id")).flatMap(recipe -> {
            DataBuffer buffer = new DefaultDataBufferFactory().wrap(recipe.getImage());

            return ServerResponse
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(BodyInserters.fromDataBuffers(Flux.just(buffer)));
        });
    }
}