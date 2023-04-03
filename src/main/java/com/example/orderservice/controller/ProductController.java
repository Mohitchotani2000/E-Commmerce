package com.example.orderservice.controller;

import com.example.orderservice.entity.Product;
import com.example.orderservice.exception.ErrorResponse;
import com.example.orderservice.exception.ResourceAlreadyExistsException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.payload.ProductDto;
import com.example.orderservice.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-service/products")
@Log4j2
public class ProductController {

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex)
    {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
    @Autowired
    private ProductService productService;

    /**
     * Calls productService addProduct method
     * @param productDto, value of new product to be added {@link ProductDto}.
     * @return added product with Http status {@link ResponseEntity<Product>}.
     */
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto){
        log.info("API for adding Product called!!");
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
    }

    /**
     * Calls productService getAllProduct method.
     * @return all fetched products {@link List<Product>}.
     */
    @GetMapping
    public List<Product> getAllProduct(){
        log.info("Fetching all Products!!");
        return productService.getAllProducts();
    }

    /**
     * Calls productService updateProduct method.
     * @param productDto, product value to be updated {@link ProductDto}.
     * @param id, id of the product {@link Long}.
     * @return updated product with Http status {@link ResponseEntity<Product>}.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable(name = "id") long id){
        log.info("API for updating product called!!");
        return new ResponseEntity<>(productService.updateProduct(productDto,id), HttpStatus.OK);
    }

    /**
     * Deleting a product from productId.
     * @param id , id of the product {@link Long}.
     * @return Https Status on successfull deletion {@link ResponseEntity<String>}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") long id){
        productService.deleteProductById(id);
        log.info("API for deleting product called!!");
        return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
    }
}
