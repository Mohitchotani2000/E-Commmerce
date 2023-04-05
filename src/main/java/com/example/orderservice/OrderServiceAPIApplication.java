package com.example.orderservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mohitchotani
 */
@SpringBootApplication
@Log4j2
public class OrderServiceAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceAPIApplication.class, args);
//		log.info("Info message");
//		log.error("Error message");
//		log.warn("Warning message");
	}
}