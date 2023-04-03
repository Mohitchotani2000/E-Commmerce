package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ErrorResponse;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.payload.OrderDto;
import com.example.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order-service/orders")
@Log4j2
public class OrderController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
    @Autowired
    private OrderService orderService;

    /**
     * Calls OrderService placeOrder method.
     * @param orderDto, order value to be placed {@link OrderDto}.
     * @return Placed Order values with Http status {@link ResponseEntity<Order>}.
     */
    @PostMapping("/add")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto){
        log.info("API for placing orders is called!!");
        return new ResponseEntity<>(orderService.placeOrder(orderDto), HttpStatus.CREATED);
    }

    /**
     * Calls OrderService getAllOrders method.
     * @return, list of fetched orders {@link List<Order>}.
     */
    @GetMapping
    public List<Order> getAllOrders(){
        log.info("Fetching all orders!!");
        return orderService.getAllOrders();
    }

    /**
     * Generate order by order id, calls orderService getOrderById method.
     * @param orderId, id of the order {@link Long}.
     * @return order fetched with Http status {@link ResponseEntity<Order>}.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") long orderId){
        log.info("Fetching Order with id {}", orderId);
        return new ResponseEntity<>(orderService.getOrderById(orderId),HttpStatus.OK);
    }
}
