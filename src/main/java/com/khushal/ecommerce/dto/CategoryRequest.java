package com.khushal.ecommerce.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Category name cannot be empty")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
    private String name;

    @Size(max = 255, message = "Description must be under 255 characters")
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
