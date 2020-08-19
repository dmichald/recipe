package com.md.recipe.recipe.services;

import com.md.recipe.recipe.command.IngredientCommand;
import org.springframework.stereotype.Service;

@Service
public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long recipeId, Long ingredientId);
}
