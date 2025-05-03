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

    public List<Map<String, Object>> getAveragePlaceToShipTime(LocalDate startDate, LocalDate endDate, PlaceToShipGroupBy groupBy) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<Map<String, Object>> results = new ArrayList<>();

        switch (groupBy) {
            case OVERALL:
                Double avgOverall = orderRepository.findAveragePlaceToShipTimeOverall(startDateTime, endDateTime);
                if (avgOverall != null) {
                    Map<String, Object> overallResult = new HashMap<>();
                    overallResult.put("groupByValue", "OVERALL");
                    overallResult.put("averageDuration", avgOverall / SECONDS_PER_HOUR); // Convert seconds to hours
                    results.add(overallResult);
                }
                break;
            case REGION:
                List<Object[]> avgByRegion = orderRepository.findAveragePlaceToShipTimeByRegion(startDateTime, endDateTime);
                for (Object[] row : avgByRegion) {
                    Map<String, Object> regionResult = new HashMap<>();
                    regionResult.put("groupByValue", "RegionID_" + row[0]); // Example: Prefixing for clarity
                    regionResult.put("averageDuration", (Double) row[1] / SECONDS_PER_HOUR); // Convert seconds to hours
                    results.add(regionResult);
                }
                break;
        }
        return results;
    }

    public List<Map<String, Object>> getAverageShipToDeliverTime(LocalDate startDate, LocalDate endDate, ShipToDeliverGroupBy groupBy) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        List<Map<String, Object>> results = new ArrayList<>();

        // Note: Using shippedTimestamp for the date range check here
        switch (groupBy) {
            case OVERALL:
                Double avgOverall = orderRepository.findAverageShipToDeliverTimeOverall(startDateTime, endDateTime);
                if (avgOverall != null) {
                    Map<String, Object> overallResult = new HashMap<>();
                    overallResult.put("groupByValue", "OVERALL");
                    overallResult.put("averageDuration", avgOverall / SECONDS_PER_HOUR); // Convert seconds to hours
                    results.add(overallResult);
                }
                break;
            case REGION:
                List<Object[]> avgByRegion = orderRepository.findAverageShipToDeliverTimeByRegion(startDateTime, endDateTime);
                for (Object[] row : avgByRegion) {
                    Map<String, Object> regionResult = new HashMap<>();
                    regionResult.put("groupByValue", "RegionID_" + row[0]);
                    regionResult.put("averageDuration", (Double) row[1] / SECONDS_PER_HOUR);
                    results.add(regionResult);
                }
                break;
            case COURIER:
                 List<Object[]> avgByCourier = orderRepository.findAverageShipToDeliverTimeByCourier(startDateTime, endDateTime);
                 for (Object[] row : avgByCourier) {
                    Map<String, Object> courierResult = new HashMap<>();
                    courierResult.put("groupByValue", "CourierID_" + row[0]);
                    courierResult.put("averageDuration", (Double) row[1] / SECONDS_PER_HOUR);
                    results.add(courierResult);
                }
                break;
        }
        return results;
    }
}