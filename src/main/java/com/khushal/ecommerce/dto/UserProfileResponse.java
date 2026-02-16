package com.khushal.ecommerce.dto;

import java.util.Set;

public class UserProfileResponse {

    private Long id;
    private String email;
    private String fullName;
    private Set<String> roles;

    public UserProfileResponse(Long id, String email, String fullName, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
