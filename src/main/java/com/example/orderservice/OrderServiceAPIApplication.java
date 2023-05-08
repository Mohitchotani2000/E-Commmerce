package com.example.orderservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author mohitchotani
 */
@SpringBootApplication
@Log4j2
public class OrderServiceAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceAPIApplication.class, args);
	}

}