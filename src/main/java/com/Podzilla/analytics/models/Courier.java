package com.Podzilla.analytics.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "couriers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    public enum CourierStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }
}
