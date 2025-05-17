package com.Podzilla.analytics.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "regions")
@Data
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String city;
        private String state;
        private String country;
        private String postalCode;

        public Builder() { }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder city(final String city) {
            this.city = city;
            return this;
        }

        public Builder state(final String state) {
            this.state = state;
            return this;
        }

        public Builder country(final String country) {
            this.country = country;
            return this;
        }

        public Builder postalCode(final String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Region build() {
            return new Region(id, city, state, country, postalCode);
        }
    }
}
