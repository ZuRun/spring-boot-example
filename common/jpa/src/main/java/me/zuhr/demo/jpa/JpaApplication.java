package me.zuhr.demo.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class JpaApplication {

    public static void main(String[] args){
        SpringApplication.run(JpaApplication.class, args);
    }
}
