package com.md.recipe.recipe.controllers;

import com.md.recipe.recipe.command.RecipeCommand;
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

    @GetMapping
    @RequestMapping("recipe/new")
    String createRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    String saveOrUpdate(@ModelAttribute RecipeCommand recipe) {
        RecipeCommand newRecipe = recipeService.createRecipe(recipe);
        return "redirect:/recipe/" + newRecipe.getId();
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    String deleteRecipe(@PathVariable String id, Model model) {
        recipeService.deleteRecipe(Long.valueOf(id));
        log.debug("Delete recipe with id: " + id);

        return "redirect:/";
    }
}
