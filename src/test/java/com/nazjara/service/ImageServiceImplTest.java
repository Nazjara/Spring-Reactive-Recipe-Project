package com.nazjara.service;

import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private Recipe recipe;

    @Mock
    private MultipartFile file;

    private ImageService imageService;

    @Before
    public void setUp() throws Exception {
        imageService = new ImageServiceImpl(recipeRepository);

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
        when(file.getBytes()).thenReturn(new byte[]{1,2,3});
    }

    @Test
    public void saveImageFile() throws Exception {
        imageService.saveImage("1", file);

        verify(recipeRepository).findById("1");
        verify(recipeRepository).save(recipe);
        verify(recipe).setImage(eq(new byte[]{1,2,3}));
    }
}