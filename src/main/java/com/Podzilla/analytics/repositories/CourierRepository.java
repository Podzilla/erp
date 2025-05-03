package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}