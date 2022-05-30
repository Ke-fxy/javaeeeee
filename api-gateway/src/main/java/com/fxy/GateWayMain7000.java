package com.fxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Ke
 * @Date 2022/4/18 16:01
 * @Description
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayMain7000 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayMain7000.class);
    }

}