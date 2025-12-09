package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id ;

    @JoinColumn(name = "customer_id")
    @ManyToOne
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "created_at" , insertable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.PERSIST)
    private Set<OrderItem> items  = new LinkedHashSet<>();

    public static Order fromCart(Cart cart , User user) {
        var order = new Order();
        order.setTotalPrice(cart.calcTotalPrice());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(user);
        cart.getCartItems().forEach(c->{
        var orderItem = new OrderItem(order , c.getProduct()  , c.getQuantity());
        order.items.add(orderItem);
        });
        return order;
    }
}
