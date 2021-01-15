package com.java.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * author:快乐风男
 * time:20:05
 */
@SpringBootApplication
@MapperScan(basePackages = "com.java.product.mapper")
@EnableEurekaClient
//@EnableScheduling
@ServletComponentScan(basePackages = "com.java.product.filter")
public class ProductStart {
    public static void main(String[] args) {
        SpringApplication.run(ProductStart.class);
    }
}
