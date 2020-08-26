package com.md.recipe.recipe.services;

import com.md.recipe.recipe.domain.Recipe;
import com.md.recipe.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found recipe with id: " + id));
        try {
            Byte[] imageBytes = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                imageBytes[i++] = b;
            }
            recipe.setImage(imageBytes);
            recipeRepository.save(recipe);

        } catch (IOException e) {
            log.error("Error occur", e);
            e.printStackTrace();
        }
    }
}
