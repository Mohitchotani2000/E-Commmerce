package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Product;
import com.example.orderservice.exception.ResourceAlreadyExistsException;
import com.example.orderservice.payload.ProductDto;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.utils.OrderServiceImplUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    void testAddProduct() {
        //given
        Optional<Product> products = Optional.empty();
        Product expected = OrderServiceImplUtils.PRODUCT_4;
        ProductDto request = OrderServiceImplUtils.getProductDto();
        when(productRepository.findByproductName("Bag")).thenReturn(products);
        //when
        Product actual = productService.addProduct(request);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testAddProduct_throwsException() {
        //given
        Optional<Product> product = Optional.of(OrderServiceImplUtils.PRODUCT_4);
        ProductDto request = OrderServiceImplUtils.getProductDto();
        when(productRepository.findByproductName("Bag")).thenReturn(product);
        //when
       assertThrows(ResourceAlreadyExistsException.class,()->{
            productService.addProduct(request);
        });
        //then
        Mockito.verify(productRepository,never()).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        //given
        List<Product> expected = OrderServiceImplUtils.getProductsList();
        List<Product> products = OrderServiceImplUtils.getProductsList();
        when(productRepository.findAll()).thenReturn(products);
        //when
        List<Product> actual = productService.getAllProducts();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testUpdateProduct() {
        //given
        Optional<Product> product = Optional.of(OrderServiceImplUtils.PRODUCT_3);
        ProductDto request = OrderServiceImplUtils.getProductDto();
        request.setProductName("Laptop Charger");
        request.setPrice(1200);
        when(productRepository.findById(request.getProductId())).thenReturn(product);
        //when
        Product actual = productService.updateProduct(request,request.getProductId());
        //then
        assertThat(actual.getProductName()).isEqualTo("Laptop Charger");
        assertThat(actual.getPrice()).isEqualTo(1200);
    }

    @Test
    void testDeleteProductById() {
        //given
        Optional<Product> product = Optional.of(OrderServiceImplUtils.PRODUCT_1);
        long id =1;
        when(productRepository.findById(id)).thenReturn(product);
        doNothing().when(productRepository).delete(OrderServiceImplUtils.PRODUCT_1);
        //when
        productService.deleteProductById(id);
        //then
        verify(productRepository,times(1)).delete(OrderServiceImplUtils.PRODUCT_1);
    }
}