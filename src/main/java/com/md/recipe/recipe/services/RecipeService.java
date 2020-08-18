package com.md.recipe.recipe.services;

import com.md.recipe.recipe.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe getRecipeById(Long id);
}
