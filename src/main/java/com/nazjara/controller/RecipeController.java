package com.nazjara.controller;

import com.nazjara.command.RecipeCommand;
import com.nazjara.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Base64;

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
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand recipeCommand) {
        webDataBinder.validate();

        if (webDataBinder.getBindingResult().hasErrors()) {
            webDataBinder.getBindingResult().getAllErrors().forEach(error -> log.debug(error.toString()));

            return "recipe/recipeform";
        }

        return "redirect:/recipe/" + recipeService.saveRecipeCommand(recipeCommand).block().getId();
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ModelAndView handleNotFoundException(Exception exception) {
//        log.error(exception.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("status", "404 Not Found");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }
}