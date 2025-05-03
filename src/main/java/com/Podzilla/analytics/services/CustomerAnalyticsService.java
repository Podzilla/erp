package com.Podzilla.analytics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.repositories.CustomerRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CustomerAnalyticsService {
    private final CustomerRepository customerRepository;

    public CustomerAnalyticsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Map<String, Object>> getTopSpenders(LocalDateTime startDate, LocalDateTime endDate, int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return customerRepository.findTopSpenders(startDate, endDate, pageRequest)
                .map(row -> Map.of(
                        "customerId", row[0],
                        "customerName", row[1],
                        "totalSpending", row[2]));
    }
}