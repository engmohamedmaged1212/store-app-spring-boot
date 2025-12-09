package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.CheckoutRequestDto;
import com.codewithmosh.store.dtos.CheckoutResponseDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor

public class CheckOutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    public CheckoutResponseDto checkout(CheckoutRequestDto request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElseThrow(()->new CartNotFoundException(""));

        if(cart.isEmpty()){
            throw new CartNotFoundException("cart is empty");
        }
        var user = authService.getCurrentUser();
        var order = Order.fromCart(cart, user);
        orderRepository.save(order);
        cart.clear();
        return new CheckoutResponseDto(order.getId());
    }
}
