package com.md.recipe.recipe.converters;

import com.md.recipe.recipe.command.UnitOfMeasureCommand;
import com.md.recipe.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {
    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void shouldConvert() {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(1L);
        command.setDescription("description");

        //when
        UnitOfMeasure uom = converter.convert(command);

        //then
        assertAll(
                () -> assertNotNull(uom),
                () -> assertEquals(1L, uom.getId()),
                () -> assertEquals("description", uom.getDescription())
        );
    }
}