package com.java.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * author:快乐风男
 * time:14:56
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaStart1 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaStart1.class);

    }
}


