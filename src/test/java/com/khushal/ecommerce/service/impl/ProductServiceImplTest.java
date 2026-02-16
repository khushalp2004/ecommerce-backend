package com.khushal.ecommerce.service.impl;

import com.khushal.ecommerce.dto.ProductRequest;
import com.khushal.ecommerce.dto.ProductResponse;
import com.khushal.ecommerce.exception.ResourceNotFoundException;
import com.khushal.ecommerce.model.Category;
import com.khushal.ecommerce.model.Product;
import com.khushal.ecommerce.repository.CategoryRepository;
import com.khushal.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_returnsResponse() {
        Category category = new Category();
        category.setId(5L);
        category.setName("Electronics");

        ProductRequest request = new ProductRequest();
        request.setName("Headphones");
        request.setDescription("Wireless");
        request.setPrice(BigDecimal.valueOf(199.99));
        request.setStockQuantity(10);
        request.setCategoryId(5L);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Headphones");
        saved.setDescription("Wireless");
        saved.setPrice(BigDecimal.valueOf(199.99));
        saved.setStockQuantity(10);
        saved.setCategory(category);

        when(categoryRepository.findById(5L)).thenReturn(Optional.of(category));
        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenReturn(saved);

        ProductResponse response = productService.createProduct(request);

        assertEquals(1L, response.getId());
        assertEquals("Headphones", response.getName());
        assertEquals("Electronics", response.getCategoryName());
    }

    @Test
    void getProductById_throwsWhenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99L));
    }
}
