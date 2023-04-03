package com.example.orderservice.payload;

import com.example.orderservice.entity.OrderItem;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private long OrderId;
    private List<OrderItem> orderItems;
    private Date createdDate;
    private long totalPrice;
}
