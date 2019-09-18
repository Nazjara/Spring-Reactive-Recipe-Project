package com.nazjara.controller;

import com.nazjara.service.ImageService;
import com.nazjara.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("recipe/{id}/image")
    public String saveImage(@PathVariable String id, @RequestParam("image") MultipartFile image) {
        imageService.saveImage(id, image).subscribe();

        return String.format("redirect:/recipe/%s", id);
    }

//    @GetMapping("recipe/{id}/recipeimage")
//    public Mono<ServerResponse> renderImageFromDB(@PathVariable String id) {
//        return recipeService.findRecipeCommandById(id).flatMap(recipe -> {
//            byte[] byteArray = new byte[recipe.getImage().length];
//            int i = 0;
//
//            for (Byte wrappedByte : recipe.getImage()){
//                byteArray[i++] = wrappedByte; //auto unboxing
//            }
//
//            DataBuffer buffer = new DefaultDataBufferFactory().wrap(byteArray);
//
//            return ServerResponse
//                    .ok()
//                    .contentType(MediaType.IMAGE_JPEG)
//                    .body(BodyInserters.fromDataBuffers(Flux.just(buffer)));
//        });
//    }
}