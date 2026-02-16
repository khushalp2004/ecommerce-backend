package com.khushal.ecommerce.metrics;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Service
public class RequestMetricsService {

    private final long startTimeMillis = System.currentTimeMillis();
    private final LongAdder totalRequests = new LongAdder();
    private final LongAdder errorRequests = new LongAdder();
    private final LongAdder totalResponseTimeMillis = new LongAdder();

    private final AtomicLong dailyCount = new AtomicLong(0);
    private volatile LocalDate dailyDate = LocalDate.now();

    private final Map<String, Long> activeUsers = new ConcurrentHashMap<>();
    private static final long ACTIVE_USER_TTL_MILLIS = Duration.ofMinutes(15).toMillis();

    public void recordRequest(long durationMillis, int status, String username) {
        totalRequests.increment();
        totalResponseTimeMillis.add(durationMillis);
        if (status >= 400) {
            errorRequests.increment();
        }

        LocalDate today = LocalDate.now();
        if (!today.equals(dailyDate)) {
            dailyDate = today;
            dailyCount.set(0);
        }
        dailyCount.incrementAndGet();

        if (username != null && !username.isBlank()) {
            activeUsers.put(username, System.currentTimeMillis());
        }
    }

    public long getUptimeMillis() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    public long getTotalRequests() {
        return totalRequests.sum();
    }

    public long getDailyRequests() {
        return dailyCount.get();
    }

    public double getAverageResponseTimeMillis() {
        long count = totalRequests.sum();
        if (count == 0) {
            return 0.0;
        }
        return (double) totalResponseTimeMillis.sum() / count;
    }

    public double getErrorRate() {
        long count = totalRequests.sum();
        if (count == 0) {
            return 0.0;
        }
        return (double) errorRequests.sum() / count;
    }

    public long getActiveUserCount() {
        long now = System.currentTimeMillis();
        activeUsers.entrySet().removeIf(entry -> now - entry.getValue() > ACTIVE_USER_TTL_MILLIS);
        return activeUsers.size();
    }
}
