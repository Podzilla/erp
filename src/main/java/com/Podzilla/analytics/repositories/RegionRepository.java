package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.Region;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
}
