package com.codewithmosh.store.mapper;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderStatus", expression = "java(order.getOrderStatus().name())")
    OrderDto toDto(Order order);
}
