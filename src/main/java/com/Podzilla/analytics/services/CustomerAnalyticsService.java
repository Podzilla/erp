package com.Podzilla.analytics.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.customer.CustomersTopSpendersResponse;
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.util.DatetimeFormatter;
import com.Podzilla.analytics.util.StringToUUIDParser;
import com.Podzilla.analytics.models.Customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerAnalyticsService {
    private final CustomerRepository customerRepository;

    public List<CustomersTopSpendersResponse> getTopSpenders(
            final LocalDate startDate,
            final LocalDate endDate,
            final int page,
            final int size) {
        log.info("Getting top spenders between {} and {}, page: {},"
                + " size: {}", startDate, endDate, page, size);
        LocalDateTime startDateTime = DatetimeFormatter
                .convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime = DatetimeFormatter
                .convertEndDateToDatetime(endDate);
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CustomersTopSpendersResponse> topSpenders = customerRepository
                .findTopSpenders(startDateTime, endDateTime, pageRequest)
                .stream()
                .map(row -> CustomersTopSpendersResponse.builder()
                        .customerId(row.getCustomerId())
                        .customerName(row.getCustomerName())
                        .totalSpending(row.getTotalSpending())
                        .build())
                .toList();
        log.info("Found {} top spenders", topSpenders.size());
        return topSpenders;
    }

    public void saveCustomer(
            final String customerId,
            final String customerName) {
        UUID id = StringToUUIDParser.parseStringToUUID(customerId);
        Customer customer = Customer.builder()
                .id(id)
                .name(customerName)
                .build();
        log.info("Saving customer: {} with ID: {}",
                customer.getName(), customer.getId());
        customerRepository.save(customer);
    }
}
