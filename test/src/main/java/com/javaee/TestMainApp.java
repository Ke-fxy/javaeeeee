package com.javaee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Ke
 * @Date 2022/6/9 20:29
 * @Description
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TestMainApp {

    public static void main(String[] args) {
        SpringApplication.run(TestMainApp.class,args);
    }

}