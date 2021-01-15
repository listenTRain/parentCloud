package com.java.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * author:快乐风男
 * time:15:13
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaStart2 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaStart2.class);
    }
}
