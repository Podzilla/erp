package com.Podzilla.analytics.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.Podzilla.analytics.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Component
public class CleanupScheduler {

    private final OrderRepository orderRepository;

    @Autowired
    public CleanupScheduler(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // // Runs at 2:00 AM on the first day of every month
    @Scheduled(cron = "0 0 2 1 * ?")
    @Transactional
    public void monthlyCleanup() {
        System.out.println("Starting monthly cleanup");
        LocalDateTime cutoff = LocalDateTime.now().minusMonths(1);
        // Delete order items first
        orderRepository.deleteOrderItemsOlderThan(cutoff);
        // Then delete orders
        orderRepository.deleteOrdersOlderThan(cutoff);
        System.out.println("Monthly cleanup completed");
    }
}
