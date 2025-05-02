package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}