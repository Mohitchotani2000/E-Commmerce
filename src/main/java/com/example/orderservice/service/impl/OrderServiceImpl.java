package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.Product;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.payload.OrderDto;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ObjectMapper mapper;

    /**
     * Places an order.
     * @param orderDto the order value for the order to be placed {@link OrderDto}
     * @return Order {@link Order}
     */
    @Override
    public Order placeOrder(OrderDto orderDto) {
        Order order = mapper.convertValue(orderDto,Order.class);
        List<OrderItem> orderItems = orderDto.getOrderItems();
        orderItems.forEach(x->x.setProduct(getProductByName(x.getProductName()))); // reduce
        order.setOrderItems(orderItems);
        long sum = 0;
        for(OrderItem x : orderItems){
            sum += x.getQuantity() * x.getProduct().getPrice();
        }
        order.setTotalPrice(sum);
        order.setCreatedDate(new Date());
        orderRepository.save(order);
        orderItems.forEach(x->x.setOrder(order));
        orderItemRepository.saveAll(orderItems);
        log.info("Order with id " + order.getOrderId() + " added successfully");
        return order;
    }

    /**
     * To get all orders placed.
     * @return List<OrderDto> - of all orders placed {@link List<Order>}.
     */
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(x->x.getOrderItems().forEach(y-> y.setProductName(y.getProduct().getProductName())));
        return orders;
    }

    /**
     * Get order using orderId.
     * @param orderId, the id of the order {@link Long}.
     * @return Order found at the given id {@link Order}.
     * @throws ResourceNotFoundException
     */
    @Override
    public Order getOrderById(long orderId) throws ResourceNotFoundException{
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with id "+ orderId + " does not exist"));
        order.getOrderItems().forEach(x-> x.setProductName(x.getProduct().getProductName()));
        return order;
    }

    /**
     * Find a product by product name.
     * @param productName , name of the product {@link String}.
     * @return product found with the name {@link Product}.
     * @throws ResourceNotFoundException, if product not found, throws exception
     */
    public Product getProductByName(String productName) throws ResourceNotFoundException{
        Optional<Product> product = productRepository.findByproductName(productName);
        if(product.isPresent()){
            return product.get();
        }
        throw new ResourceNotFoundException("Product " + productName + " does not exist");
    }
}