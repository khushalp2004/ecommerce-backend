package com.khushal.ecommerce.service;

import com.khushal.ecommerce.dto.CategoryRequest;
import com.khushal.ecommerce.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    void deleteAllCategories();
}
