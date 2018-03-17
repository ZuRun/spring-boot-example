package me.zuhr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author zurun
 * @date 2018/3/17 00:50:08
 */
@SpringCloudApplication
@EnableJpaAuditing
public class BasisServerApplication {
    public static void main(String[] args){
        SpringApplication.run(BasisServerApplication.class, args);
    }
}
