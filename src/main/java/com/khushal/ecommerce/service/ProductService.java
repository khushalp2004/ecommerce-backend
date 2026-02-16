package com.khushal.ecommerce.service;

import com.khushal.ecommerce.dto.ProductRequest;
import com.khushal.ecommerce.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    Page<ProductResponse> getAllProducts(int page, int size, String sortBy);

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteAllProducts();

    void deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);
}
