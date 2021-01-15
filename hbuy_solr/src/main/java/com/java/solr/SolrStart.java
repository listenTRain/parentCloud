package com.java.solr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * author:快乐风男
 * time:15:12
 */
@SpringBootApplication
@MapperScan(basePackages = "com.java.solr.mapper")
@EnableEurekaClient
@EnableScheduling
public class SolrStart {
    public static void main(String[] args) {
        SpringApplication.run(SolrStart.class);
    }
}
