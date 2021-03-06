package com.md.recipe.recipe.converters;

import com.md.recipe.recipe.command.CategoryCommand;
import com.md.recipe.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "descript";
    private CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertAll(
                () -> assertEquals(ID_VALUE, categoryCommand.getId()),
                () -> assertEquals(DESCRIPTION, categoryCommand.getDescription())
        );
    }
}