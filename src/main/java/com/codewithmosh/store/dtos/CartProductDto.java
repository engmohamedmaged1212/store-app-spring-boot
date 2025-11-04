package com.codewithmosh.store.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductDto {
    private long id;
    private String name;
    private BigDecimal price;
}
