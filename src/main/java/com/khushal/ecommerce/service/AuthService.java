package com.khushal.ecommerce.service;

import com.khushal.ecommerce.dto.AuthRequest;
import com.khushal.ecommerce.dto.AuthResponse;
import com.khushal.ecommerce.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
