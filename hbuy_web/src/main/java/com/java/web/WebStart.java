package com.java.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author:快乐风男
 * time:15:32
 */
@SpringBootApplication
@MapperScan(basePackages = "com.java.web.mapper")
@EnableEurekaClient //启用注册中心客户端
@ServletComponentScan(basePackages = "com.java.web.filter")
public class WebStart {
    public static void main(String[] args) {
        SpringApplication.run(WebStart.class);
    }
}
