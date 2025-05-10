package com.Podzilla.analytics.models;


import jakarta.persistence.*; // Use javax.persistence.* for older JPA versions
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;







// NOTE: this is AI generated  















@Entity
@Table(name = "order_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalytics {

    @Id // Primary Key
    private String analyticId; // Using analyticId as the primary key for this derived/analytic Order record

    private Instant timestamp;

    private String customerId; // Assuming 'customerID' was a typo

    private double totalAmount; // Note: BigDecimal is generally preferred for currency

    private double rating;

    // Note: This looks like the original Order ID, distinct from analyticId
    // If 'analyticId' is a *new* ID for the analytic record, and 'orderID' is the *original* order ID,
    // you might make orderID a natural key if needed for lookups, but analyticId is the PK here.
    @Column(name = "original_order_id") // Give it a clear column name if different from field name
    private String orderId; // Assuming 'orderID' was a typo

    // Mapping the status string directly
    private String status; // Stores "completed", "failed", "inprogress" as text
    // Alternatively, you could use an Enum if the set of statuses is fixed and small
    // @Enumerated(EnumType.STRING)
    // private OrderStatus status; // Need to define OrderStatus enum

}