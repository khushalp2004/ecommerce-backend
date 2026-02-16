package com.khushal.ecommerce.service;

import com.khushal.ecommerce.dto.CartAddRequest;
import com.khushal.ecommerce.dto.CartResponse;

public interface CartService {
    CartResponse addToCart(String email, CartAddRequest request);
    CartResponse getCart(String email);
    CartResponse clearCart(String email);
}
