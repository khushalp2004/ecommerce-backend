package com.khushal.ecommerce.repository;

import com.khushal.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            select p from Product p
            where lower(p.name) like lower(concat('%', :keyword, '%'))
               or lower(p.description) like lower(concat('%', :keyword, '%'))
            """)
    List<Product> searchByKeyword(String keyword);
}
