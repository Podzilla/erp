package com.Podzilla.analytics.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private UUID id;

    private BigDecimal totalAmount;

    private LocalDateTime orderPlacedTimestamp;
    private LocalDateTime orderFulfillmentFailedTimestamp;
    private LocalDateTime orderCancelledTimestamp;
    private LocalDateTime shippedTimestamp;
    private LocalDateTime deliveredTimestamp;
    private LocalDateTime orderDeliveryFailedTimestamp;
    private LocalDateTime finalStatusTimestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = true)
    private String failureReason;

    private int numberOfItems;
    private BigDecimal courierRating;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = true)
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = true)
    private Region region;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public enum OrderStatus {
        PLACED,
        FULFILLMENT_FAILED,
        CANCELLED,
        SHIPPED,
        DELIVERED,
        DELIVERY_FAILED
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private BigDecimal totalAmount;
        private LocalDateTime orderPlacedTimestamp;
        private LocalDateTime orderFulfillmentFailedTimestamp;
        private LocalDateTime orderCancelledTimestamp;
        private LocalDateTime shippedTimestamp;
        private LocalDateTime deliveredTimestamp;
        private LocalDateTime orderDeliveryFailedTimestamp;
        private LocalDateTime finalStatusTimestamp;
        private OrderStatus status;
        private String failureReason;
        private int numberOfItems;
        private BigDecimal courierRating;
        private Customer customer;
        private Courier courier;
        private Region region;
        private List<OrderItem> orderItems;

        public Builder() {
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder totalAmount(final BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder orderPlacedTimestamp(
                final LocalDateTime orderPlacedTimestamp) {
            this.orderPlacedTimestamp = orderPlacedTimestamp;
            return this;
        }

        public Builder orderFulfillmentFailedTimestamp(
                final LocalDateTime orderFulfillmentFailedTimestamp) {
            this.orderFulfillmentFailedTimestamp =
                orderFulfillmentFailedTimestamp;
            return this;
        }

        public Builder orderCancelledTimestamp(
                final LocalDateTime orderCancelledTimestamp) {
            this.orderCancelledTimestamp = orderCancelledTimestamp;
            return this;
        }

        public Builder shippedTimestamp(final LocalDateTime shippedTimestamp) {
            this.shippedTimestamp = shippedTimestamp;
            return this;
        }

        public Builder deliveredTimestamp(
                final LocalDateTime deliveredTimestamp) {
            this.deliveredTimestamp = deliveredTimestamp;
            return this;
        }

        public Builder orderDeliveryFailedTimestamp(
                final LocalDateTime orderDeliveryFailedTimestamp) {
            this.orderDeliveryFailedTimestamp = orderDeliveryFailedTimestamp;
            return this;
        }

        public Builder finalStatusTimestamp(
                final LocalDateTime finalStatusTimestamp) {
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

        public Builder orderItems(
                final List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(
                    id,
                    totalAmount,
                    orderPlacedTimestamp,
                    orderFulfillmentFailedTimestamp,
                    orderCancelledTimestamp,
                    shippedTimestamp,
                    deliveredTimestamp,
                    orderDeliveryFailedTimestamp,
                    finalStatusTimestamp,
                    status,
                    failureReason,
                    numberOfItems,
                    courierRating,
                    customer,
                    courier,
                    region,
                    orderItems);
        }
    }
}
