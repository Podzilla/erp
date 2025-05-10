package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentTimeResponse;
import com.Podzilla.analytics.api.projections.fulfillment.FulfillmentTimeProjection;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FulfillmentAnalyticsService {

    private final OrderRepository orderRepository;
    private static final double SECONDS_PER_HOUR = 3600.0;

    public enum PlaceToShipGroupBy {
        REGION, OVERALL
    }

    public enum ShipToDeliverGroupBy {
        REGION, OVERALL, COURIER
    }

    public List<FulfillmentTimeResponse> getPlaceToShipTimeResponse(
        final LocalDate startDate,
        final LocalDate endDate,
        final PlaceToShipGroupBy groupBy
    ) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<FulfillmentTimeResponse> results = new ArrayList<>();

        switch (groupBy) {
        case OVERALL:
            FulfillmentTimeProjection overall = orderRepository
                .findPlaceToShipTimeOverall(startDateTime, endDateTime);
            if (overall != null) {
                results.add(convertToResponse(overall));
            }
            break;
        case REGION:
            List<FulfillmentTimeProjection> byRegion = orderRepository
                .findPlaceToShipTimeByRegion(startDateTime, endDateTime);
            results.addAll(byRegion.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList()));
            break;
        default:
            // Handle unknown groupBy or throw an exception
            break;
        }

        return results;
    }

    public List<FulfillmentTimeResponse> getShipToDeliverTimeResponse(
        final LocalDate startDate,
        final LocalDate endDate,
        final ShipToDeliverGroupBy groupBy
    ) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<FulfillmentTimeResponse> results = new ArrayList<>();

        switch (groupBy) {
        case OVERALL:
            FulfillmentTimeProjection overall = orderRepository
                .findShipToDeliverTimeOverall(startDateTime, endDateTime);
            if (overall != null) {
                results.add(convertToResponse(overall));
            }
            break;
        case REGION:
            List<FulfillmentTimeProjection> byRegion = orderRepository
                .findShipToDeliverTimeByRegion(startDateTime, endDateTime);
            results.addAll(byRegion.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList()));
            break;
        case COURIER:
            List<FulfillmentTimeProjection> byCourier = orderRepository
                .findShipToDeliverTimeByCourier(startDateTime, endDateTime);
            results.addAll(byCourier.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList()));
            break;
        default:
            // Handle unknown groupBy or throw an exception
            break;
        }

        return results;
    }

    private FulfillmentTimeResponse convertToResponse(
        final FulfillmentTimeProjection projection
    ) {
        return FulfillmentTimeResponse.builder()
            .groupByValue(projection.getGroupByValue())
            .averageDuration(
                BigDecimal.valueOf(projection.getAverageDuration()))
            .build();
    }

    private FulfillmentTimeResponse convertToFulfillmentTimeResponse(
        final Map<String, Object> data
    ) {
        return FulfillmentTimeResponse.builder()
            .groupByValue((String) data.get("groupByValue"))
            .averageDuration(BigDecimal.valueOf(
                (Double) data.get("averageDuration")))
            .build();
    }

    ///////////////////////

    public List<Map<String, Object>> getAveragePlaceToShipTime(
        final LocalDate startDate,
        final LocalDate endDate,
        final PlaceToShipGroupBy groupBy
    ) {
        // This method is kept for backward compatibility
        return new ArrayList<>();
    }

    public List<Map<String, Object>> getAverageShipToDeliverTime(
        final LocalDate startDate,
        final LocalDate endDate,
        final ShipToDeliverGroupBy groupBy
    ) {
        // This method is kept for backward compatibility
        return new ArrayList<>();
    }
}
