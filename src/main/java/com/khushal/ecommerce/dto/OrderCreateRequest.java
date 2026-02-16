package com.khushal.ecommerce.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.util.List;

public class OrderCreateRequest {

    @NotEmpty
    private List<OrderItemRequest> items;

    @NotBlank
    private String shippingAddress;

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
