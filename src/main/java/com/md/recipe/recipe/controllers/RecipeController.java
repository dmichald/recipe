package com.md.recipe.recipe.controllers;

import com.md.recipe.recipe.command.RecipeCommand;
import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/recipe/{id}"})
    public String showById(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/recipeView";
    }

    @GetMapping("recipe/new")
    String createRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand newRecipe = recipeService.createRecipe(recipe);
        return "redirect:/recipe/" + newRecipe.getId();
    }

    @GetMapping("recipe/{id}/update")
    String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/delete")
    String deleteRecipe(@PathVariable String id, Model model) {
        recipeService.deleteRecipe(Long.valueOf(id));
        log.debug("Delete recipe with id: " + id);

        return "redirect:/";
    }
}
