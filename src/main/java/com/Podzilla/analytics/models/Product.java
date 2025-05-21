package com.Podzilla.analytics.models;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private UUID id;
    private String name;
    private String category;
    private BigDecimal cost;
    private int lowStockThreshold;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String category;
        private BigDecimal cost;
        private int lowStockThreshold;
        private List<OrderItem> orderItems;

        public Builder() {
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder category(final String category) {
            this.category = category;
            return this;
        }

        public Builder cost(final BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public Builder lowStockThreshold(final int lowStockThreshold) {
            this.lowStockThreshold = lowStockThreshold;
            return this;
        }

        public Builder orderItems(final List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Product build() {
            return new Product(
                    id, name, category, cost, lowStockThreshold, orderItems);
        }
    }
}
