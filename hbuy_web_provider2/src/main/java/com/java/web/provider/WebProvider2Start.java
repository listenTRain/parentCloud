package com.java.web.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author:快乐风男
 * time:11:45
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@MapperScan("com.java.web.provider.mapper")
public class WebProvider2Start {
    public static void main(String[] args) {
        SpringApplication.run(WebProvider2Start.class);
    }
}
