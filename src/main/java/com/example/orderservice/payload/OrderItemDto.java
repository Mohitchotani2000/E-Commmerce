package com.example.orderservice.payload;

import com.example.orderservice.entity.Product;
import lombok.Data;

@Data
public class OrderItemDto {
    private long orderItemId;
    private long productName;
    private long quantity;
    private Product product;
}
