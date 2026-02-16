package com.khushal.ecommerce.service.impl;

import com.khushal.ecommerce.dto.AuthRequest;
import com.khushal.ecommerce.dto.AuthResponse;
import com.khushal.ecommerce.dto.RegisterRequest;
import com.khushal.ecommerce.exception.BadRequestException;
import com.khushal.ecommerce.model.Role;
import com.khushal.ecommerce.model.User;
import com.khushal.ecommerce.repository.UserRepository;
import com.khushal.ecommerce.security.JwtService;
import com.khushal.ecommerce.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getRoles());
        return new AuthResponse(token, "Bearer");
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtService.generateToken(authentication.getName(), jwtService.extractRoles(authentication));
        return new AuthResponse(token, "Bearer");
    }
}
