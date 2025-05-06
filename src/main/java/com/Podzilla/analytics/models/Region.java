package com.Podzilla.analytics.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a geographical region in the Podzilla analytics system.
 */
@Entity
@Table(name = "regions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    /** Unique identifier for the region. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** City name of the region. */
    private String city;

    /** State or province of the region. */
    private String state;

    /** Country of the region. */
    private String country;

    /** Postal code of the region. */
    private String postalCode;
}
