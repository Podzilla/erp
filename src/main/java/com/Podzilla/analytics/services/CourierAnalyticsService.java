package com.Podzilla.analytics.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.courier.CourierAverageRatingResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierDeliveryCountResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierPerformanceReportResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierSuccessRateResponse;
import com.Podzilla.analytics.api.projections.courier.CourierPerformanceProjection;
import com.Podzilla.analytics.models.Courier;
import com.Podzilla.analytics.repositories.CourierRepository;
import com.Podzilla.analytics.util.MetricCalculator;
import com.Podzilla.analytics.util.StringToUUIDParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourierAnalyticsService {
        private final CourierRepository courierRepository;

        private List<CourierPerformanceProjection> getCourierPerformanceData(
            final LocalDate startDate,
            final LocalDate endDate
        ) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        return courierRepository.findCourierPerformanceBetweenDates(
                startDateTime,
                endDateTime
        );
    }

        public List<CourierDeliveryCountResponse> getCourierDeliveryCounts(
            final LocalDate startDate,
            final LocalDate endDate
        ) {
                return getCourierPerformanceData(startDate, endDate).stream()
                        .map(data -> CourierDeliveryCountResponse.builder()
                                .courierId(data.getCourierId())
                                .courierName(data.getCourierName())
                                .deliveryCount(data.getDeliveryCount())
                                .build())
                        .toList();
        }

        public List<CourierSuccessRateResponse> getCourierSuccessRate(
            final LocalDate startDate,
            final LocalDate endDate
        ) {
                return getCourierPerformanceData(startDate, endDate).stream()
                        .map(data -> CourierSuccessRateResponse.builder()
                                .courierId(data.getCourierId())
                                .courierName(data.getCourierName())
                                .successRate(
                                        MetricCalculator.calculateRate(
                                                data.getCompletedCount(),
                                                data.getDeliveryCount()))
                                .build())
                        .toList();
        }

        public List<CourierAverageRatingResponse> getCourierAverageRating(
            final LocalDate startDate,
            final LocalDate endDate
        ) {
                return getCourierPerformanceData(startDate, endDate).stream()
                        .map(data -> CourierAverageRatingResponse.builder()
                                .courierId(data.getCourierId())
                                .courierName(data.getCourierName())
                                .averageRating(data.getAverageRating())
                                .build())
                        .toList();
        }

        public List<CourierPerformanceReportResponse>
                getCourierPerformanceReport(
            final LocalDate startDate,
            final LocalDate endDate
        ) {
                return getCourierPerformanceData(startDate, endDate).stream()
                        .map(data -> CourierPerformanceReportResponse.builder()
                                .courierId(data.getCourierId())
                                .courierName(data.getCourierName())
                                .deliveryCount(data.getDeliveryCount())
                                .successRate(
                                        MetricCalculator.calculateRate(
                                                data.getCompletedCount(),
                                                data.getDeliveryCount()))
                                .averageRating(data.getAverageRating())
                                .build())
                        .toList();
    }

        public void saveCourier(
                final String courierId,
                final String courierName
        ) {
                UUID id = StringToUUIDParser.parseStringToUUID(courierId);
                Courier courier = Courier.builder()
                        .id(id)
                        .name(courierName)
                        .build();
                courierRepository.save(courier);
        }
}
