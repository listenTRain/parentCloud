package com.java.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * author:快乐风男
 * time:15:43
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.java.admin.mapper")
@ServletComponentScan(basePackages = "com.java.admin.filter")
public class AdminStart {
    public static void main(String[] args) {
        SpringApplication.run(AdminStart.class);
    }
}

