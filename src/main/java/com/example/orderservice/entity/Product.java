package com.example.orderservice.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long productId;
    private String productName;
    private String productUrl;
    private String productDesc;
    private double productPrice;
}

