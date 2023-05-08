package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.User;
import com.example.orderservice.payload.CartDto;

import java.util.List;

public interface OrderService {
    Order placeOrder(CartDto cartDto, User user) ;
    List<Order> getAllOrdersByUserId(User user);
}
