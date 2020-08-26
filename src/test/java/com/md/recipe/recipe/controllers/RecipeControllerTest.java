package com.md.recipe.recipe.controllers;

import com.md.recipe.recipe.command.RecipeCommand;
import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.exceptions.ControllerAdvisor;
import com.md.recipe.recipe.exceptions.NotFoundException;
import com.md.recipe.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {


    private RecipeService recipeService;
    private RecipeController recipeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        recipeService = mock(RecipeService.class);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }


    @Test
    void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeView"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.createRecipe(any())).thenReturn(recipeCommand);
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id","")
        .param("description", "this is description")
        .param("directions", "some directions")
        .param("url","https://www.example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService,times(1)).deleteRecipe(anyLong());
    }

    @Test
    void shouldThrowExceptionNotFound() throws Exception {
        when(recipeService.getRecipeById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/34"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("errors/404error"));

    }

    @Test
    void testNewRecipeFormValidation() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.createRecipe(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id",""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));

    }
}