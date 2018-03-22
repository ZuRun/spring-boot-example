package me.zuhr.demo;

import me.zuhr.demo.std.BaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author zurun
 * @date 2018/3/17 00:50:08
 */
@EnableJpaAuditing
public class BasisServerApplication extends BaseApplication {
    public static void main(String[] args){
        SpringApplication.run(BasisServerApplication.class, args);
    }
}
