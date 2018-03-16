package me.zuhr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author zurun
 * @date 2018/3/17 00:50:08
 */
@SpringCloudApplication
public class BasisServerApplication {
    public static void main(String[] args){
        SpringApplication.run(BasisServerApplication.class, args);
    }
}
