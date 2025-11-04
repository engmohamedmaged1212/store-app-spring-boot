package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.mapper.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    private final String cartWasNotFoundMessage = "Cart was not found";
    private final String productWasNotFoundMessage = "Product Not found";
    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }
    public CartItemDto addToCart(UUID cartId , Long productId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw  new CartNotFoundException(cartWasNotFoundMessage);
        }
        var product =productRepository.findById(productId).orElse(null);
        if(product == null) throw  new ProductNotFoundException(productWasNotFoundMessage);

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }


    public CartItemDto updateItem(UUID cartId , Long productId , int quantity) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException(cartWasNotFoundMessage);
        }
        var cartItem = cart.getItem(productId);
        if(cartItem == null ){
            throw new ProductNotFoundException(productWasNotFoundMessage);
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void deleteItem(UUID cartId , Long productId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) {
            throw new CartNotFoundException(cartWasNotFoundMessage);
        }
        var cartItem = cart.getItem(productId);
        if(cartItem == null ){
            throw new ProductNotFoundException(productWasNotFoundMessage);
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public List<CartDto> getAllCarts(){
        return cartRepository.findAllWithCartItems()
                .stream()
                .map(cartMapper::toDto)
                .toList();
    }

    public CartDto getCart(UUID cartId){
        Optional<Cart> optionalCart = cartRepository.getCartWithItems(cartId);
        if(optionalCart.isEmpty()) throw new CartNotFoundException(cartWasNotFoundMessage);
        Cart cart = optionalCart.get();
        return cartMapper.toDto(cart);
    }
    public void clearCart(UUID cartId){
        Optional<Cart> optionalCart = cartRepository.getCartWithItems(cartId);
        if(optionalCart.isEmpty()){
            throw new CartNotFoundException(cartWasNotFoundMessage);
        }
        var cart = optionalCart.get();
        cart.clear();
        cartRepository.save(cart);
    }
}
