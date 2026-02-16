package com.khushal.ecommerce.dto;

import java.math.BigDecimal;

public class StatsResponse {

    private String uptime;
    private long totalProducts;
    private long totalCategories;
    private long registeredUsers;
    private long totalOrders;
    private BigDecimal revenueGenerated;
    private long apiRequestsToday;
    private double averageResponseTimeMs;
    private double averageDbQueryTimeMs;
    private long memoryUsageMb;
    private long concurrentUsers;
    private double errorRate;

    public StatsResponse(String uptime,
                         long totalProducts,
                         long totalCategories,
                         long registeredUsers,
                         long totalOrders,
                         BigDecimal revenueGenerated,
                         long apiRequestsToday,
                         double averageResponseTimeMs,
                         double averageDbQueryTimeMs,
                         long memoryUsageMb,
                         long concurrentUsers,
                         double errorRate) {
        this.uptime = uptime;
        this.totalProducts = totalProducts;
        this.totalCategories = totalCategories;
        this.registeredUsers = registeredUsers;
        this.totalOrders = totalOrders;
        this.revenueGenerated = revenueGenerated;
        this.apiRequestsToday = apiRequestsToday;
        this.averageResponseTimeMs = averageResponseTimeMs;
        this.averageDbQueryTimeMs = averageDbQueryTimeMs;
        this.memoryUsageMb = memoryUsageMb;
        this.concurrentUsers = concurrentUsers;
        this.errorRate = errorRate;
    }

    public String getUptime() {
        return uptime;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public long getRegisteredUsers() {
        return registeredUsers;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public BigDecimal getRevenueGenerated() {
        return revenueGenerated;
    }

    public long getApiRequestsToday() {
        return apiRequestsToday;
    }

    public double getAverageResponseTimeMs() {
        return averageResponseTimeMs;
    }

    public double getAverageDbQueryTimeMs() {
        return averageDbQueryTimeMs;
    }

    public long getMemoryUsageMb() {
        return memoryUsageMb;
    }

    public long getConcurrentUsers() {
        return concurrentUsers;
    }

    public double getErrorRate() {
        return errorRate;
    }
}
