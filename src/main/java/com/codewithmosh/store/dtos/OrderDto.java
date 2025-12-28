package com.codewithmosh.store.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private int id ;
    private String orderStatus;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items= new ArrayList<>();
    private BigDecimal totalPrice;
}
