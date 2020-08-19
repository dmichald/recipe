package com.md.recipe.recipe.services;

import com.md.recipe.recipe.command.IngredientCommand;
import com.md.recipe.recipe.converters.IngredientCommandToIngredient;
import com.md.recipe.recipe.converters.IngredientToIngredientCommand;
import com.md.recipe.recipe.domain.Ingredient;
import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.domain.UnitOfMeasure;
import com.md.recipe.recipe.repositories.RecipeRepository;
import com.md.recipe.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found. ID: " + recipeId));


        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        return ingredientCommandOptional.orElseThrow(() -> new RuntimeException("Ingredient with given id not found. ID: " + ingredientId));
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Recipe recipe = recipeRepository.findById(ingredientCommand.getRecipeId()).orElseThrow(() -> new RuntimeException("No recipe found"));
        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();
        if(ingredientOptional.isPresent()){
            Ingredient ingredient = ingredientOptional.get();
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setDescription(ingredientCommand.getDescription());
            UnitOfMeasure u = unitOfMeasureRepository
                    .findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"));
            ingredient.setUnitOfMeasure(u);
        }else {
            System.err.println(ingredientToIngredientCommand);
            recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        }
        Recipe savedRecipe =recipeRepository.save(recipe);


        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not found")));
    }
}
