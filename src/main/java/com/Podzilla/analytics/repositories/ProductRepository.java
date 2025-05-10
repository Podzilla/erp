package com.Podzilla.analytics.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Podzilla.analytics.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
