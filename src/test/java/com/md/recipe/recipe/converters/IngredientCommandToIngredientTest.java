package com.md.recipe.recipe.converters;

import com.md.recipe.recipe.command.IngredientCommand;
import com.md.recipe.recipe.command.UnitOfMeasureCommand;
import com.md.recipe.recipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final String DESCRIPTION = "Cheeseburger";
    private static final Long ID_VALUE = 1L;
    private static final Long UOM_ID = 2L;

    private IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertAll(
                () -> assertNotNull(ingredient),
                () -> assertNotNull(ingredient.getUnitOfMeasure()),
                () -> assertEquals(ID_VALUE, ingredient.getId()),
                () -> assertEquals(AMOUNT, ingredient.getAmount()),
                () -> assertEquals(DESCRIPTION, ingredient.getDescription()),
                () -> assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId()));
    }

    @Test
    void convertWithNullUOM() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();


        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertAll(
                () -> assertNotNull(ingredient),
                () -> assertNull(ingredient.getUnitOfMeasure()),
                () -> assertEquals(ID_VALUE, ingredient.getId()),
                () -> assertEquals(AMOUNT, ingredient.getAmount()),
                () -> assertEquals(DESCRIPTION, ingredient.getDescription())
        );
    }
}