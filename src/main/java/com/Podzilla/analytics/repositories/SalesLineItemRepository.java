package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.SalesLineItem;

public interface SalesLineItemRepository extends
    JpaRepository<SalesLineItem, Long> {
}
