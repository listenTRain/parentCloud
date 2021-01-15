package com.java.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author:快乐风男
 * time:19:06
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.java.sso.filter")
@MapperScan(basePackages = "com.java.sso.mapper")
//启用注册中心客户端
@EnableEurekaClient
public class SsoStart {
    public static void main(String[] args) {
        SpringApplication.run(SsoStart.class);
    }
}
