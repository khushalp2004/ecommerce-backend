package com.khushal.ecommerce.service;

import com.khushal.ecommerce.dto.OrderCreateRequest;
import com.khushal.ecommerce.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(String email, OrderCreateRequest request);
    OrderResponse getOrderById(String email, Long orderId);
    OrderResponse updateOrderStatus(Long orderId, String status);
}
