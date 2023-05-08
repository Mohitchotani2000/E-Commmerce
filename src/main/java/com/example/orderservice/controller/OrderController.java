package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.User;
import com.example.orderservice.exception.UserNotExistException;
import com.example.orderservice.payload.CartDto;
import com.example.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/order-service/orders")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${userService.base.url}")
    private String userBaseURL;
    @Value("${cartService.base.url}")
    private String cartBaseURL;


    /**
     * @param userId, user cart value to be placed.
     * @return Placed Order values with Http status {@link ResponseEntity<Order>}.
     * @throws UserNotExistException
     */

    @PostMapping("/add")
    public ResponseEntity<Order> placeOrder(@RequestParam("userId")long userId) throws UserNotExistException {
        log.info("API for placing orders is called!!");
        User user = restTemplate.getForObject(userBaseURL + userId,User.class);
        CartDto cartDto = restTemplate.getForObject(cartBaseURL+"/getCartItems?userId="+userId,CartDto.class);
        return new ResponseEntity<>(orderService.placeOrder(cartDto,user), HttpStatus.CREATED);
    }

    /**
     * Calls OrderService getAllOrders method.
     * @return, list of fetched orders {@link List<Order>}.
     */

    @GetMapping("/user")
    public List<Order> getAllUserOrders(@RequestParam("userId") long userId){
        User user = restTemplate.getForObject(userBaseURL + userId,User.class);
        return orderService.getAllOrdersByUserId(user);
    }

}
