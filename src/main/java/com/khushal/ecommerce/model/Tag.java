package com.khushal.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
