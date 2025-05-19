package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.Podzilla.analytics.repositories.OrderItemRepository;
import com.Podzilla.analytics.repositories.ProductRepository;
import com.Podzilla.analytics.repositories.OrderRepository;

import com.Podzilla.analytics.util.StringToUUIDParser;

import org.springframework.beans.factory.annotation.Autowired;
import com.Podzilla.analytics.models.OrderItem;
import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.models.Order;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {
    @Autowired
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final OrderRepository orderRepository;

    public OrderItem saveOrderItem(
            final int quantity,
            final BigDecimal pricePerUnit,
            final String productId,
            final String orderId) {
        log.info("Attempting to save OrderItem."
                + " quantity: {}, pricePerUnit: {}, productId: {}, orderId: {}",
                quantity, pricePerUnit, productId,
                orderId);

        UUID productUUID = StringToUUIDParser.parseStringToUUID(productId);
        UUID orderUUID = StringToUUIDParser.parseStringToUUID(orderId);

        log.debug("Parsed productId to UUID: {}, orderId to UUID: {}",
                productUUID, orderUUID);

        Product product = productRepository.findById(productUUID)
                .orElseThrow(() -> {
                    log.error("Product not found. productId: {}",
                            productId);
                    return new RuntimeException("Product not found");
                });

        Order order = orderRepository.findById(orderUUID)
                .orElseThrow(() -> {
                    log.error("Order not found. orderId: {}", orderId);
                    return new RuntimeException("Order not found");
                });

        OrderItem orderItem = OrderItem.builder()
                .quantity(quantity)
                .pricePerUnit(pricePerUnit)
                .product(product)
                .order(order)
                .build();

        log.info("Saving OrderItem for orderId: {}, productId: {}",
                orderId, productId);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        log.info("OrderItem saved successfully. orderItemId: {}",
                savedOrderItem.getId());

        return savedOrderItem;
    }
}
