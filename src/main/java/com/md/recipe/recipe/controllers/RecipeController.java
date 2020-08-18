package com.md.recipe.recipe.controllers;

import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/recipe/{id}"})
    public String showById(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/recipeView";
    }

    @DeleteMapping({"recipe/{id}"})
    void deleteRecipe(@PathVariable Long id){

    }

    @PostMapping
     Recipe addRecipe(@RequestParam Recipe recipe){
        return null;
    }

    @PutMapping
    Recipe updateRecipe( @RequestParam Recipe recipe){
        return null;
    }
}
