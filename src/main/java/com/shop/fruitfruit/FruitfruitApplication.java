package com.shop.fruitfruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FruitfruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitfruitApplication.class, args);
    }

}
