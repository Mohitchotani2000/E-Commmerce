package com.example.orderservice.entity;

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

    @Transient
    private String productName;
    @Column(name = "quantity")
    private long quantity;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "productId")
    private Product product;

}
