package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_created" , insertable = false , updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.MERGE , orphanRemoval = true)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public CartItem getItem(Long productId){
        return this.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    public CartItem addItem(Product product){
        var cartItem = this.getItem(product.getId());
        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(this);
            cartItem.setQuantity(1);
            cartItems.add(cartItem);
        }
        return cartItem;
    }
    public void removeItem(Long productId){
        var carItem = getItem(productId);
        if(carItem != null){
            cartItems.remove(carItem);
            carItem.setCart(null);
        }
    }
    public void clear(){
        cartItems.clear();
    }
    public BigDecimal calcTotalPrice(){
       return cartItems.stream()
               .map(CartItem :: calcTotalPrice)
               .reduce(BigDecimal.valueOf(0) , BigDecimal:: add);
    }
}
