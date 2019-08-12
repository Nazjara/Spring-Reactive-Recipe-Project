package com.nazjara.controller;

import com.nazjara.command.IngredientCommand;
import com.nazjara.command.UnitOfMeasureCommand;
import com.nazjara.service.IngredientService;
import com.nazjara.service.RecipeService;
import com.nazjara.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findRecipeCommandById(recipeId));

        return "ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(recipeId, id));
        return "ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String create(@PathVariable String recipeId, Model model){
        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUnitOfMeasureCommand(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  unitOfMeasureService.findAll());
        model.addAttribute("recipeId",  recipeId);

        return "ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(recipeId, id));

        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command, @PathVariable String recipeId){
        command.setRecipeId(recipeId);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + recipeId + "/ingredient/" + savedCommand.getId();
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id){

        log.debug("deleting ingredient id:" + id);
        ingredientService.deleteById(recipeId, id);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}