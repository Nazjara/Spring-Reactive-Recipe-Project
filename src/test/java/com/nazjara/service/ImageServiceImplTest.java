package com.nazjara.service;

import com.nazjara.model.Recipe;
import com.nazjara.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import static junit.framework.TestCase.assertSame;
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

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
        when(file.getBytes()).thenReturn(new byte[]{1,2,3});
    }

    @Test
    public void saveImageFile() throws Exception {
        ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImage("1", file).block();

        verify(recipeRepository).findById("1");
        verify(recipeRepository).save(captor.capture());
        verify(recipe).setImage(eq(new byte[]{1,2,3}));

        assertSame(captor.getValue(), recipe);
    }
}