package com.Podzilla.analytics.services;

import java.math.BigDecimal;
import java.time.Instant;
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
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.util.DatetimeFormatter;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Order.OrderStatus;
import com.Podzilla.analytics.models.Region;
import com.Podzilla.analytics.models.Customer;
import com.Podzilla.analytics.util.StringToUUIDParser;


import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderAnalyticsService {


    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;

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

    public Order saveOrder(
        final String orderId,
        final String customerId,
        final List<com.podzilla.mq.events.OrderItem> items,
        final Region region,
        final BigDecimal totalAmount,
        final Instant timeStamp
    ) {
        UUID orderUUID =
            StringToUUIDParser.parseStringToUUID(orderId);
        UUID customerUUID =
            StringToUUIDParser.parseStringToUUID(customerId);
        Customer customer =
            customerRepository.findById(customerUUID)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        int numberOfItems = items.stream()
            .mapToInt(com.podzilla.mq.events.OrderItem::getQuantity)
            .sum();
        LocalDateTime orderPlacedTimestamp =
            DatetimeFormatter.convertIntsantToDateTime(timeStamp);
        Order order = Order.builder()
            .id(orderUUID)
            .totalAmount(totalAmount)
            .orderPlacedTimestamp(orderPlacedTimestamp)
            .finalStatusTimestamp(orderPlacedTimestamp)
            .region(region)
            .customer(customer)
            .numberOfItems(numberOfItems)
            .status(OrderStatus.PLACED)
            .build();
        orderRepository.save(order);

        List<com.Podzilla.analytics.models.OrderItem> orderItems =
            items.stream()
            .map(item -> orderItemService.saveOrderItem(
                item.getQuantity(),
                item.getPricePerUnit(),
                item.getProductId(),
                orderUUID.toString()
            ))
            .toList();
        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }
}
