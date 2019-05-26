package com.tensquare.qa;

import com.tensquare.tools.JwtUtil;
import com.tensquare.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableFeignClients
public class QaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
