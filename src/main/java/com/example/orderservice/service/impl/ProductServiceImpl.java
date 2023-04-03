package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Product;
import com.example.orderservice.exception.ResourceAlreadyExistsException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.payload.ProductDto;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper mapper;

    /**
     * Adds a new product to the database.
     * @param productDto, values of the product to be added {@link ProductDto}.
     * @return productDto, added product {@link Product}.
     */
    @Override
    public Product addProduct(ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findByproductName(productDto.getProductName());
        if(existingProduct.isPresent()){
            throw new ResourceAlreadyExistsException("Cannot be added. Product Already exists");
        }
        Product addedProduct =  mapper.convertValue(productDto,Product.class);
        productRepository.save(addedProduct);
        log.info("Product with id " + addedProduct.getProductId() + " added successfully!!");
        return addedProduct;

    }

    /**
     * Get all products from DB.
     * @return list of all products {@link List<Product>}.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Update a product from product value and given id.
     * @param productDto, product values to be updated {@link ProductDto}.
     * @param id, id of the product {@link Long}.
     * @return updated value of the product {@link Product}.
     */
    @Override
    public Product updateProduct(ProductDto productDto, long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with given with Id: " + id + " not found"));
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
        log.info("Successfully updated the product with product id " + product.getProductId() +  " !!");
        return product;
    }

    /**
     * Delete a product from id.
     * @param id, id of the product to be deleted {@link Long}.
     */
    @Override
    public void deleteProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product with given with Id: " + id + " not found"));
        productRepository.delete(product);
        log.info("Product with productId " + product.getProductId() + " is deleted successfully!!");
    }
}