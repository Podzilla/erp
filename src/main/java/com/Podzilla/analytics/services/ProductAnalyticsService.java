package com.Podzilla.analytics.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.TopSellerRequest;
import com.Podzilla.analytics.api.DTOs.TopSellerRequest.SortBy; // Import SortBy enum
import com.Podzilla.analytics.api.DTOs.TopSellerResponse;
import com.Podzilla.analytics.repositories.ProductRepository; // Import ProductRepository

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductAnalyticsService {
}
