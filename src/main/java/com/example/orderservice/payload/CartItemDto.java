package com.example.orderservice.payload;

import com.example.orderservice.entity.Product;
import lombok.Data;

@Data
public class CartItemDto {
    private long cartItemId;
    private  long quantity;
    private Product product;
}
