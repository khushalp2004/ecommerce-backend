package com.khushal.ecommerce.repository;

import com.khushal.ecommerce.IntegrationTestBase;
import com.khushal.ecommerce.model.Category;
import com.khushal.ecommerce.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ProductRepositoryIT extends IntegrationTestBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    void searchByKeyword_returnsMatches() {
        Category category = new Category();
        category.setName("Audio");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setName("Bluetooth Speaker");
        product.setDescription("Portable audio");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setCategory(category);
        productRepository.save(product);

        List<Product> results = productRepository.searchByKeyword("audio");

        assertFalse(results.isEmpty());
    }
}
