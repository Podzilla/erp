package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
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
        final String orderId
    ) {
        UUID productUUID = StringToUUIDParser.parseStringToUUID(productId);
        UUID orderUUID = StringToUUIDParser.parseStringToUUID(orderId);
        Product product = productRepository.findById(productUUID)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        Order order = orderRepository.findById(orderUUID)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderItem orderItem = OrderItem.builder()
            .quantity(quantity)
            .pricePerUnit(pricePerUnit)
            .product(product)
            .order(order)
            .build();
        return orderItemRepository.save(orderItem);
    }

}
