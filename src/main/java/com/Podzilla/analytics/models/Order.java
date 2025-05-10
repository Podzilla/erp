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

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount;
    private LocalDateTime orderPlacedTimestamp;
    private LocalDateTime shippedTimestamp;
    private LocalDateTime deliveredTimestamp;
    private LocalDateTime finalStatusTimestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = true)
    private String failureReason;

    private int numberOfItems;
    private BigDecimal courierRating;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<SalesLineItem> salesLineItems;

    public enum OrderStatus {
        PLACED,
        SHIPPED,
        DELIVERED_PENDING_PAYMENT,
        COMPLETED,
        FAILED
    }
}
