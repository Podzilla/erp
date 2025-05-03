package com.Podzilla.analytics.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.order.OrderFailureDTO;
import com.Podzilla.analytics.api.dtos.order.OrderFailureReasonsDTO;
import com.Podzilla.analytics.api.dtos.order.OrderRegionDTO;
import com.Podzilla.analytics.api.dtos.order.OrderStatusDTO;
import com.Podzilla.analytics.api.projections.order.OrderFailureRateProjection;
import com.Podzilla.analytics.api.projections.order.OrderFailureReasonsProjection;
import com.Podzilla.analytics.api.projections.order.OrderRegionProjection;
import com.Podzilla.analytics.api.projections.order.OrderStatusProjection;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Order.OrderStatus;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.util.DatetimeFormatter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderAnalyticsService {


    private final OrderRepository orderRepository;
    
    public List<OrderRegionDTO> getOrdersByRegion(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = DatetimeFormatter.convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime = DatetimeFormatter.convertEndDateToDatetime(endDate);
        List<OrderRegionProjection> ordersByRegion = orderRepository.findOrdersByRegion(startDateTime, endDateTime);
        return ordersByRegion.stream()
            .map(data -> OrderRegionDTO.builder()
                    .regionId(data.getRegionId())
                    .city(data.getCity())
                    .country(data.getCountry())
                    .orderCount(data.getOrderCount())
                    .averageOrderValue(data.getAverageOrderValue())
                    .build())
            .toList();
    }

    public List<OrderStatusDTO> getOrdersStatusCounts(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = DatetimeFormatter.convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime = DatetimeFormatter.convertEndDateToDatetime(endDate);
        List<OrderStatusProjection> orderStatusCounts = orderRepository.findOrderStatusCounts(startDateTime, endDateTime);
        return orderStatusCounts.stream()
            .map(data -> OrderStatusDTO.builder()
                    .status(data.getStatus())
                    .count(data.getCount())
                    .build())
            .toList();
    }

    public OrderFailureDTO getOrdersFailures(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = DatetimeFormatter.convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime = DatetimeFormatter.convertEndDateToDatetime(endDate);
        List<OrderFailureReasonsProjection> failureReasons = orderRepository.findFailureReasons(startDateTime, endDateTime);
        OrderFailureRateProjection failureRate = orderRepository.calculateFailureRate(startDateTime, endDateTime);
        List<OrderFailureReasonsDTO> failureReasonsDTO = failureReasons.stream()
            .map(data -> OrderFailureReasonsDTO.builder()
                    .reason(data.getReason())
                    .count(data.getCount())
                    .build())
            .toList();
        return OrderFailureDTO.builder()
            .reasons(failureReasonsDTO)
            .failureRate(failureRate.getFailureRate())
            .build();
    }
}