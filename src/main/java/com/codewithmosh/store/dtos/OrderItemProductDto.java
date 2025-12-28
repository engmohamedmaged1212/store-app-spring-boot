package com.codewithmosh.store.dtos;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.codewithmosh.store.entities.Product}
 */
@Data
public class OrderItemProductDto  {
   private Long id;
   private String name;
   private BigDecimal price;
}