package com.Podzilla.analytics.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private UUID id;
    private String name;

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private UUID id;
        private String name;

        public Builder() { }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Customer build() {
            return new Customer(id, name);
        }
    }
}
