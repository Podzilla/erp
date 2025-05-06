package com.Podzilla.analytics.models;

import java.math.BigDecimal;

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
 * Represents a single item in an order in the Podzilla analytics system.
 */
@Entity
@Table(name = "sales_line_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesLineItem {
    /** Unique identifier for the sales line item. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Quantity of the product ordered. */
    private int quantity;

    /** Price per unit of the product. */
    private BigDecimal pricePerUnit;

    /** Product associated with this line item. */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** Order this line item belongs to. */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
