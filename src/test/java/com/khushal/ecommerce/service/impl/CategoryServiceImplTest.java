package com.khushal.ecommerce.service.impl;

import com.khushal.ecommerce.dto.CategoryRequest;
import com.khushal.ecommerce.dto.CategoryResponse;
import com.khushal.ecommerce.exception.ResourceNotFoundException;
import com.khushal.ecommerce.model.Category;
import com.khushal.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory_returnsResponse() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Electronics");
        request.setDescription("Devices");

        Category saved = new Category();
        saved.setId(1L);
        saved.setName("Electronics");
        saved.setDescription("Devices");

        when(categoryRepository.save(org.mockito.ArgumentMatchers.any(Category.class)))
                .thenReturn(saved);

        CategoryResponse response = categoryService.createCategory(request);

        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getName());
        assertEquals("Devices", response.getDescription());
    }

    @Test
    void getCategoryById_throwsWhenMissing() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(99L));
    }
}
