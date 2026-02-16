package com.khushal.ecommerce.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khushal.ecommerce.dto.CategoryRequest;
import com.khushal.ecommerce.dto.CategoryResponse;
import com.khushal.ecommerce.service.CategoryService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        logger.info("Creating category: {}", request.getName());
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        logger.info("Fetching category with id: {}", id);
        CategoryResponse response = categoryService.getCategoryById(id);
        logger.info("Category fetched: {}", response.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        logger.info("Fetching all categories");
        List<CategoryResponse> categories = categoryService.getAllCategories();
        logger.info("Fetched {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        logger.info("Updating category with id: {}", id);
        CategoryResponse response = categoryService.updateCategory(id, request);
        logger.info("Category updated: {}", response.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category with id: {}", id);
        categoryService.deleteCategory(id);
        logger.info("Category deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllCategories() {
        logger.warn("Deleting all categories!");
        categoryService.deleteAllCategories();
        logger.info("All categories deleted");
        return ResponseEntity.noContent().build();
    }

}
