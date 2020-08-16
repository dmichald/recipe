package com.md.recipe.recipe.services;

import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes(){

        Recipe recipe = new Recipe();
        HashSet<Recipe> set = new HashSet<>();
        set.add(recipe);
        when(recipeRepository.findAll()).thenReturn(set);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1,recipes.size());
        verify(recipeRepository,times(1)).findAll();
    }

}