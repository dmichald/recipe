package com.md.recipe.recipe.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    void saveImageFile(Long anyLong, MultipartFile file);
}
