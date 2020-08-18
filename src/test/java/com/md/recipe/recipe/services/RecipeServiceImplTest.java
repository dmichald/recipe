package com.md.recipe.recipe.services;

import com.md.recipe.recipe.converters.RecipeCommandToRecipe;
import com.md.recipe.recipe.converters.RecipeToRecipeCommand;
import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("RecipeServiceImpl should")
class RecipeServiceImplTest {
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    @DisplayName("return all recipes")
    void getRecipes(){

        Recipe recipe = new Recipe();
        HashSet<Recipe> set = new HashSet<>();
        set.add(recipe);
        when(recipeRepository.findAll()).thenReturn(set);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1,recipes.size());
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("return recipe by given id")
    void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        assertNotNull(recipeService.getRecipeById(1L));
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("should throw exception when not found recipe")
    void getRecipeShouldThrowException() {
        Throwable thrown = assertThrows(
                Throwable.class,
                () -> recipeService.getRecipeById(anyLong())
        );
        assertTrue(thrown.getMessage().contains("Recipe not found"));
    }

    @Test
    @DisplayName("delete recipe by given id")
    void deleteRecipe() {
        recipeService.deleteRecipe(1L);
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}