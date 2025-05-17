package com.Podzilla.analytics.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "couriers")
@Data
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier {
    @Id
    private UUID id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    public enum CourierStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String name;
        private CourierStatus status;

        public Builder() { }
        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder status(final CourierStatus status) {
            this.status = status;
            return this;
        }

        public Courier build() {
            return new Courier(id, name, status);
        }
    }
}
