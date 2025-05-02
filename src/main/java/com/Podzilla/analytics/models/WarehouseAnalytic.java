package com.Podzilla.analytics.models;

import jakarta.persistence.*; // Use javax.persistence.* for older JPA versions
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;





// NOTE: this is AI generated  









@Entity
@Table(name = "warehouse_analytics") // Table name in DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseAnalytic {

    @Id // Primary Key
    private String analyticId;

    private String productId; // Assuming 'productid' was a typo

    private Instant timestamp;

    private int currentQuantity; // Assuming 'current Quantity' was a typo

    private int soldQuantity; // Assuming 'sold quantity' was a typo

    private double profit; // Note: BigDecimal is generally preferred for currency/profit

    private boolean isLow;
}