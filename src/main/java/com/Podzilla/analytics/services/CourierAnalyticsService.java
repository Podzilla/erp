package com.Podzilla.analytics.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.CourierAverageRatingDTO;
import com.Podzilla.analytics.api.DTOs.CourierDeliveryCountDTO;
import com.Podzilla.analytics.api.DTOs.CourierSuccessRateDTO;
import com.Podzilla.analytics.repositories.CourierRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourierAnalyticsService {
    private final CourierRepository courierRepository;

    public List<CourierDeliveryCountDTO> getCourierDeliveryCounts(LocalDate startDate, LocalDate endDate) {
        return courierRepository.getCourierDeliveryCounts(startDate, endDate);
    }

    public List<CourierSuccessRateDTO> getCourierSuccessRate(LocalDate startDate, LocalDate endDate) {
        return courierRepository.getCourierSuccessRate(startDate, endDate);
    }

    public List<CourierAverageRatingDTO> getCourierAverageRating(LocalDate startDate, LocalDate endDate) {
        return courierRepository.getCourierAverageRating(startDate, endDate);
    }
}