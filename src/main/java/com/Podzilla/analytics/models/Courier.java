package com.Podzilla.analytics.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a courier entity in the Podzilla analytics system.
 */
@Entity
@Table(name = "couriers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier {
    /** Unique identifier for the courier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the courier company. */
    private String name;

    /** Current status of the courier. */
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    /**
     * Enum representing possible statuses of a courier.
     */
    public enum CourierStatus {
        /** Courier is available for deliveries. */
        ACTIVE,
        /** Courier is not currently operational. */
        INACTIVE,
        /** Courier is temporarily suspended. */
        SUSPENDED
    }
}
