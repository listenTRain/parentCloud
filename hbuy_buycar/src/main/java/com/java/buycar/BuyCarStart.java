package com.java.buycar;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author:快乐风男
 * time:12:40
 */
@SpringBootApplication
@EnableEurekaClient
@ServletComponentScan(basePackages = "com.java.buycar.filter")
@MapperScan(basePackages = "com.java.buycar.mapper")
public class BuyCarStart {
    public static void main(String[] args) {
        SpringApplication.run(BuyCarStart.class);
    }
}
