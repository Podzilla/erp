package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.util.DatetimeFormatter;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentTimeResponse;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentPlaceToShipRequest.PlaceToShipGroupBy;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentShipToDeliverRequest.ShipToDeliverGroupBy;
import com.Podzilla.analytics.api.projections.fulfillment.FulfillmentTimeProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FulfillmentAnalyticsService {

    private final OrderRepository orderRepository;

    public List<FulfillmentTimeResponse> getPlaceToShipTimeResponse(
            final LocalDate startDate,
            final LocalDate endDate,
            final PlaceToShipGroupBy groupBy) {
        log.info("Getting place-to-ship time response between {} and {}"
                + " with groupBy {}", startDate, endDate, groupBy);
        LocalDateTime startDateTime = DatetimeFormatter
                .convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime = DatetimeFormatter
                .convertEndDateToDatetime(endDate);
        List<FulfillmentTimeResponse> results = new ArrayList<>();

        switch (groupBy) {
            case OVERALL:
                log.debug("Fetching overall place-to-ship time");
                FulfillmentTimeProjection overall = orderRepository
                        .findPlaceToShipTimeOverall(startDateTime, endDateTime);
                if (overall != null) {
                    results.add(convertToResponse(overall));
                }
                break;
            case REGION:
                log.debug("Fetching place-to-ship time by region");
                List<FulfillmentTimeProjection> byRegion = orderRepository
                        .findPlaceToShipTimeByRegion(
                                startDateTime, endDateTime);
                results.addAll(byRegion.stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList()));
                break;
            default:
                log.warn("Unknown groupBy value for place-to-ship: {}",
                        groupBy);
                // Handle unknown groupBy or throw an exception
                break;
        }

        return results;
    }

    public List<FulfillmentTimeResponse> getShipToDeliverTimeResponse(
            final LocalDate startDate,
            final LocalDate endDate,
            final ShipToDeliverGroupBy groupBy) {
        log.info("Getting ship-to-deliver time response between {} and"
                + " {} with groupBy {}", startDate, endDate,
                groupBy);
        LocalDateTime startDateTime = DatetimeFormatter
                .convertStartDateToDatetime(startDate);
        LocalDateTime endDateTime = DatetimeFormatter
                .convertEndDateToDatetime(endDate);
        List<FulfillmentTimeResponse> results = new ArrayList<>();

        switch (groupBy) {
            case OVERALL:
                log.debug("Fetching overall ship-to-deliver time");
                FulfillmentTimeProjection overall = orderRepository
                        .findShipToDeliverTimeOverall(
                                startDateTime, endDateTime);
                if (overall != null) {
                    results.add(convertToResponse(overall));
                }
                break;
            case REGION:
                log.debug("Fetching ship-to-deliver time by region");
                List<FulfillmentTimeProjection> byRegion = orderRepository
                        .findShipToDeliverTimeByRegion(
                                startDateTime, endDateTime);
                results.addAll(byRegion.stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList()));
                break;
            case COURIER:
                log.debug("Fetching ship-to-deliver time by courier");
                List<FulfillmentTimeProjection> byCourier = orderRepository
                        .findShipToDeliverTimeByCourier(
                                startDateTime, endDateTime);
                results.addAll(byCourier.stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList()));
                break;
            default:
                log.warn("Unknown groupBy value for ship-to-deliver: {}",
                        groupBy);
                // Handle unknown groupBy or throw an exception
                break;
        }

        return results;
    }

    private FulfillmentTimeResponse convertToResponse(
            final FulfillmentTimeProjection projection) {
        return FulfillmentTimeResponse.builder()
                .groupByValue(projection.getGroupByValue())
                .averageDuration(
                        BigDecimal.valueOf(projection.getAverageDuration()))
                .build();
    }
}
