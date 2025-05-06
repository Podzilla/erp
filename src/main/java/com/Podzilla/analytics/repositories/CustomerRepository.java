package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
