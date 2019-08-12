package com.nazjara.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImage(String recipeId, MultipartFile image);
}
