package com.khushal.ecommerce.controller;

import com.khushal.ecommerce.dto.StatsResponse;
import com.khushal.ecommerce.metrics.RequestMetricsService;
import com.khushal.ecommerce.repository.CategoryRepository;
import com.khushal.ecommerce.repository.OrderRepository;
import com.khushal.ecommerce.repository.ProductRepository;
import com.khushal.ecommerce.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.Duration;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RequestMetricsService metricsService;
    private final EntityManagerFactory entityManagerFactory;

    public StatsController(ProductRepository productRepository,
                           CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           OrderRepository orderRepository,
                           RequestMetricsService metricsService,
                           EntityManagerFactory entityManagerFactory) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.metricsService = metricsService;
        this.entityManagerFactory = entityManagerFactory;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StatsResponse getStats() {
        long totalProducts = productRepository.count();
        long totalCategories = categoryRepository.count();
        long registeredUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        BigDecimal revenue = orderRepository.sumTotalRevenue();

        long requestsToday = metricsService.getDailyRequests();
        double avgResponseTime = metricsService.getAverageResponseTimeMillis();
        double errorRate = metricsService.getErrorRate();
        long concurrentUsers = metricsService.getActiveUserCount();

        double avgDbQueryTime = getAverageDbQueryTimeMs();
        long memoryUsageMb = getMemoryUsageMb();

        String uptime = formatUptime(metricsService.getUptimeMillis());

        return new StatsResponse(
                uptime,
                totalProducts,
                totalCategories,
                registeredUsers,
                totalOrders,
                revenue,
                requestsToday,
                avgResponseTime,
                avgDbQueryTime,
                memoryUsageMb,
                concurrentUsers,
                errorRate
        );
    }

    private double getAverageDbQueryTimeMs() {
        try {
            SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
            Statistics statistics = sessionFactory.getStatistics();
            long count = statistics.getQueryExecutionCount();
            if (count == 0) {
                return 0.0;
            }
            return (double) statistics.getQueryExecutionMaxTime() / count;
        } catch (Exception ex) {
            return 0.0;
        }
    }

    private long getMemoryUsageMb() {
        Runtime runtime = Runtime.getRuntime();
        long usedBytes = runtime.totalMemory() - runtime.freeMemory();
        return usedBytes / (1024 * 1024);
    }

    private String formatUptime(long uptimeMillis) {
        Duration duration = Duration.ofMillis(uptimeMillis);
        long days = duration.toDays();
        long hours = duration.minusDays(days).toHours();
        long minutes = duration.minusDays(days).minusHours(hours).toMinutes();
        return days + " days, " + hours + " hours, " + minutes + " minutes";
    }
}
