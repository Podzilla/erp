package com.Podzilla.analytics.models;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_snapshots")
@Data
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventorySnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Long id;
        private LocalDateTime timestamp;
        private Product product;
        private int quantity;

        public Builder() { }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder timestamp(final LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder product(final Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public InventorySnapshot build() {
            return new InventorySnapshot(id, timestamp, product, quantity);
        }
    }

}
