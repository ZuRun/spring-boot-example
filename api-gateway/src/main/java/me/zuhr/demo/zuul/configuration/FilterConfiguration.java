package me.zuhr.demo.zuul.configuration;

import me.zuhr.demo.zuul.filter.CheckLoginStateFilter;
import me.zuhr.demo.zuul.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/4/21 22:51:02
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public CheckLoginStateFilter accessFilter() {
        return new CheckLoginStateFilter();
    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
