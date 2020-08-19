package com.md.recipe.recipe.services;

import com.md.recipe.recipe.command.UnitOfMeasureCommand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> getAllUoms();
}
