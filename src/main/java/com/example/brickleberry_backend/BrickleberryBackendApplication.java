package com.example.brickleberry_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//ssh -p 2222 s336504@helios.se.ifmo.ru -Y -L 5432:pg:5432
@SpringBootApplication
public class BrickleberryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrickleberryBackendApplication.class, args);
    }

}
