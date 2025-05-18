package com.Podzilla.analytics.models;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "sales_line_items")
@Data
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesLineItem {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private int quantity;
    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private int quantity;
        private BigDecimal pricePerUnit;
        private Product product;
        private Order order;

        public Builder() { }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder pricePerUnit(final BigDecimal pricePerUnit) {
            this.pricePerUnit = pricePerUnit;
            return this;
        }

        public Builder product(final Product product) {
            this.product = product;
            return this;
        }

        public Builder order(final Order order) {
            this.order = order;
            return this;
        }

        public SalesLineItem build() {
            return new SalesLineItem(
                id,
                quantity,
                pricePerUnit,
                product,
                order
            );
        }
    }
}
