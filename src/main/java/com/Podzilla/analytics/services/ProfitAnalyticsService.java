package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.ProfitByCategoryDTO;
import com.Podzilla.analytics.repositories.SalesLineItemRepository;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfitAnalyticsService {
    private final SalesLineItemRepository salesLineItemRepository;
    
    public List<ProfitByCategoryDTO> getProfitByCategory(LocalDate startDate, LocalDate endDate) {
        // Convert LocalDate to LocalDateTime for start of day and end of day
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        List<Object[]> salesData = salesLineItemRepository.findSalesByCategoryBetweenDates(startDateTime, endDateTime);
        List<ProfitByCategoryDTO> result = new ArrayList<>();
        
        for (Object[] data : salesData) {
            String category = (String) data[0];
            BigDecimal totalRevenue = (BigDecimal) data[1];
            BigDecimal totalCost = (BigDecimal) data[2];
            BigDecimal grossProfit = totalRevenue.subtract(totalCost);
            
            BigDecimal grossProfitMargin = BigDecimal.ZERO;
            if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
                grossProfitMargin = grossProfit.divide(totalRevenue, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
            }
            
            result.add(ProfitByCategoryDTO.builder()
                    .category(category)
                    .totalRevenue(totalRevenue)
                    .totalCost(totalCost)
                    .grossProfit(grossProfit)
                    .grossProfitMargin(grossProfitMargin)
                    .build());
        }
        
        return result;
    }
}