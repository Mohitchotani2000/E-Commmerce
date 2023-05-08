package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.Product;
import com.example.orderservice.entity.User;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.payload.CartDto;
import com.example.orderservice.payload.CartItemDto;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    @Value("${userService.base.url}")
    private String userBaseURL;

    @Value("${cartService.base.url}")
    private String cartBaseURL;

    @Value("${productService.base.url}")
    private String productBaseURL;

    /**
     * Places an order.
     * @param cartDto,User the order value for the order to be placed by User {@link CartDto,User}
     * @return Order {@link Order}
     */
    @Override
    public Order placeOrder(CartDto cartDto,User user){
        Order order = new Order();
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();
        if(cartItemDtoList.size()==0){
            throw new ResourceNotFoundException("Cart is Empty");
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDto cartItemDto : cartItemDtoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setProductId(cartItemDto.getProduct().getProductId());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(cartDto.getTotalCost());
        order.setCreatedDate(new Date());
        order.setUserId(user.getUserId());
        orderRepository.save(order);
        restTemplate.delete(cartBaseURL+"/delete?userId="+user.getUserId());
        log.info("Order with id " + order.getOrderId() + " added successfully");
        return order;
    }

    @Override
    public List<Order> getAllOrdersByUserId(User user) {
        List<Order> orders =  orderRepository.findAllByUserId(user.getUserId());
        if(orders.size()==0){
            throw new ResourceNotFoundException("No orders yet");
        }
        orders.forEach(x->x.getOrderItems().forEach(y-> y.setProduct(restTemplate.getForObject(productBaseURL+"/getProductDetail/"+y.getProductId(), Product.class))));
        return orders;
    }

}