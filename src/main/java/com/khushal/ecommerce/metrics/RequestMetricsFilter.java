package com.khushal.ecommerce.metrics;

import com.khushal.ecommerce.security.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestMetricsFilter extends OncePerRequestFilter {

    private final RequestMetricsService metricsService;
    private final JwtService jwtService;

    public RequestMetricsFilter(RequestMetricsService metricsService, JwtService jwtService) {
        this.metricsService = metricsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            String username = extractUsername(request);
            metricsService.recordRequest(duration, response.getStatus(), username);
        }
    }

    private String extractUsername(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                return jwtService.extractUsername(authHeader.substring(7));
            } catch (Exception ignored) {
                return null;
            }
        }
        return null;
    }
}
