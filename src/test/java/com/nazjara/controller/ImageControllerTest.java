package com.nazjara.controller;

import com.nazjara.service.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageControllerTest {

    @Mock
    ImageService imageService;

    ImageController controller;

    MockMvc mockMvc;

    private MockMultipartFile multipartFile;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
        multipartFile = new MockMultipartFile("image", "testing.txt", "text/plain",
                        "test".getBytes());

        doNothing().when(imageService).saveImage(anyString(), any());
    }

    @Test
    public void testShowUploadForm() throws Exception {
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipeId"));
    }

    @Test
    public void testSaveImage() throws Exception {
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1"));

        verify(imageService).saveImage(eq("1"), eq(multipartFile));
    }
}
