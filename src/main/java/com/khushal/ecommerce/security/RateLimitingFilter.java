package com.khushal.ecommerce.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final int capacity;
    private final int refillPerMinute;
    private final Map<String, Window> windows = new ConcurrentHashMap<>();

    public RateLimitingFilter(
            @Value("${security.rate-limit.capacity:100}") int capacity,
            @Value("${security.rate-limit.refill-per-minute:100}") int refillPerMinute) {
        this.capacity = capacity;
        this.refillPerMinute = refillPerMinute;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String key = request.getRemoteAddr();
        Window window = windows.computeIfAbsent(key, k -> new Window());
        if (window.tryConsume(capacity, refillPerMinute)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(429);
        response.getWriter().write("Rate limit exceeded");
    }

    private static final class Window {
        private volatile long windowStartMillis = System.currentTimeMillis();
        private final AtomicInteger count = new AtomicInteger(0);

        boolean tryConsume(int capacity, int refillPerMinute) {
            long now = System.currentTimeMillis();
            if (now - windowStartMillis >= 60_000L) {
                windowStartMillis = now;
                count.set(0);
            }
            int limit = Math.min(capacity, refillPerMinute);
            return count.incrementAndGet() <= limit;
        }
    }
}
