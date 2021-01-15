package com.java.web.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * author:快乐风男
 * time:10:51
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient  //开启提供者的客户端
@MapperScan(basePackages = "com.java.web.provider.mapper")
public class WebProvider1Start {
    public static void main(String[] args) {
        SpringApplication.run(WebProvider1Start.class);
    }
}
