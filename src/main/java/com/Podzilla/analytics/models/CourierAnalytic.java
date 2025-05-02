package com.Podzilla.analytics.models;

import jakarta.persistence.*; 
import java.time.Instant;

import lombok.Data; 
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor; 

// NOTE: this is AI generated  





@Entity
@Table(name = "courier_analytics")
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor @AllArgsConstructor 
public class CourierAnalytic {

    @Id // Primary Key
    private String analyticId;

    private Instant dispatchTimestamp;

    private String courierId;

    private long duration; // Using long for duration

    private boolean orderDelivered; 

    private double rating;
}