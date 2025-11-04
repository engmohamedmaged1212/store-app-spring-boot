package com.codewithmosh.store.Controllers;

import com.codewithmosh.store.dtos.AddItemToCartRequestDto;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Create new cart")
    @PostMapping
    public ResponseEntity<CartDto> createCart(

            UriComponentsBuilder uriBuilder
    ){
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    @Operation(summary = "Add item to the cart")
    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId ,
            @RequestBody AddItemToCartRequestDto request,
            UriComponentsBuilder uriBuilder
            ){
       var cartItemDto = cartService.addToCart(cartId , request.getProduct_id());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping
    @Operation(summary = "Get all carts")
    public ResponseEntity<List<CartDto>> getAllCarts(){
        var carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }
    @GetMapping("/{cartId}")
    @Operation(summary = "get the card")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @PutMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Update item in the cart")
    public ResponseEntity<?> updateItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ){
        var cartItemDto = cartService.updateItem(cartId , productId , request.getQuantity());
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Delete item from the cart")
    public ResponseEntity<?> deleteItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId
    ){
        cartService.deleteItem(cartId , productId);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "clear cart")
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable("cartId") UUID cartId){
       cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
