package com.Podzilla.analytics.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.CourierAverageRatingDTO;
import com.Podzilla.analytics.api.dtos.CourierDeliveryCountDTO;
import com.Podzilla.analytics.api.dtos.CourierPerformanceReportDTO;
import com.Podzilla.analytics.api.dtos.CourierSuccessRateDTO;
import com.Podzilla.analytics.api.projections.CourierPerformanceProjection;
import com.Podzilla.analytics.repositories.CourierRepository;
import com.Podzilla.analytics.util.MetricCalculator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourierAnalyticsService {
    private final CourierRepository courierRepository;

    private List<CourierPerformanceProjection> getCourierPerformanceData(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        return courierRepository.findCourierPerformanceBetweenDates(startDateTime, endDateTime);
    }

    public List<CourierDeliveryCountDTO> getCourierDeliveryCounts(LocalDate startDate, LocalDate endDate) {
        List<CourierPerformanceProjection> performanceData = getCourierPerformanceData(startDate, endDate);
        return performanceData.stream()
                .map(data -> CourierDeliveryCountDTO.builder()
                        .courierId(data.getCourierId())
                        .courierName(data.getCourierName())
                        .deliveryCount(data.getDeliveryCount())
                        .build())
                .toList();
    }

    public List<CourierSuccessRateDTO> getCourierSuccessRate(LocalDate startDate, LocalDate endDate) {
        List<CourierPerformanceProjection> performanceData = getCourierPerformanceData(startDate, endDate);
        return performanceData.stream()
                .map(data -> CourierSuccessRateDTO.builder()
                        .courierId(data.getCourierId())
                        .courierName(data.getCourierName())
                        .successRate(
                                MetricCalculator.calculatePercentage(data.getCompletedCount(), data.getDeliveryCount()))
                        .build())
                .toList();
    }

    public List<CourierAverageRatingDTO> getCourierAverageRating(LocalDate startDate, LocalDate endDate) {
        List<CourierPerformanceProjection> performanceData = getCourierPerformanceData(startDate, endDate);
        return performanceData.stream()
                .map(data -> CourierAverageRatingDTO.builder()
                        .courierId(data.getCourierId())
                        .courierName(data.getCourierName())
                        .averageRating(data.getAverageRating())
                        .build())
                .toList();
    }

    public List<CourierPerformanceReportDTO> getCourierPerformanceReport(LocalDate startDate, LocalDate endDate) {
        List<CourierPerformanceProjection> performanceData = getCourierPerformanceData(startDate, endDate);
        return performanceData.stream()
                .map(data -> CourierPerformanceReportDTO.builder()
                        .courierId(data.getCourierId())
                        .courierName(data.getCourierName())
                        .deliveryCount(data.getDeliveryCount())
                        .successRate(
                                MetricCalculator.calculatePercentage(data.getCompletedCount(), data.getDeliveryCount()))
                        .averageRating(data.getAverageRating())
                        .build())
                .toList();
    }
}