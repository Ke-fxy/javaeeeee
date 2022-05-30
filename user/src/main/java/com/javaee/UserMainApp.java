package com.javaee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Ke
 * @Date 2022/5/30 13:24
 * @Description
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserMainApp {

    public static void main(String[] args) {
        SpringApplication.run(UserMainApp.class,args);
    }

}