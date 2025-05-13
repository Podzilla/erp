package com.Podzilla.analytics.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.fulfillment
.FulfillmentPlaceToShipRequest;
import com.Podzilla.analytics.api.dtos.fulfillment
.FulfillmentShipToDeliverRequest;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentTimeResponse;
import com.Podzilla.analytics.services.FulfillmentAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/fulfillment-analytics")
public class FulfillmentReportController {
    private final FulfillmentAnalyticsService fulfillmentAnalyticsService;

    @Operation(summary = "Get average time from order placement to shipping",
     description = "Returns the average time (in hours) between when"
            + " an order was placed and when it was shipped, grouped"
            + " by the specified dimension")
    @GetMapping("/place-to-ship-time")
    public ResponseEntity<List<FulfillmentTimeResponse>> getPlaceToShipTime(
            @Valid @ModelAttribute final FulfillmentPlaceToShipRequest req) {

        List<FulfillmentTimeResponse> reportData = fulfillmentAnalyticsService
                .getPlaceToShipTimeResponse(
                        req.getStartDate(),
                        req.getEndDate(),
                        req.getGroupBy());
        return ResponseEntity.ok(reportData);
    }

    @Operation(summary = "Get average time from shipping to delivery",
     description = "Returns the average time (in hours) between when"
            + " an order was shipped and when it was delivered, grouped"
            + " by the specified dimension")
    @GetMapping("/ship-to-deliver-time")
    public ResponseEntity<List<FulfillmentTimeResponse>> getShipToDeliverTime(
            @Valid @ModelAttribute final FulfillmentShipToDeliverRequest req) {

        log.debug(req.toString());

        List<FulfillmentTimeResponse> reportData = fulfillmentAnalyticsService
                .getShipToDeliverTimeResponse(
                        req.getStartDate(),
                        req.getEndDate(),
                        req.getGroupBy());
        return ResponseEntity.ok(reportData);
    }
}
