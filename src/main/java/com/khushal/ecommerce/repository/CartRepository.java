package com.khushal.ecommerce.repository;

import com.khushal.ecommerce.model.Cart;
import com.khushal.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
