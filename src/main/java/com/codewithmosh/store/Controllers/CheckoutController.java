package com.codewithmosh.store.Controllers;

import com.codewithmosh.store.dtos.CheckoutRequestDto;
import com.codewithmosh.store.dtos.CheckoutResponseDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequestDto request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            return ResponseEntity.badRequest().body(
                    Map.of("error" , "Cart not found")
            );
        }
        if(cart.getCartItems().isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("error" , "Cart is empty")
            );
        }
        var user = authService.getCurrentUser();
        var order = new Order();
        order.setTotalPrice(cart.calcTotalPrice());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(user);
        cart.getCartItems().forEach(c->{
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(c.getProduct());
            orderItem.setQuantity(c.getQuantity());
            orderItem.setTotalPrice(c.calcTotalPrice());
            orderItem.setUnitPrice(c.getProduct().getPrice());
            order.getItems().add(orderItem);
        });
        orderRepository.save(order);
        cart.clear();
        return ResponseEntity.ok(new CheckoutResponseDto(order.getId()));
    }
}
