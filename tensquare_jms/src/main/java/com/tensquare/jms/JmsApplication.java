package com.tensquare.jms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class JmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmsApplication.class, args);
    }
}
