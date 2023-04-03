package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products",uniqueConstraints = {@UniqueConstraint(columnNames = {"product_name"})})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    @Column(name = "product_name",nullable = false,unique = true)
    private String productName;
    @Column(name = "price")
    private long price;
}
