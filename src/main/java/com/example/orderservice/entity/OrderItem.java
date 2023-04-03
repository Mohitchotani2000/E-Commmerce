package com.example.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;
//    @Column(name = "product_name")
    @Transient
//    @JsonIgnore
    private String productName;
    @Column(name = "quantity")
    private long quantity;
    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "productId")
    private Product product;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;
}
