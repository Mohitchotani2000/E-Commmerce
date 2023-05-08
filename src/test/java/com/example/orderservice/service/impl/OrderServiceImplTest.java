//package com.example.orderservice.service.impl;
//
//import com.example.orderservice.entity.Order;
//import com.example.orderservice.entity.Product;
//import com.example.orderservice.exception.ResourceNotFoundException;
//import com.example.orderservice.utils.OrderServiceImplUtils;
//import com.example.orderservice.payload.OrderDto;
//import com.example.orderservice.repository.OrderItemRepository;
//import com.example.orderservice.repository.OrderRepository;
//import com.example.orderservice.repository.ProductRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class OrderServiceImplTest {
//    @InjectMocks
//    private OrderServiceImpl orderService;
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private ProductRepository productRepository;
//    @Mock
//    private OrderItemRepository orderItemRepository;
//    @Spy
//    private ObjectMapper mapper;
//
////    @Test
////    void testPlaceOrder() {
////        //given
////        List<Product> products = OrderServiceImplUtils.getProductsList();
////        Order expected = OrderServiceImplUtils.getOrderList().get(0);
////        OrderDto request = OrderServiceImplUtils.getOrderDto();
////        when(productRepository.findByproductName("Pencil")).thenReturn(Optional.of(products.get(0)));
////        when(productRepository.findByproductName("Mouse")).thenReturn(Optional.of(products.get(1)));
////        //when
////        Order actual = orderService.placeOrder(request);
////        //then
////        assertThat(actual).usingRecursiveComparison().ignoringFields("createdDate").isEqualTo(expected);
////    }
//
//    @Test
//    void testGetAllOrders() {
//        //given
//        List<Order> expected = OrderServiceImplUtils.getOrdersList();
//        List<Order> orders = OrderServiceImplUtils.getOrdersList();
//        when(orderRepository.findAll()).thenReturn(orders);
//        //when
//        List<Order> actual = orderService.getAllOrders();
//        //then
//        assertThat(actual).usingRecursiveComparison().ignoringFields("createdDate").isEqualTo(expected);
//    }
//
//    @Test
//    void testGetOrderById() {
//        //given
//        Order expected = OrderServiceImplUtils.getOrdersList().get(0);
//        Order request = OrderServiceImplUtils.getOrdersList().get(0);
//        long orderId = 1;
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(request));
//        //when
//        Order actual = orderService.getOrderById(orderId);
//        //then
//        assertThat(actual).usingRecursiveComparison().ignoringFields("createdDate").isEqualTo(expected);
//    }
//
//    @Test
//    void testGetProductByName() {
//        //given
//        Optional<Product> products = OrderServiceImplUtils.getProductsList().stream().findFirst();
//        String productName ="Pencil";
//        Product expected = OrderServiceImplUtils.getProductsList().get(0);
//        when(productRepository.findByproductName(productName)).thenReturn(products);
//        //when
//        Product actual = orderService.getProductByName(productName);
//        //then
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    void testGetProductByName_throwsException() {
//        //given
//        String productName ="Laptop";
//        //when
//        assertThrows(ResourceNotFoundException.class,()-> orderService.getProductByName(productName));
//        //then
//        Mockito.verify(productRepository,only()).findByproductName(productName);
//    }
//}