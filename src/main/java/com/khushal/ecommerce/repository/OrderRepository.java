package com.khushal.ecommerce.repository;

import com.khushal.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select coalesce(sum(o.totalAmount), 0) from Order o")
    BigDecimal sumTotalRevenue();
}
