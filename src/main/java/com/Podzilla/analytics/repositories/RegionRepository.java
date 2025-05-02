package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}