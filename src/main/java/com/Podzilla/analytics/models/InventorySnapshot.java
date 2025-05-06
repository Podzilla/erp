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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a snapshot of product inventory at a specific point in time in the
 * Podzilla analytics system.
 */
@Entity
@Table(name = "inventory_snapshots")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventorySnapshot {
    /** Unique identifier for the inventory snapshot. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Timestamp when the inventory snapshot was taken. */
    private LocalDateTime timestamp;

    /** Product associated with this inventory snapshot. */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** Quantity of the product in stock at the snapshot time. */
    private int quantity;
}
