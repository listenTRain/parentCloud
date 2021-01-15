package com.java.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * author:快乐风男
 * time:16:23
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy //开启网关代理
public class ZuulStart {
    public static void main(String[] args) {
        SpringApplication.run(ZuulStart.class);
    }
}
