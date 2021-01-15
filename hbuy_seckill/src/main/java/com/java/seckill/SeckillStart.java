package com.java.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * author:快乐风男
 * time:14:49
 */
@SpringBootApplication
@MapperScan(basePackages = "com.java.seckill.mapper")
@EnableEurekaClient
@EnableScheduling //开启任务调度
public class SeckillStart {
    public static void main(String[] args) {
        SpringApplication.run(SeckillStart.class);
    }
}
