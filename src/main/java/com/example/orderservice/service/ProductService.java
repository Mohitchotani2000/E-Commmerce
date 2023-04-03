package com.example.orderservice.service;

import com.example.orderservice.entity.Product;
import com.example.orderservice.payload.ProductDto;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);

    List<Product> getAllProducts();

    Product updateProduct(ProductDto productDto, long id);

    void deleteProductById(long id);
}
