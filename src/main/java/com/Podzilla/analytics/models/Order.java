package com.Podzilla.analytics.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an order in the Podzilla analytics system.
 */
@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /** Unique identifier for the order. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Total amount of the order. */
    private BigDecimal totalAmount;

    /** Timestamp when the order was placed. */
    private LocalDateTime orderPlacedTimestamp;

    /** Timestamp when the order was shipped. */
    private LocalDateTime shippedTimestamp;

    /** Timestamp when the order was delivered. */
    private LocalDateTime deliveredTimestamp;

    /** Timestamp when the order reached its final status. */
    private LocalDateTime finalStatusTimestamp;

    /** Current status of the order. */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /** Reason for order failure, if applicable. */
    @Column(nullable = true)
    private String failureReason;

    /** Total number of items in the order. */
    private int numberOfItems;

    /** Rating given to the courier for this order. */
    private BigDecimal courierRating;

    /** Customer who placed the order. */
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /** Courier responsible for delivering the order. */
    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    /** Region where the order is to be delivered. */
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    /** List of sales line items associated with the order. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<SalesLineItem> salesLineItems;

    /**
     * Enum representing possible statuses of an order.
     */
    public enum OrderStatus {
        /** Order has been placed but not yet processed. */
        PLACED,
        /** Order has been shipped. */
        SHIPPED,
        /** Order has been delivered but payment is pending. */
        DELIVERED_PENDING_PAYMENT,
        /** Order has been successfully completed. */
        COMPLETED,
        /** Order has failed due to an issue. */
        FAILED
    }
}
