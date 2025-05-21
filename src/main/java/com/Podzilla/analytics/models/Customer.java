package com.Podzilla.analytics.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private UUID id;
        private String name;
        private List<Order> orders;

        public Builder() { }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder orders(final List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Customer build() {
            return new Customer(id, name, orders);
        }
    }
}
