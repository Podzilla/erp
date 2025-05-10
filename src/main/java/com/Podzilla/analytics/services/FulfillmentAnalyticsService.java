package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;
import com.Podzilla.analytics.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> getAveragePlaceToShipTime(
            final LocalDate startDate, final LocalDate endDate,
            final PlaceToShipGroupBy groupBy) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<Map<String, Object>> results = new ArrayList<>();

        switch (groupBy) {
            case OVERALL:
                Double avgOverall = orderRepository
                        .findAveragePlaceToShipTimeOverall(
                                startDateTime,
                                endDateTime);
                if (avgOverall != null) {
                    Map<String, Object> overallResult = new HashMap<>();
                    overallResult.put("groupByValue", "OVERALL");
                    // Convert seconds to hours
                    overallResult.put("averageDuration",
                            avgOverall / SECONDS_PER_HOUR);
                    results.add(overallResult);
                }
                break;
            case REGION:
                List<Object[]> avgByRegion = orderRepository
                        .findAveragePlaceToShipTimeByRegion(
                                startDateTime,
                                endDateTime);
                for (Object[] row : avgByRegion) {
                    Map<String, Object> regionResult = new HashMap<>();
                    // Example: Prefixing for clarity
                    regionResult.put("groupByValue", "RegionID_" + row[0]);
                    // Convert seconds to hours
                    regionResult.put("averageDuration",
                            (Double) row[1] / SECONDS_PER_HOUR);
                    results.add(regionResult);
                }
                break;
            default:
                // Optionally handle unknown groupBy or throw an exception
                break;
        }
        return results;
    }

    public List<Map<String, Object>> getAverageShipToDeliverTime(
            final LocalDate startDate, final LocalDate endDate,
            final ShipToDeliverGroupBy groupBy) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<Map<String, Object>> results = new ArrayList<>();

        // Note: Using shippedTimestamp for the date range check here
        switch (groupBy) {
            case OVERALL:
                Double avgOverall = orderRepository
                        .findAverageShipToDeliverTimeOverall(
                                startDateTime,
                                endDateTime);
                if (avgOverall != null) {
                    Map<String, Object> overallResult = new HashMap<>();
                    overallResult.put("groupByValue", "OVERALL");
                    // Convert seconds to hours
                    overallResult.put("averageDuration",
                            avgOverall / SECONDS_PER_HOUR);
                    results.add(overallResult);
                }
                break;
            case REGION:
                List<Object[]> avgByRegion = orderRepository
                        .findAverageShipToDeliverTimeByRegion(
                                startDateTime,
                                endDateTime);
                for (Object[] row : avgByRegion) {
                    Map<String, Object> regionResult = new HashMap<>();
                    regionResult.put("groupByValue", "RegionID_" + row[0]);
                    // Convert seconds to hours
                    regionResult.put("averageDuration",
                            (Double) row[1] / SECONDS_PER_HOUR);
                    results.add(regionResult);
                }
                break;
            case COURIER:
                 List<Object[]> avgByCourier = orderRepository
                         .findAverageShipToDeliverTimeByCourier(
                                 startDateTime,
                                 endDateTime);
                 for (Object[] row : avgByCourier) {
                    Map<String, Object> courierResult = new HashMap<>();
                    courierResult.put("groupByValue", "CourierID_" + row[0]);
                    // Convert seconds to hours
                    courierResult.put("averageDuration",
                            (Double) row[1] / SECONDS_PER_HOUR);
                    results.add(courierResult);
                }
                break;
            default:
                // Optionally handle unknown groupBy or throw an exception
                break;
        }
        return results;
    }
}
