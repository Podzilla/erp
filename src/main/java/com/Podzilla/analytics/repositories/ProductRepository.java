package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Podzilla.analytics.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
