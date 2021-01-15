package com.java.register;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author:快乐风男
 * time:16:59
 */
@SpringBootApplication
@MapperScan(basePackages = "com.java.register.mapper")
@EnableEurekaClient
public class RegisterStart {
    public static void main(String[] args) {
        SpringApplication.run(RegisterStart.class);
    }
}
