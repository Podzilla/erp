package com.Podzilla.analytics.api.dtos.order;
import lombok.*;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRegionDTO {
    private Long regionId;
    private String city;
    private String country;
    private Long orderCount;
    private BigDecimal averageOrderValue;
}
