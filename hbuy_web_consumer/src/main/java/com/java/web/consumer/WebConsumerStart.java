package com.java.web.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * author:快乐风男
 * time:10:29
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient //开启消费者
@ServletComponentScan(basePackages = "com.java.web.consumer.filter")
public class WebConsumerStart {
    public static void main(String[] args) {
        SpringApplication.run(WebConsumerStart.class);
    }

    @Bean //实例化  接收请求处理响应的模版
    @LoadBalanced //启动项目时自动加载，创建RestTemplate对象放在SpringIOC容器中
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
