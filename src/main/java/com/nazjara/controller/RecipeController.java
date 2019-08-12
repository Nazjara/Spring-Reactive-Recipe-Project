package com.nazjara.controller;

import com.nazjara.command.RecipeCommand;
import com.nazjara.exception.NotFoundException;
import com.nazjara.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Base64;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public String getById(@PathVariable String id, Model model) {
        RecipeCommand recipe = recipeService.findRecipeCommandById(id);

        model.addAttribute("recipe", recipe);

        if (recipe.getImage() != null) {
            model.addAttribute("image", Base64.getEncoder().encodeToString(recipe.getImage()));
        }

        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateById(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(id);

        model.addAttribute("recipe", recipeCommand);

        if (recipeCommand.getImage() != null) {
            model.addAttribute("image", Base64.getEncoder().encodeToString(recipeCommand.getImage()));
        }

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/new")
    public String create(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute("recipe") @Valid RecipeCommand recipeCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));

            return "recipe/recipeform";
        }

        return "redirect:/recipe/" + recipeService.saveRecipeCommand(recipeCommand).getId();
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception exception) {
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("status", "404 Not Found");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}