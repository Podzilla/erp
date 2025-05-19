package com.Podzilla.analytics.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.models.ProductSnapshot;
import com.Podzilla.analytics.repositories.ProductRepository;
import com.Podzilla.analytics.repositories.ProductSnapshotRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class InventoryReportControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private ProductSnapshotRepository productSnapshotRepository;

        @Autowired
        private EntityManager entityManager;

        private Product electronicsProduct;
        private Product clothingProduct;

        @BeforeEach
        void setUp() {
                productSnapshotRepository.deleteAll();
                productRepository.deleteAll();
                entityManager.flush();
                entityManager.clear();

                // Create test products
                electronicsProduct = productRepository.save(Product.builder()
                                .id(UUID.randomUUID())
                                .name("Laptop")
                                .category("Electronics")
                                .cost(new BigDecimal("1000.00"))
                                .lowStockThreshold(10)
                                .build());

                clothingProduct = productRepository.save(Product.builder()
                                .id(UUID.randomUUID())
                                .name("T-Shirt")
                                .category("Clothing")
                                .cost(new BigDecimal("20.00"))
                                .lowStockThreshold(5)
                                .build());

                entityManager.flush();
                entityManager.clear();

                // Create product snapshots in a separate transaction
                ProductSnapshot electronicsSnapshot = ProductSnapshot.builder()
                                .timestamp(LocalDateTime.now())
                                .product(electronicsProduct)
                                .quantity(15)
                                .build();

                ProductSnapshot clothingSnapshot = ProductSnapshot.builder()
                                .timestamp(LocalDateTime.now())
                                .product(clothingProduct)
                                .quantity(3) // Below threshold
                                .build();

                productSnapshotRepository.save(electronicsSnapshot);
                productSnapshotRepository.save(clothingSnapshot);
                entityManager.flush();
                entityManager.clear();
        }

        @AfterEach
        void tearDown() {
                productSnapshotRepository.deleteAll();
                productRepository.deleteAll();
                entityManager.flush();
                entityManager.clear();
        }

        @Test
        void contextLoads() {
        }

        @Test
        void getInventoryValueByCategory_ShouldReturnListOfCategoryValues() throws Exception {
                mockMvc.perform(get("/inventory-analytics/value/by-category"))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[?(@.category == '" + electronicsProduct.getCategory()
                                                + "')].totalStockValue")
                                                .value(hasItem(closeTo(electronicsProduct.getCost()
                                                                .multiply(new BigDecimal("15"))
                                                                .doubleValue(), 0.01))))
                                .andExpect(jsonPath("$[?(@.category == '" + clothingProduct.getCategory()
                                                + "')].totalStockValue")
                                                .value(hasItem(closeTo(clothingProduct.getCost()
                                                                .multiply(new BigDecimal("3"))
                                                                .doubleValue(), 0.01))));
        }

        @Test
        void getLowStockProducts_ShouldReturnOnlyProductsBelowThreshold() throws Exception {
                mockMvc.perform(get("/inventory-analytics/low-stock")
                                .param("page", "0")
                                .param("size", "10"))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", hasSize(1)))
                                .andExpect(jsonPath("$.content[0].productName").value(clothingProduct.getName()))
                                .andExpect(jsonPath("$.content[0].currentQuantity").value(3))
                                .andExpect(jsonPath("$.content[0].threshold")
                                                .value(clothingProduct.getLowStockThreshold()));
        }

        @Test
        void getLowStockProducts_ShouldReturnEmptyListWhenNoProductsBelowThreshold() throws Exception {
                // Create a new snapshot with quantity above threshold
                ProductSnapshot newSnapshot = ProductSnapshot.builder()
                                .timestamp(LocalDateTime.now())
                                .product(clothingProduct)
                                .quantity(clothingProduct.getLowStockThreshold() + 1)
                                .build();

                productSnapshotRepository.save(newSnapshot);
                entityManager.flush();
                entityManager.clear();

                mockMvc.perform(get("/inventory-analytics/low-stock")
                                .param("page", "0")
                                .param("size", "10"))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", hasSize(0)));
        }

        @Test
        void getLowStockProducts_ShouldHandlePagination() throws Exception {
                Product product2 = productRepository.save(Product.builder()
                                .id(UUID.randomUUID())
                                .name("Jeans")
                                .category(clothingProduct.getCategory())
                                .cost(new BigDecimal("50.00"))
                                .lowStockThreshold(8)
                                .build());

                entityManager.flush();
                entityManager.clear();

                ProductSnapshot snapshot2 = ProductSnapshot.builder()
                                .timestamp(LocalDateTime.now())
                                .product(product2)
                                .quantity(5)
                                .build();

                productSnapshotRepository.save(snapshot2);
                entityManager.flush();
                entityManager.clear();

                mockMvc.perform(get("/inventory-analytics/low-stock")
                                .param("page", "0")
                                .param("size", "1"))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", hasSize(1)))
                                .andExpect(jsonPath("$.content[0].productName").value(clothingProduct.getName()))
                                .andExpect(jsonPath("$.content[0].currentQuantity").value(3))
                                .andExpect(jsonPath("$.content[0].threshold")
                                                .value(clothingProduct.getLowStockThreshold()))
                                .andExpect(jsonPath("$.totalElements").value(2))
                                .andExpect(jsonPath("$.totalPages").value(2));

                mockMvc.perform(get("/inventory-analytics/low-stock")
                                .param("page", "1")
                                .param("size", "1"))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", hasSize(1)))
                                .andExpect(jsonPath("$.content[0].productName").value(product2.getName()))
                                .andExpect(jsonPath("$.content[0].currentQuantity").value(5))
                                .andExpect(jsonPath("$.content[0].threshold").value(product2.getLowStockThreshold()))
                                .andExpect(jsonPath("$.totalElements").value(2))
                                .andExpect(jsonPath("$.totalPages").value(2));
        }
}