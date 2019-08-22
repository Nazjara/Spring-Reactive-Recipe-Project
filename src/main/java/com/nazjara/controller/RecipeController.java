package com.nazjara.controller;

import com.nazjara.command.RecipeCommand;
import com.nazjara.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private WebDataBinder webDataBinder;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{id}")
    public String getById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(id));

        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(id));

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/new")
    public String create(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public Mono<String> saveOrUpdate(@ModelAttribute("recipe") RecipeCommand recipeCommand) {
        webDataBinder.validate();

        if (webDataBinder.getBindingResult().hasErrors()) {
            webDataBinder.getBindingResult().getAllErrors().forEach(error -> log.debug(error.toString()));

            return Mono.just("recipe/recipeform");
        }

        return recipeService.saveRecipeCommand(recipeCommand).map(recipeCommand1 -> "redirect:/recipe/" + recipeCommand1.getId());
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(id).subscribe();

        return "redirect:/";
    }
}