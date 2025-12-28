package com.codewithmosh.store.dtos;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.codewithmosh.store.entities.OrderItem}
 */
@Data
public class OrderItemDto {
    private OrderItemProductDto product;
    int quantity;
    BigDecimal totalPrice;
}