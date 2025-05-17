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
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
// @Builder
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private BigDecimal totalAmount;
        private LocalDateTime orderPlacedTimestamp;
        private LocalDateTime shippedTimestamp;
        private LocalDateTime deliveredTimestamp;
        private LocalDateTime finalStatusTimestamp;
        private OrderStatus status;
        private String failureReason;
        private int numberOfItems;
        private BigDecimal courierRating;
        private Customer customer;
        private Courier courier;
        private Region region;
        private List<SalesLineItem> salesLineItems;

        public Builder() { }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder totalAmount(final BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder orderPlacedTimestamp(
            final LocalDateTime orderPlacedTimestamp
        ) {
            this.orderPlacedTimestamp = orderPlacedTimestamp;
            return this;
        }

        public Builder shippedTimestamp(final LocalDateTime shippedTimestamp) {
            this.shippedTimestamp = shippedTimestamp;
            return this;
        }

        public Builder deliveredTimestamp(
            final LocalDateTime deliveredTimestamp
        ) {
            this.deliveredTimestamp = deliveredTimestamp;
            return this;
        }

        public Builder finalStatusTimestamp(
            final LocalDateTime finalStatusTimestamp
        ) {
            this.finalStatusTimestamp = finalStatusTimestamp;
            return this;
        }

        public Builder status(final OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder failureReason(final String failureReason) {
            this.failureReason = failureReason;
            return this;
        }

        public Builder numberOfItems(final int numberOfItems) {
            this.numberOfItems = numberOfItems;
            return this;
        }

        public Builder courierRating(final BigDecimal courierRating) {
            this.courierRating = courierRating;
            return this;
        }

        public Builder customer(final Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder courier(final Courier courier) {
            this.courier = courier;
            return this;
        }

        public Builder region(final Region region) {
            this.region = region;
            return this;
        }

        public Builder salesLineItems(
            final List<SalesLineItem> salesLineItems
        ) {
            this.salesLineItems = salesLineItems;
            return this;
        }

        public Order build() {
            return new Order(
                id,
                totalAmount,
                orderPlacedTimestamp,
                shippedTimestamp,
                deliveredTimestamp,
                finalStatusTimestamp,
                status,
                failureReason,
                numberOfItems,
                courierRating,
                customer,
                courier,
                region,
                salesLineItems
            );
        }
    }
}
