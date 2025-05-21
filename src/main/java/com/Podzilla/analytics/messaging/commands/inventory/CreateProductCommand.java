package com.Podzilla.analytics.messaging.commands.inventory;

import java.math.BigDecimal;
import com.Podzilla.analytics.services.ProductAnalyticsService;

import lombok.Builder;

import com.Podzilla.analytics.messaging.commands.Command;

@Builder
public class CreateProductCommand implements Command {

    private ProductAnalyticsService productAnalyticsService;
    private String productId;
    private String productName;
    private String productCategory;
    private BigDecimal productCost;
    private Integer productLowStockThreshold;

    @Override
    public void execute() {
        productAnalyticsService.saveProduct(
            productId,
            productName,
            productCategory,
            productCost,
            productLowStockThreshold
        );
    }
}
