package com.Podzilla.analytics.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private BigDecimal cost;
    private int lowStockThreshold;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String category;
        private BigDecimal cost;
        private int lowStockThreshold;

        public Builder() { }

        public Builder id(final Long id) {
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

        public Product build() {
            return new Product(id, name, category, cost, lowStockThreshold);
        }
    }
}
