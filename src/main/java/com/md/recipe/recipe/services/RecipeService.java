package com.md.recipe.recipe.services;

import com.md.recipe.recipe.command.RecipeCommand;
import com.md.recipe.recipe.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe getRecipeById(Long id);

    RecipeCommand createRecipe(RecipeCommand recipe);

    RecipeCommand updateRecipe(RecipeCommand recipe);

    RecipeCommand findCommandById(Long id);

    void deleteRecipe(Long id);
}
