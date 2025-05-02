package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.InventorySnapshot;

public interface InventorySnapshotRepository extends JpaRepository<InventorySnapshot, Long> {
}