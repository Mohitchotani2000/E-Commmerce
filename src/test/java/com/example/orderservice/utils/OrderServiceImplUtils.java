//package com.example.orderservice.utils;
//
//import com.example.orderservice.entity.Order;
//import com.example.orderservice.entity.OrderItem;
//import com.example.orderservice.entity.Product;
//import com.example.orderservice.payload.OrderDto;
//import com.example.orderservice.payload.ProductDto;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//public class OrderServiceImplUtils {
//
//    public static Product PRODUCT_1 = new Product(1,"Pencil",10);
//    public static Product PRODUCT_2 = new Product(2,"Mouse",500);
//    public static Product PRODUCT_3 = new Product(3,"Charger",1000);
//    public static Product PRODUCT_4 = new Product(4,"Bag",500);
//    public static OrderItem ITEM_1 = new OrderItem(1,"Pencil",2,PRODUCT_1);
//    public static OrderItem ITEM_2 = new OrderItem(2,"Mouse",1,PRODUCT_2);
//    public static OrderItem ITEM_3 = new OrderItem(3,"Charger",1,PRODUCT_3);
//    public static List<OrderItem> ORDER_ITEM_1 = new ArrayList<>(Arrays.asList(ITEM_1,ITEM_2));
//    public static List<OrderItem> ORDER_ITEM_2 = new ArrayList<>(Arrays.asList(ITEM_1,ITEM_3));
//    public static Order ORDER_1 = new Order(1,new Date(),520,ORDER_ITEM_1);
//    public static Order ORDER_2 = new Order(2,new Date(),1020,ORDER_ITEM_2);
//    public static List<Product> getProductsList(){
//        List<Product> products = new ArrayList<>(Arrays.asList(PRODUCT_1,PRODUCT_2,PRODUCT_3,PRODUCT_4));
//        return products;
//    }
//
//    public static List<Order> getOrdersList(){
//        List<Order> orders = new ArrayList<>(Arrays.asList(ORDER_1,ORDER_2));
//        return orders;
//    }
//
//    public static ProductDto getProductDto(){
//        ProductDto productDto = new ProductDto();
//        productDto.setProductId(4);
//        productDto.setProductName("Bag");
//        productDto.setPrice(500);
//        return productDto;
//    }
//
//    public static OrderDto getOrderDto(){
//        OrderDto orderDto =  new OrderDto();
//        orderDto.setOrderItems(ORDER_ITEM_1);
//        orderDto.setTotalPrice(520);
//        orderDto.setCreatedDate(new Date());
//        orderDto.setOrderId(1);
//        return orderDto;
//    }
//
//    public static List<Order> getOrderList(){
//        return new ArrayList<>(Arrays.asList(ORDER_1,ORDER_2));
//    }
//}
