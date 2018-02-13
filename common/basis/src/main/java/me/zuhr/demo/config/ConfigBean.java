package me.zuhr.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * spring-boot更新到1.5.2版本后locations属性无法使用
 * @PropertySource 注解只可以加载proprties文件,无法加载yaml文件
 * 故现在把数据放到application.yml文件中,spring-boot启动时会加载
 *
 * @author zurun
 * @date 2018/2/11 10:37:16
 */
@Configuration
@PropertySource(value = "classpath:/config/myTestConf.yml")
@ConfigurationProperties(prefix = "test")
@EnableCaching
public class ConfigBean {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
