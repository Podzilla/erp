package com.Podzilla.analytics.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.order.OrderFailureResponse;
import com.Podzilla.analytics.api.dtos.order.OrderFailureReasonsResponse;
import com.Podzilla.analytics.api.dtos.order.OrderRegionResponse;
import com.Podzilla.analytics.api.dtos.order.OrderStatusResponse;
import com.Podzilla.analytics.api.projections.order.OrderFailureRateProjection;
import com.Podzilla.analytics.api.projections.order.OrderFailureReasonsProjection;
import com.Podzilla.analytics.api.projections.order.OrderRegionProjection;
import com.Podzilla.analytics.api.projections.order.OrderStatusProjection;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.util.DatetimeFormatter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderAnalyticsService {


    private final OrderRepository orderRepository;
    public List<OrderRegionResponse> getOrdersByRegion(
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        LocalDateTime startDateTime =
            DatetimeFormatter.convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime =
            DatetimeFormatter.convertEndDateToDatetime(endDate);
        List<OrderRegionProjection> ordersByRegion =
            orderRepository.findOrdersByRegion(startDateTime, endDateTime);
        return ordersByRegion.stream()
            .map(data -> OrderRegionResponse.builder()
                    .regionId(data.getRegionId())
                    .city(data.getCity())
                    .country(data.getCountry())
                    .orderCount(data.getOrderCount())
                    .averageOrderValue(data.getAverageOrderValue())
                    .build())
            .toList();
    }

    public List<OrderStatusResponse> getOrdersStatusCounts(
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        LocalDateTime startDateTime =
            DatetimeFormatter.convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime =
            DatetimeFormatter.convertEndDateToDatetime(endDate);
        List<OrderStatusProjection> orderStatusCounts =
            orderRepository.findOrderStatusCounts(startDateTime, endDateTime);
        return orderStatusCounts.stream()
            .map(data -> OrderStatusResponse.builder()
                    .status(data.getStatus())
                    .count(data.getCount())
                    .build())
            .toList();
    }

    public OrderFailureResponse getOrdersFailures(
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        LocalDateTime startDateTime =
            DatetimeFormatter.convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime =
            DatetimeFormatter.convertEndDateToDatetime(endDate);
        List<OrderFailureReasonsProjection> failureReasons =
            orderRepository.findFailureReasons(startDateTime, endDateTime);
        OrderFailureRateProjection failureRate =
            orderRepository.calculateFailureRate(startDateTime, endDateTime);
        List<OrderFailureReasonsResponse>
            failureReasonsDTO = failureReasons.stream()
            .map(data -> OrderFailureReasonsResponse.builder()
                    .reason(data.getReason())
                    .count(data.getCount())
                    .build())
            .toList();
        return OrderFailureResponse.builder()
            .reasons(failureReasonsDTO)
            .failureRate(failureRate.getFailureRate())
            .build();
    }
}
