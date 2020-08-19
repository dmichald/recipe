package com.md.recipe.recipe.controllers;

import com.md.recipe.recipe.command.IngredientCommand;
import com.md.recipe.recipe.command.UnitOfMeasureCommand;
import com.md.recipe.recipe.services.IngredientService;
import com.md.recipe.recipe.services.RecipeService;
import com.md.recipe.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    String listOfIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredients list for recipe id: " + recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }
    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id));
        model.addAttribute("ingredient",ingredient);
        model.addAttribute("uomList", unitOfMeasureService.getAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand saved = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("Saved recipe id: " + ingredientCommand.getRecipeId());
        log.debug("Saved ingredient id: " + ingredientCommand.getId());
        return "redirect:/recipe/" + saved.getRecipeId() + "/ingredient/" + saved.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    String createNewRecipe(@PathVariable String recipeId, Model model) {
        recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList", unitOfMeasureService.getAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    String deleteRecipe(@PathVariable String recipeId, @PathVariable String id){
        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(id));
        log.debug(String.format("Delete ingredient with id: %s from recipe with id: $s", recipeId,id));
        return "redirect:/recipe/" + recipeId  + "/ingredients";
    }


}
