package com.codewithmosh.store.mapper;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice" , expression = "java(cart.calcTotalPrice())")
    @Mapping(source = "cartItems", target = "items")
    CartDto toDto(Cart cart);
    Cart toEntity(CartDto cartDto);
    @Mapping(target = "totalPrice" , expression = "java(cartItem.calcTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
