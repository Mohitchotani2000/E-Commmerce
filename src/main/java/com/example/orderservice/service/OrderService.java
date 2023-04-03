package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.payload.OrderDto;

import java.util.List;

public interface OrderService {
    Order placeOrder(OrderDto orderDto);
    List<Order> getAllOrders();
    Order getOrderById(long orderId);
}
