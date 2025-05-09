package com.Podzilla.analytics.services;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.customer.CustomersTopSpendersResponse;
import com.Podzilla.analytics.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAnalyticsService {
    private final CustomerRepository customerRepository;

    public List<CustomersTopSpendersResponse> getTopSpenders(LocalDateTime startDate, LocalDateTime endDate, int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CustomersTopSpendersResponse> topSpenders = customerRepository.findTopSpenders(startDate, endDate, pageRequest)
                .stream()
                .map(row -> CustomersTopSpendersResponse.builder()
                        .customerId(row.getCustomerId())
                        .customerName(row.getCustomerName())
                        .totalSpending(row.getTotalSpending())
                        .build())
                .toList();
        return topSpenders;
    }
}
