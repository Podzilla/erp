package com.Podzilla.analytics.models;


import jakarta.persistence.*; // Use javax.persistence.* for older JPA versions
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;







// NOTE: this is AI generated  






@Entity
@Table(name = "customer_analytics") // Table name in DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAnalytic {

    @Id // Primary Key
    private String analyticId;

    private Instant timestamp;

    private String customerId;

    private double totalAmount; // Note: BigDecimal is generally preferred for currency

    private long duration; // Using long for duration

    private double rating;
}